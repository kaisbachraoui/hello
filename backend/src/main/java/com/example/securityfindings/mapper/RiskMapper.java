package com.example.securityfindings.mapper;

import com.example.securityfindings.dto.RiskDto;
import com.example.securityfindings.dto.RiskRequest;
import com.example.securityfindings.entity.Risk;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RiskMapper {
    RiskDto toDto(Risk risk);

    Risk toEntity(RiskRequest request);

    void update(@MappingTarget Risk risk, RiskRequest request);
}
