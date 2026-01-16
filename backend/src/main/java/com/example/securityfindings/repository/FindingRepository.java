package com.example.securityfindings.repository;

import com.example.securityfindings.dto.DashboardSummary;
import com.example.securityfindings.entity.Finding;
import com.example.securityfindings.entity.FindingStatus;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FindingRepository extends JpaRepository<Finding, UUID> {
    @Query("select f from Finding f where (:projectId is null or f.project.id = :projectId) and " +
            "(:status is null or f.status = :status)")
    Page<Finding> search(@Param("projectId") UUID projectId, @Param("status") FindingStatus status, Pageable pageable);

    @Query("select new com.example.securityfindings.dto.DashboardSummary$DomainCount(r.domain, count(f)) " +
            "from Finding f join f.risk r group by r.domain")
    List<DashboardSummary.DomainCount> countByDomain();

    @Query("select new com.example.securityfindings.dto.DashboardSummary$SeverityStatusCount(f.severity, f.status, count(f)) " +
            "from Finding f group by f.severity, f.status")
    List<DashboardSummary.SeverityStatusCount> countBySeverityStatus();

    @Query("select new com.example.securityfindings.dto.DashboardSummary$RiskCount(r.riskId, r.title, count(f)) " +
            "from Finding f join f.risk r group by r.riskId, r.title order by count(f) desc")
    List<DashboardSummary.RiskCount> topRisks(Pageable pageable);

    List<Finding> findByProjectId(UUID projectId);
}
