package com.example.securityfindings.mapper;

import com.example.securityfindings.dto.FindingDto;
import com.example.securityfindings.entity.Finding;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FindingMapper {
    @Mapping(target = "projectId", source = "project.id")
    @Mapping(target = "projectName", source = "project.name")
    @Mapping(target = "riskId", source = "risk.riskId")
    @Mapping(target = "riskTitle", source = "risk.title")
    FindingDto toDto(Finding finding);
}
