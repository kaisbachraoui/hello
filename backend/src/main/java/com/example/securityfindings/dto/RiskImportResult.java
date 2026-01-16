package com.example.securityfindings.dto;

import java.util.List;

public record RiskImportResult(
        int imported,
        List<String> skippedRiskIds
) {
}
