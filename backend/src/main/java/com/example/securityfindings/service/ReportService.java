package com.example.securityfindings.service;

import com.example.securityfindings.dto.DashboardSummary;
import com.example.securityfindings.entity.Finding;
import com.example.securityfindings.repository.FindingRepository;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    private final FindingRepository findingRepository;

    public ReportService(FindingRepository findingRepository) {
        this.findingRepository = findingRepository;
    }

    public DashboardSummary getDashboard() {
        return new DashboardSummary(
                findingRepository.countByDomain(),
                findingRepository.countBySeverityStatus(),
                findingRepository.topRisks(PageRequest.of(0, 10))
        );
    }

    public byte[] exportProjectFindings(UUID projectId) {
        List<Finding> findings = findingRepository.findByProjectId(projectId);
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Findings");
            Row header = sheet.createRow(0);
            String[] columns = {"project", "finding", "riskId", "riskTitle", "severity", "status", "owner", "dueDate", "evidence"};
            for (int i = 0; i < columns.length; i++) {
                header.createCell(i).setCellValue(columns[i]);
            }
            int rowIndex = 1;
            for (Finding finding : findings) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(finding.getProject().getName());
                row.createCell(1).setCellValue(finding.getTitle());
                row.createCell(2).setCellValue(finding.getRisk().getRiskId());
                row.createCell(3).setCellValue(finding.getRisk().getTitle());
                row.createCell(4).setCellValue(finding.getSeverity().name());
                row.createCell(5).setCellValue(finding.getStatus().name());
                row.createCell(6).setCellValue(finding.getOwner());
                row.createCell(7).setCellValue(finding.getDueDate() == null ? "" : finding.getDueDate().toString());
                row.createCell(8).setCellValue("See evidence list");
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (IOException ex) {
            throw new IllegalStateException("Unable to export findings", ex);
        }
    }
}
