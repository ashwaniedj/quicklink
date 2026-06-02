package com.quicklink.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document(collection = "quicklink")
public class Quicklink {
    @Id
    private String id;
    private String quicklink;
    private String targetUrl;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;
    private List<String> tags = new ArrayList<>();
    private long usageCount;
    private boolean blocked;

    public Quicklink() {
    }

    public Quicklink(String quicklink, String targetUrl, List<String> tags) {
        this.quicklink = quicklink;
        this.targetUrl = targetUrl;
        this.tags = tags != null ? new ArrayList<>(tags) : new ArrayList<>();
        this.creationDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
        this.usageCount = 0;
        this.blocked = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuicklink() {
        return quicklink;
    }

    public void setQuicklink(String quicklink) {
        this.quicklink = quicklink;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags != null ? new ArrayList<>(tags) : new ArrayList<>();
    }

    public long getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(long usageCount) {
        this.usageCount = usageCount;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quicklink quicklink1 = (Quicklink) o;
        return Objects.equals(id, quicklink1.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
