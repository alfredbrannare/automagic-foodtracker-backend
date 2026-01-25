package com.automagic.foodtracker.service.cookinghistory;

import com.automagic.foodtracker.entity.CookingHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CookingHistoryService {
    Collection<CookingHistory> getAllCookingHistory(String userId);
    Page<CookingHistory> getCookingHistoryPaginated(String userId, Pageable pageable);
}
