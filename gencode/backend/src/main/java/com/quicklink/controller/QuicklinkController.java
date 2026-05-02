package com.quicklink.controller;

import com.quicklink.model.AuditEvent;
import com.quicklink.model.Quicklink;
import com.quicklink.service.QuicklinkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quicklinks")
public class QuicklinkController {

    private final QuicklinkService quicklinkService;

    public QuicklinkController(QuicklinkService quicklinkService) {
        this.quicklinkService = quicklinkService;
    }

    @GetMapping
    public List<Quicklink> list() {
        return quicklinkService.listAll();
    }

    @PostMapping
    public ResponseEntity<Quicklink> create(@RequestBody Quicklink quicklink) {
        Quicklink saved = quicklinkService.create(quicklink);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{quicklink}")
    public ResponseEntity<Quicklink> update(@PathVariable String quicklink, @RequestBody Quicklink update) {
        Quicklink saved = quicklinkService.update(quicklink, update);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{quicklink}")
    public ResponseEntity<Void> delete(@PathVariable String quicklink) {
        quicklinkService.delete(quicklink);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/audit")
    public List<AuditEvent> audit() {
        return quicklinkService.auditEvents();
    }
}
