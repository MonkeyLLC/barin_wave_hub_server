package com.llc.search_service.controller;

import com.llc.search_service.controller.model.response.CollectsResponse;
import com.llc.search_service.domain.auth.AuthUser;
import com.llc.search_service.model.response.R;
import com.llc.search_service.service.CollectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "CollectController", description = "收藏")
@RestController
@Slf4j
@RequestMapping("collect")
public class CollectController {
    private final CollectService collectService;

    public CollectController(CollectService collectService) {
        this.collectService = collectService;
    }

    @PutMapping()
    @Operation(method = "PUT", summary = "收藏")
    public R<Boolean> collect(@AuthenticationPrincipal AuthUser user, @RequestParam Integer paperId) {
        boolean collect = collectService.collect(user.getId(), paperId);
        return R.ok(collect);
    }

    @GetMapping
    @Operation(method = "GET", summary = "获取收藏列表")
    public R<CollectsResponse> list(@AuthenticationPrincipal AuthUser user, @RequestParam Integer page) {
        CollectsResponse collectsResponse = collectService.list(user.getId(), page);
        return R.ok(collectsResponse);
    }

    @DeleteMapping
    @Operation(method = "DELETE", summary = "取消收藏")
    public R<Boolean> cancel(@AuthenticationPrincipal AuthUser user, @RequestParam Integer paperId) {
        boolean cancel = collectService.cancel(user.getId(), paperId);
        return R.ok(cancel);
    }

    @PostMapping("/is-collect")
    @Operation(method = "POST", summary = "是否收藏")
    public R<Map<Integer, Boolean>> isCollect(@AuthenticationPrincipal AuthUser user, @RequestBody List<Integer> paperIds) {
        if (user == null) {
            return R.ok();
        }
        Map<Integer, Boolean> isCollect = collectService.isCollect(user.getId(), paperIds);
        return R.ok(isCollect);
    }
}
