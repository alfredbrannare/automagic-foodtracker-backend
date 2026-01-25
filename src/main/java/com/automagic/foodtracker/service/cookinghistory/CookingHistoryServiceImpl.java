package com.automagic.foodtracker.service.cookinghistory;

import com.automagic.foodtracker.entity.CookingHistory;
import com.automagic.foodtracker.repository.cookinghistory.CookingHistoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CookingHistoryServiceImpl implements CookingHistoryService {
    private final CookingHistoryRepository cookingHistoryRepository;

    public CookingHistoryServiceImpl(CookingHistoryRepository cookingHistoryRepository) {
        this.cookingHistoryRepository = cookingHistoryRepository;
    }

    @Override
    public Collection<CookingHistory> getAllCookingHistory(String userId) {
        return cookingHistoryRepository.findByUserId(userId);
    }

    @Override
    public Page<CookingHistory> getCookingHistoryPaginated(String userId, Pageable pageable) {
        return cookingHistoryRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
    }
}
