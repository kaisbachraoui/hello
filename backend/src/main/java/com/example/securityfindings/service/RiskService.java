package com.example.securityfindings.service;

import com.example.securityfindings.dto.RiskDto;
import com.example.securityfindings.dto.RiskImportResult;
import com.example.securityfindings.dto.RiskRequest;
import com.example.securityfindings.entity.Risk;
import com.example.securityfindings.mapper.RiskMapper;
import com.example.securityfindings.repository.RiskRepository;
import com.example.securityfindings.util.ResourceNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RiskService {
    private final RiskRepository riskRepository;
    private final RiskMapper riskMapper;

    public RiskService(RiskRepository riskRepository, RiskMapper riskMapper) {
        this.riskRepository = riskRepository;
        this.riskMapper = riskMapper;
    }

    public Page<RiskDto> search(String domain, String keyword, Pageable pageable) {
        return riskRepository.search(domain, keyword, pageable).map(riskMapper::toDto);
    }

    public RiskDto getById(String riskId) {
        Risk risk = riskRepository.findById(riskId)
                .orElseThrow(() -> new ResourceNotFoundException("Risk not found: " + riskId));
        return riskMapper.toDto(risk);
    }

    @Transactional
    public RiskDto create(RiskRequest request) {
        Risk risk = riskMapper.toEntity(request);
        return riskMapper.toDto(riskRepository.save(risk));
    }

    @Transactional
    public RiskDto update(String riskId, RiskRequest request) {
        Risk risk = riskRepository.findById(riskId)
                .orElseThrow(() -> new ResourceNotFoundException("Risk not found: " + riskId));
        riskMapper.update(risk, request);
        return riskMapper.toDto(riskRepository.save(risk));
    }

    @Transactional
    public void delete(String riskId) {
        if (!riskRepository.existsById(riskId)) {
            throw new ResourceNotFoundException("Risk not found: " + riskId);
        }
        riskRepository.deleteById(riskId);
    }

    @Transactional
    public RiskImportResult importFromExcel(InputStream inputStream) {
        List<String> skipped = new ArrayList<>();
        int imported = 0;
        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                String riskId = getCellValue(row, 0);
                if (riskId == null || riskId.isBlank() || riskRepository.existsById(riskId)) {
                    if (riskId != null && !riskId.isBlank()) {
                        skipped.add(riskId);
                    }
                    continue;
                }
                Risk risk = new Risk();
                risk.setRiskId(riskId);
                risk.setDomain(getCellValue(row, 1));
                risk.setTitle(getCellValue(row, 2));
                risk.setDescription(getCellValue(row, 3));
                risk.setTypicalFindingExamples(getCellValue(row, 4));
                risk.setAnnexAMapping(getCellValue(row, 5));
                riskRepository.save(risk);
                imported++;
            }
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to import risks", ex);
        }
        return new RiskImportResult(imported, skipped);
    }

    private String getCellValue(Row row, int index) {
        if (row.getCell(index) == null) {
            return null;
        }
        row.getCell(index).setCellType(org.apache.poi.ss.usermodel.CellType.STRING);
        return row.getCell(index).getStringCellValue();
    }
}
