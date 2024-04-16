package com.llc.search_service.controller;

import com.llc.search_service.service.DownloadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "DownloadController", description = "下载")
@RestController
@Slf4j
@RequestMapping("download")
public class DownloadController {
    private final DownloadService downloadService;

    public DownloadController(DownloadService downloadService) {
        this.downloadService = downloadService;
    }

    @GetMapping
    @Operation(method = "GET", summary = "下载")
    public void download(/*@AuthenticationPrincipal AuthUser user,*/ @RequestParam Integer paperId, HttpServletResponse response) {
        downloadService.download(1, paperId, response);
    }
}
