package com.example.securityfindings.dto;

import com.example.securityfindings.entity.FindingStatus;
import com.example.securityfindings.entity.Impact;
import com.example.securityfindings.entity.Likelihood;
import com.example.securityfindings.entity.RiskLevel;
import com.example.securityfindings.entity.Severity;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public record FindingDto(
        UUID id,
        UUID projectId,
        String projectName,
        String riskId,
        String riskTitle,
        String title,
        String description,
        Severity severity,
        Likelihood likelihood,
        Impact impact,
        RiskLevel riskLevel,
        FindingStatus status,
        String owner,
        LocalDate dueDate,
        Instant createdAt,
        Set<String> controlTags
) {
}
