package com.shorterurl.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Schema(description = "Request payload for shortening a URL")
public record ShorterUrlRequest(
    @NotBlank(message = "URL must not be blank")
        @URL(message = "URL is not valid")
        @Schema(
            description = "The original URL to be shortened",
            example = "https://www.youtube.com/watch?v=MarSC2dFA9g",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String url) {}
