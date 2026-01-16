package com.example.securityfindings.dto;

import jakarta.validation.constraints.NotBlank;

public record RiskRequest(
        @NotBlank String riskId,
        @NotBlank String domain,
        @NotBlank String title,
        String description,
        String typicalFindingExamples,
        String annexAMapping
) {
}
