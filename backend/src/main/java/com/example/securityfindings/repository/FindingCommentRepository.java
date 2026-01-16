package com.example.securityfindings.repository;

import com.example.securityfindings.entity.FindingComment;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FindingCommentRepository extends JpaRepository<FindingComment, UUID> {
    List<FindingComment> findByFindingId(UUID findingId);
}
