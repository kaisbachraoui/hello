package com.example.securityfindings.controller;

import com.example.securityfindings.dto.DashboardSummary;
import com.example.securityfindings.service.ReportService;
import java.util.UUID;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/dashboard")
    public DashboardSummary dashboard() {
        return reportService.getDashboard();
    }

    @GetMapping("/projects/{projectId}/findings/export")
    public ResponseEntity<byte[]> exportProjectFindings(@PathVariable UUID projectId) {
        byte[] data = reportService.exportProjectFindings(projectId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=project-findings.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(data);
    }
}
