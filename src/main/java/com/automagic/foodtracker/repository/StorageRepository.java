package com.automagic.foodtracker.repository;

import com.automagic.foodtracker.entity.Storage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface StorageRepository extends MongoRepository<Storage, String> {
    Collection<Storage> findByUserId(String userId);
    Optional<Storage> findByIdAndUserId(String id, String userId);
    long countByUserId(String userId);
}
