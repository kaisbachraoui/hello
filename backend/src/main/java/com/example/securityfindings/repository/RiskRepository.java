package com.example.securityfindings.repository;

import com.example.securityfindings.entity.Risk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RiskRepository extends JpaRepository<Risk, String> {
    @Query("select r from Risk r where (:domain is null or r.domain = :domain) and (:keyword is null or " +
            "lower(r.title) like lower(concat('%', :keyword, '%')) or " +
            "lower(r.description) like lower(concat('%', :keyword, '%')))")
    Page<Risk> search(@Param("domain") String domain, @Param("keyword") String keyword, Pageable pageable);
}
