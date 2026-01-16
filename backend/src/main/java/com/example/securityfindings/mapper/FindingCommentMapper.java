package com.example.securityfindings.mapper;

import com.example.securityfindings.dto.FindingCommentDto;
import com.example.securityfindings.entity.FindingComment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FindingCommentMapper {
    @Mapping(target = "findingId", source = "finding.id")
    FindingCommentDto toDto(FindingComment comment);
}
