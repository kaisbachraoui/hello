package com.example.securityfindings.controller;

import com.example.securityfindings.dto.EvidenceDto;
import com.example.securityfindings.dto.FindingCommentDto;
import com.example.securityfindings.dto.FindingCommentRequest;
import com.example.securityfindings.dto.FindingDto;
import com.example.securityfindings.dto.FindingHistoryDto;
import com.example.securityfindings.dto.FindingHistoryRequest;
import com.example.securityfindings.dto.FindingRequest;
import com.example.securityfindings.entity.FindingStatus;
import com.example.securityfindings.service.FindingService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/api/findings")
public class FindingController {
    private final FindingService findingService;

    public FindingController(FindingService findingService) {
        this.findingService = findingService;
    }

    @GetMapping
    public Page<FindingDto> search(@RequestParam(required = false) UUID projectId,
                                   @RequestParam(required = false) FindingStatus status,
                                   Pageable pageable) {
        return findingService.search(projectId, status, pageable);
    }

    @GetMapping("/{id}")
    public FindingDto get(@PathVariable UUID id) {
        return findingService.getById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','SECURITY_ARCHITECT')")
    @PostMapping
    public FindingDto create(@Valid @RequestBody FindingRequest request) {
        return findingService.create(request);
    }

    @PreAuthorize("hasAnyRole('ADMIN','SECURITY_ARCHITECT','PROJECT_MANAGER')")
    @PutMapping("/{id}")
    public FindingDto update(@PathVariable UUID id, @Valid @RequestBody FindingRequest request) {
        return findingService.update(id, request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        findingService.delete(id);
    }

    @GetMapping("/{findingId}/comments")
    public List<FindingCommentDto> comments(@PathVariable UUID findingId) {
        return findingService.listComments(findingId);
    }

    @PreAuthorize("hasAnyRole('ADMIN','SECURITY_ARCHITECT','PROJECT_MANAGER')")
    @PostMapping("/{findingId}/comments")
    public FindingCommentDto addComment(@PathVariable UUID findingId, @Valid @RequestBody FindingCommentRequest request) {
        return findingService.addComment(findingId, request);
    }

    @GetMapping("/{findingId}/history")
    public List<FindingHistoryDto> history(@PathVariable UUID findingId) {
        return findingService.listHistory(findingId);
    }

    @PreAuthorize("hasAnyRole('ADMIN','SECURITY_ARCHITECT')")
    @PostMapping("/{findingId}/history")
    public FindingHistoryDto addHistory(@PathVariable UUID findingId, @Valid @RequestBody FindingHistoryRequest request) {
        return findingService.addHistory(findingId, request);
    }

    @GetMapping("/{findingId}/evidence")
    public List<EvidenceDto> evidence(@PathVariable UUID findingId) {
        return findingService.listEvidence(findingId);
    }

    @PreAuthorize("hasAnyRole('ADMIN','SECURITY_ARCHITECT','PROJECT_MANAGER')")
    @PostMapping("/{findingId}/evidence")
    public EvidenceDto addEvidence(@PathVariable UUID findingId,
                                   @RequestParam("file") MultipartFile file,
                                   @RequestParam(value = "url", required = false) String url) {
        return findingService.addEvidence(findingId, file, url);
    }
}
