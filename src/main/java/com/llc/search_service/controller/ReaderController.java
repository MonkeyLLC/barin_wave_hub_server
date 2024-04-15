package com.llc.search_service.controller;

import com.llc.search_service.controller.model.request.ReaderRequest;
import com.llc.search_service.model.response.R;
import com.llc.search_service.service.ReaderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Reader", description = "在线阅读接口")
@RestController
@Slf4j
@RequestMapping("reader")
public class ReaderController {
    private final ReaderService readerService;

    public ReaderController(ReaderService readerService) {
        this.readerService = readerService;
    }

    @Operation(method = "POST", summary = "在线阅读", description = "在线阅读")
    @PostMapping("{id}")
    public R<List<String>> read(@RequestBody ReaderRequest request, @PathVariable Integer id) {
        return R.ok(readerService.read(id,request));
    }
}
