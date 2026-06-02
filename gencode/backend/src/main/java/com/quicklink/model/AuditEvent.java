package com.quicklink.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "audit")
public class AuditEvent {
    @Id
    private String id;
    private LocalDateTime eventDate;
    private String eventDesc;
    private String quicklink;
    private String eventType;

    public AuditEvent() {
    }

    public AuditEvent(String eventDesc, String quicklink, String eventType) {
        this.eventDate = LocalDateTime.now();
        this.eventDesc = eventDesc;
        this.quicklink = quicklink;
        this.eventType = eventType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public String getQuicklink() {
        return quicklink;
    }

    public void setQuicklink(String quicklink) {
        this.quicklink = quicklink;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}
