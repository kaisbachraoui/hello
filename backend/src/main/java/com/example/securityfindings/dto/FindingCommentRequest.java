package com.example.securityfindings.dto;

import jakarta.validation.constraints.NotBlank;

public record FindingCommentRequest(
        @NotBlank String author,
        @NotBlank String comment
) {
}
