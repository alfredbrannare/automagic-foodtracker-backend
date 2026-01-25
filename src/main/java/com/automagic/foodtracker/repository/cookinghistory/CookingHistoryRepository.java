package com.automagic.foodtracker.repository.cookinghistory;

import com.automagic.foodtracker.entity.CookingHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface CookingHistoryRepository extends JpaRepository<CookingHistory, String> {
    Collection<CookingHistory> findByUserId(String userId);
    Page<CookingHistory> findByUserIdOrderByCreatedAtDesc(String userId, Pageable pageable);
}
