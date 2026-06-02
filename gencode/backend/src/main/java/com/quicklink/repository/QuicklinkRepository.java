package com.quicklink.repository;

import com.quicklink.model.Quicklink;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuicklinkRepository extends MongoRepository<Quicklink, String> {
    Optional<Quicklink> findByQuicklink(String quicklink);
    boolean existsByQuicklink(String quicklink);
}
