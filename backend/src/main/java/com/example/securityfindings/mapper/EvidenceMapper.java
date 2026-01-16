package com.example.securityfindings.mapper;

import com.example.securityfindings.dto.EvidenceDto;
import com.example.securityfindings.entity.Evidence;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EvidenceMapper {
    @Mapping(target = "findingId", source = "finding.id")
    EvidenceDto toDto(Evidence evidence);
}
