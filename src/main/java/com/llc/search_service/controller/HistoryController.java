package com.llc.search_service.controller;

import com.llc.search_service.controller.model.request.SearchHistoryRequest;
import com.llc.search_service.controller.model.response.SearchHistoryResponse;
import com.llc.search_service.domain.auth.AuthUser;
import com.llc.search_service.model.response.R;
import com.llc.search_service.service.HistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "HistoryController", description = "历史记录")
@RestController
@Slf4j
@RequestMapping("history")
public class HistoryController {
    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping("search")
    @Operation(method = "GET", summary = "获取检索记录")
    public R<SearchHistoryResponse> search(@AuthenticationPrincipal AuthUser user, @RequestParam Integer page) {
        log.info("获取检索记录");
        return R.ok(historyService.search(user.getId(), page));
    }

    @PutMapping()
    @Operation(method = "PUT", summary = "记录检索")
    public R<Boolean> record(@AuthenticationPrincipal AuthUser user, @RequestBody SearchHistoryRequest request) {
        String query = request.getQuery();
        if (!StringUtils.hasText(query)) {
            return R.ok(false);
        }
        Boolean b = historyService.record(user.getId(), query);
        return R.ok(b);
    }

    @GetMapping("hot")
    @Operation(method = "GET", summary = "获取热门检索")
    public R<List<String>> hot() {
        return R.ok(historyService.hot());
    }

}
