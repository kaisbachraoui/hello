package com.example.securityfindings.dto;

public record RiskDto(
        String riskId,
        String domain,
        String title,
        String description,
        String typicalFindingExamples,
        String annexAMapping
) {
}
