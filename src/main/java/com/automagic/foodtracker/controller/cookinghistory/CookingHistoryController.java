package com.automagic.foodtracker.controller.cookinghistory;

import com.automagic.foodtracker.dto.response.cookinghistory.CookingHistoryResponse;
import com.automagic.foodtracker.entity.CookingHistory;
import com.automagic.foodtracker.mapper.cookinghistory.CookingHistoryMapper;
import com.automagic.foodtracker.security.AuthenticatedUser;
import com.automagic.foodtracker.service.cookinghistory.CookingHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cooking-history")
@RequiredArgsConstructor
public class CookingHistoryController {
    CookingHistoryService cookingHistoryService;

    @GetMapping
    public ResponseEntity<Page<CookingHistoryResponse>> getCookingHistory(
            @AuthenticationPrincipal AuthenticatedUser user,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ){
        Page<CookingHistory> page = cookingHistoryService.getCookingHistoryPaginated(user.getUserId(), pageable);

        Page<CookingHistoryResponse> response = page.map(CookingHistoryMapper::toResponse);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
