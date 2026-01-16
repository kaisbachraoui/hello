package com.example.securityfindings.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import com.example.securityfindings.util.AuditEntityListener;

@Entity
@Table(name = "risks")
@EntityListeners(AuditEntityListener.class)
public class Risk {
    @Id
    @Column(name = "risk_id", nullable = false, updatable = false)
    private String riskId;

    @Column(nullable = false)
    private String domain;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "text")
    private String description;

    @Column(columnDefinition = "text")
    private String typicalFindingExamples;

    @Column(columnDefinition = "text")
    private String annexAMapping;

    public String getRiskId() {
        return riskId;
    }

    public void setRiskId(String riskId) {
        this.riskId = riskId;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTypicalFindingExamples() {
        return typicalFindingExamples;
    }

    public void setTypicalFindingExamples(String typicalFindingExamples) {
        this.typicalFindingExamples = typicalFindingExamples;
    }

    public String getAnnexAMapping() {
        return annexAMapping;
    }

    public void setAnnexAMapping(String annexAMapping) {
        this.annexAMapping = annexAMapping;
    }
}
