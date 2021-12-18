package com.shorterurl.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.shorterurl.service.ShorterUrlService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/url")
@RestController
@Api(value = "ShorterUrl")
public class ShorterUrlController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ShorterUrlController.class);

	private final ShorterUrlService service;

	public ShorterUrlController(ShorterUrlService service) {
		this.service = service;
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value = "Service that redirects the user to the original url from a short url.")
	public RedirectView redirect(@PathVariable String id) {
		LOGGER.info(String.format("Shortened url to redirect: %s", id));
		String originalUrl = service.getOriginalURL(id);
		LOGGER.info(String.format("Original URL: %s", originalUrl));
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl(originalUrl);
		return redirectView;
	}

	@PostMapping
	@ApiOperation(value = "Service that registers and generates a short URL for the user.")
	public ResponseEntity<String> shorter(@RequestBody String url, HttpServletRequest request) {
		LOGGER.info(String.format("Received url to shorten: %s", url));
		String localURL = request.getRequestURL().toString();
		String shorterUrl = service.shorterUrl(localURL, url);
		LOGGER.info(String.format("Shortened url is: %s", shorterUrl));
		return ResponseEntity.ok(shorterUrl);
	}
}
