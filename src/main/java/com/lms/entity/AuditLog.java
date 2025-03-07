package com.lms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

import com.lms.constant.Operation;


@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@Table(name= "auditLog")
public class AuditLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String entityName;   // Example: Books, Members, Staff
    private Long entityId;       // Example: ISBN for Books, Member ID for Members
    private String entityDetails; // Store JSON or description
    private String operationType; // ADD, MODIFIED, DELETE
    private LocalDateTime timestamp;

    public AuditLog() {}

    public AuditLog(String entityName, Long entityId, String entityDetails, Operation operation) {
        this.entityName = entityName;
        this.entityId = entityId;
        this.entityDetails = entityDetails;
        this.operationType = operation.getValue();
        this.timestamp = LocalDateTime.now();
    }

    // Getters and Setters
}

