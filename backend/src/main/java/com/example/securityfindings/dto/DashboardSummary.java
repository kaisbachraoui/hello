package com.example.securityfindings.dto;

import java.util.List;

public record DashboardSummary(
        List<DomainCount> findingsByDomain,
        List<SeverityStatusCount> findingsBySeverityStatus,
        List<RiskCount> topRecurringRisks
) {
    public record DomainCount(String domain, long count) {
    }

    public record SeverityStatusCount(String severity, String status, long count) {
    }

    public record RiskCount(String riskId, String riskTitle, long count) {
    }
}
