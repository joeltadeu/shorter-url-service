package com.shorterurl.controller;

import com.shorterurl.domain.ShorterUrl;
import com.shorterurl.dto.ShorterUrlRequest;
import com.shorterurl.dto.ShorterUrlResponse;
import com.shorterurl.service.ShorterUrlService;
import com.shorterurl.service.exception.handler.StandardError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.net.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/v1/urls")
@Tag(name = "Shorter URL", description = "Operations for generating and resolving shortened URLs")
public class ShorterUrlController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ShorterUrlController.class);

  private final ShorterUrlService service;

  public ShorterUrlController(ShorterUrlService service) {
    this.service = service;
  }

  @Operation(
      summary = "Shorten a URL",
      description =
          "Accepts a long URL and returns a shortened alias. "
              + "The mapping is persisted in Redis with a one-year TTL.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "201",
        description = "Shortened URL created successfully",
        headers =
            @Header(
                name = "Location",
                description = "Redirect URL of the created shortened resource",
                schema = @Schema(type = "string", example = "http://localhost:8081/urls/tN72u2j")),
        content =
            @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ShorterUrlResponse.class))),
    @ApiResponse(
        responseCode = "400",
        description = "The provided URL is missing or invalid",
        content =
            @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = StandardError.class)))
  })
  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ShorterUrlResponse> shorter(
      @RequestBody(
              description = "Payload containing the original URL to be shortened",
              required = true,
              content = @Content(schema = @Schema(implementation = ShorterUrlRequest.class)))
          @org.springframework.web.bind.annotation.RequestBody
          @Valid
          ShorterUrlRequest request,
      HttpServletRequest httpRequest) {
    LOGGER.info("Received url to shorten: {}", request.url());

    ShorterUrl saved = service.shorterUrl(request.url());

    URI location = URI.create(httpRequest.getRequestURL().toString() + "/" + saved.getId());
    ShorterUrlResponse response =
        new ShorterUrlResponse(
            saved.getId(), saved.getUrl(), location.toString(), saved.getExpiredAt());

    LOGGER.info("Shortened url created: {}", location);
    return ResponseEntity.created(location).body(response);
  }

  @Operation(
      summary = "Redirect to original URL",
      description =
          "Resolves the short URL hash ID and issues an HTTP 302 redirect to the original long URL.")
  @ApiResponses({
    @ApiResponse(responseCode = "302", description = "Redirect to the original URL"),
    @ApiResponse(
        responseCode = "404",
        description = "No URL found for the given short ID",
        content =
            @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = StandardError.class)))
  })
  @GetMapping("/{id}")
  public RedirectView redirect(
      @Parameter(
              description = "The hash ID of the shortened URL",
              example = "tN72u2j",
              required = true)
          @PathVariable
          String id) {
    LOGGER.info("Shortened url to redirect: {}", id);
    String originalUrl = service.getOriginalURL(id);
    LOGGER.info("Original URL: {}", originalUrl);
    RedirectView redirectView = new RedirectView();
    redirectView.setUrl(originalUrl);
    return redirectView;
  }
}
