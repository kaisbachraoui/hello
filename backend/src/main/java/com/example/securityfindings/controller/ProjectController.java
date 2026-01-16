package com.example.securityfindings.controller;

import com.example.securityfindings.dto.ProjectDto;
import com.example.securityfindings.dto.ProjectRequest;
import com.example.securityfindings.service.ProjectService;
import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public Page<ProjectDto> list(Pageable pageable) {
        return projectService.list(pageable);
    }

    @GetMapping("/{id}")
    public ProjectDto get(@PathVariable UUID id) {
        return projectService.getById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','SECURITY_ARCHITECT','PROJECT_MANAGER')")
    @PostMapping
    public ProjectDto create(@Valid @RequestBody ProjectRequest request) {
        return projectService.create(request);
    }

    @PreAuthorize("hasAnyRole('ADMIN','SECURITY_ARCHITECT','PROJECT_MANAGER')")
    @PutMapping("/{id}")
    public ProjectDto update(@PathVariable UUID id, @Valid @RequestBody ProjectRequest request) {
        return projectService.update(id, request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        projectService.delete(id);
    }
}
