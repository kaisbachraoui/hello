package com.example.securityfindings.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import com.example.securityfindings.util.AuditEntityListener;

@Entity
@Table(name = "finding_history")
@EntityListeners(AuditEntityListener.class)
public class FindingHistory {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "finding_id")
    private Finding finding;

    @Column(nullable = false)
    private String actor;

    @Enumerated(EnumType.STRING)
    private FindingStatus fromStatus;

    @Enumerated(EnumType.STRING)
    private FindingStatus toStatus;

    @Column(nullable = false)
    private String action;

    @Column(columnDefinition = "text")
    private String details;

    @Column(nullable = false)
    private Instant createdAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Finding getFinding() {
        return finding;
    }

    public void setFinding(Finding finding) {
        this.finding = finding;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public FindingStatus getFromStatus() {
        return fromStatus;
    }

    public void setFromStatus(FindingStatus fromStatus) {
        this.fromStatus = fromStatus;
    }

    public FindingStatus getToStatus() {
        return toStatus;
    }

    public void setToStatus(FindingStatus toStatus) {
        this.toStatus = toStatus;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
