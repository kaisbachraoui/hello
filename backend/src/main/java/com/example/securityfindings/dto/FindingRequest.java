package com.example.securityfindings.dto;

import com.example.securityfindings.entity.FindingStatus;
import com.example.securityfindings.entity.Impact;
import com.example.securityfindings.entity.Likelihood;
import com.example.securityfindings.entity.RiskLevel;
import com.example.securityfindings.entity.Severity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public record FindingRequest(
        @NotNull UUID projectId,
        @NotBlank String riskId,
        @NotBlank String title,
        String description,
        @NotNull Severity severity,
        @NotNull Likelihood likelihood,
        @NotNull Impact impact,
        @NotNull RiskLevel riskLevel,
        @NotNull FindingStatus status,
        @NotBlank String owner,
        LocalDate dueDate,
        Set<String> controlTags
) {
}
