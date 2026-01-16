package com.example.securityfindings.mapper;

import com.example.securityfindings.dto.FindingHistoryDto;
import com.example.securityfindings.entity.FindingHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FindingHistoryMapper {
    @Mapping(target = "findingId", source = "finding.id")
    FindingHistoryDto toDto(FindingHistory history);
}
