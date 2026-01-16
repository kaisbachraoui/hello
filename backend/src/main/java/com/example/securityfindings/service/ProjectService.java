package com.example.securityfindings.service;

import com.example.securityfindings.dto.ProjectDto;
import com.example.securityfindings.dto.ProjectRequest;
import com.example.securityfindings.entity.Project;
import com.example.securityfindings.mapper.ProjectMapper;
import com.example.securityfindings.repository.ProjectRepository;
import com.example.securityfindings.util.ResourceNotFoundException;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public ProjectService(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    public Page<ProjectDto> list(Pageable pageable) {
        return projectRepository.findAll(pageable).map(projectMapper::toDto);
    }

    public ProjectDto getById(UUID id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found: " + id));
        return projectMapper.toDto(project);
    }

    @Transactional
    public ProjectDto create(ProjectRequest request) {
        Project project = projectMapper.toEntity(request);
        return projectMapper.toDto(projectRepository.save(project));
    }

    @Transactional
    public ProjectDto update(UUID id, ProjectRequest request) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found: " + id));
        projectMapper.update(project, request);
        return projectMapper.toDto(projectRepository.save(project));
    }

    @Transactional
    public void delete(UUID id) {
        if (!projectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Project not found: " + id);
        }
        projectRepository.deleteById(id);
    }
}
