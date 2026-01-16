package com.example.securityfindings.controller;

import com.example.securityfindings.dto.RiskDto;
import com.example.securityfindings.dto.RiskImportResult;
import com.example.securityfindings.dto.RiskRequest;
import com.example.securityfindings.service.RiskService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/risks")
public class RiskController {
    private final RiskService riskService;

    public RiskController(RiskService riskService) {
        this.riskService = riskService;
    }

    @GetMapping
    public Page<RiskDto> search(@RequestParam(required = false) String domain,
                                @RequestParam(required = false) String keyword,
                                Pageable pageable) {
        return riskService.search(domain, keyword, pageable);
    }

    @GetMapping("/{riskId}")
    public RiskDto get(@PathVariable String riskId) {
        return riskService.getById(riskId);
    }

    @PreAuthorize("hasAnyRole('ADMIN','SECURITY_ARCHITECT')")
    @PostMapping
    public RiskDto create(@Valid @RequestBody RiskRequest request) {
        return riskService.create(request);
    }

    @PreAuthorize("hasAnyRole('ADMIN','SECURITY_ARCHITECT')")
    @PutMapping("/{riskId}")
    public RiskDto update(@PathVariable String riskId, @Valid @RequestBody RiskRequest request) {
        return riskService.update(riskId, request);
    }

    @PreAuthorize("hasAnyRole('ADMIN','SECURITY_ARCHITECT')")
    @DeleteMapping("/{riskId}")
    public void delete(@PathVariable String riskId) {
        riskService.delete(riskId);
    }

    @PreAuthorize("hasAnyRole('ADMIN','SECURITY_ARCHITECT')")
    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public RiskImportResult importRisks(@RequestParam("file") MultipartFile file) {
        try {
            return riskService.importFromExcel(file.getInputStream());
        } catch (Exception ex) {
            throw new IllegalStateException("Unable to import risks", ex);
        }
    }
}
