package com.shorterurl.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Response payload returned after a URL is shortened")
public record ShorterUrlResponse(
    @Schema(description = "The hash ID that identifies the shortened URL", example = "tN72u2j")
        String id,
    @Schema(
            description = "The original long URL",
            example = "https://www.youtube.com/watch?v=MarSC2dFA9g")
        String originalUrl,
    @Schema(
            description = "The ready-to-use shortened URL",
            example = "http://localhost:8081/url/tN72u2j")
        String shortenedUrl,
    @Schema(
            description = "Date and time when the shortened URL expires",
            example = "2027-05-29 14:30:00")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime expiresAt) {}
