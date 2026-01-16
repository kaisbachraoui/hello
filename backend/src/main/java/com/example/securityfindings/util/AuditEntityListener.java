package com.example.securityfindings.util;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuditEntityListener {
    private static final Logger logger = LoggerFactory.getLogger(AuditEntityListener.class);

    @PostPersist
    public void onCreate(Object entity) {
        logger.info("Created entity {}", entity.getClass().getSimpleName());
    }

    @PostUpdate
    public void onUpdate(Object entity) {
        logger.info("Updated entity {}", entity.getClass().getSimpleName());
    }

    @PostRemove
    public void onDelete(Object entity) {
        logger.info("Deleted entity {}", entity.getClass().getSimpleName());
    }
}
