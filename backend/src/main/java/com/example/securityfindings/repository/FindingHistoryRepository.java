package com.example.securityfindings.repository;

import com.example.securityfindings.entity.FindingHistory;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FindingHistoryRepository extends JpaRepository<FindingHistory, UUID> {
    List<FindingHistory> findByFindingId(UUID findingId);
}
