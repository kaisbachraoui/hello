package com.example.securityfindings.repository;

import com.example.securityfindings.entity.Evidence;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvidenceRepository extends JpaRepository<Evidence, UUID> {
    List<Evidence> findByFindingId(UUID findingId);
}
