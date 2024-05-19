package com.llc.search_service.controller;


import com.llc.search_service.controller.model.request.SearchPaperRequest;
import com.llc.search_service.controller.model.response.SearchResultResponse;
import com.llc.search_service.model.response.R;
import com.llc.search_service.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Search", description = "查询")
@RestController
@RequestMapping("search")
@Slf4j

public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @Operation(method = "POST", summary = "查询", description = "查询")
    @PostMapping
    public R<SearchResultResponse> search(@RequestBody SearchPaperRequest request) {

        return R.ok(searchService.search(request));
    }

    @Operation(method = "GET", summary = "查询总数", description = "查询总数")
    @GetMapping("/count")
    public R<Long> count() {
        return R.ok(searchService.count());
    }

    @GetMapping("{id}")
    @Operation(method = "GET", summary = "根据id查询", description = "根据id查询")
    public R<SearchResultResponse> searchById(@PathVariable Integer id) {
        return R.ok(searchService.searchById(id));
    }
}
