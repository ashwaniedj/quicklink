package com.quicklink.service;

import com.quicklink.model.AuditEvent;
import com.quicklink.model.Quicklink;
import com.quicklink.repository.AuditEventRepository;
import com.quicklink.repository.QuicklinkRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class QuicklinkService {

    private final QuicklinkRepository quicklinkRepository;
    private final AuditEventRepository auditEventRepository;

    public QuicklinkService(QuicklinkRepository quicklinkRepository, AuditEventRepository auditEventRepository) {
        this.quicklinkRepository = quicklinkRepository;
        this.auditEventRepository = auditEventRepository;
    }

    public List<Quicklink> listAll() {
        return quicklinkRepository.findAll();
    }

    public Quicklink create(Quicklink quicklink) {
        if (!StringUtils.hasText(quicklink.getQuicklink()) || !StringUtils.hasText(quicklink.getTargetUrl())) {
            throw new IllegalArgumentException("quicklink and targetUrl are required");
        }
        if (quicklinkRepository.existsByQuicklink(quicklink.getQuicklink())) {
            throw new IllegalStateException("quicklink already exists");
        }
        quicklink.setCreationDate(java.time.LocalDateTime.now());
        quicklink.setUpdateDate(java.time.LocalDateTime.now());
        quicklink.setUsageCount(0);
        quicklink.setBlocked(false);
        Quicklink saved = quicklinkRepository.save(quicklink);
        auditEventRepository.save(new AuditEvent("Created quicklink", saved.getQuicklink(), "CREATE"));
        return saved;
    }

    public Quicklink update(String quicklinkKey, Quicklink update) {
        Quicklink existing = quicklinkRepository.findByQuicklink(quicklinkKey)
                .orElseThrow(() -> new IllegalStateException("Quicklink not found"));
        if (StringUtils.hasText(update.getTargetUrl())) {
            existing.setTargetUrl(update.getTargetUrl());
        }
        if (update.getTags() != null) {
            existing.setTags(update.getTags());
        }
        existing.setBlocked(update.isBlocked());
        existing.setUpdateDate(java.time.LocalDateTime.now());
        Quicklink saved = quicklinkRepository.save(existing);
        auditEventRepository.save(new AuditEvent("Updated quicklink", saved.getQuicklink(), "UPDATE"));
        return saved;
    }

    public void delete(String quicklinkKey) {
        Quicklink existing = quicklinkRepository.findByQuicklink(quicklinkKey)
                .orElseThrow(() -> new IllegalStateException("Quicklink not found"));
        quicklinkRepository.delete(existing);
        auditEventRepository.save(new AuditEvent("Deleted quicklink", existing.getQuicklink(), "DELETE"));
    }

    public List<AuditEvent> auditEvents() {
        return auditEventRepository.findAll();
    }
}
