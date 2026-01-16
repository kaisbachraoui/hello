package com.example.securityfindings.mapper;

import com.example.securityfindings.dto.ProjectDto;
import com.example.securityfindings.dto.ProjectRequest;
import com.example.securityfindings.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    ProjectDto toDto(Project project);

    Project toEntity(ProjectRequest request);

    void update(@MappingTarget Project project, ProjectRequest request);
}
