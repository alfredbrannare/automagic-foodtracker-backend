package com.automagic.foodtracker.repository.storage;

import com.automagic.foodtracker.entity.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface StorageRepository extends JpaRepository<Storage, String> {
    Collection<Storage> findByUserId(String userId);
    Optional<Storage> findByIdAndUserId(String id, String userId);
    long countByUserId(String userId);
    void deleteAllByUserId(String userId);
}
