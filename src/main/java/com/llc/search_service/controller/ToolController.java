package com.llc.search_service.controller;


import com.llc.search_service.model.response.R;
import com.llc.search_service.service.ToolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Tool", description = "工具类")
@RestController
@RequestMapping("tool")
@Slf4j
public class ToolController {
    private final ToolService toolService;

    public ToolController(ToolService toolService) {
        this.toolService = toolService;
    }

    @Operation(method = "POST", summary = "文件上传", description = "文件上传")
    @PostMapping("/upload")
    public R<String> search(@RequestBody MultipartFile file, @RequestParam(required = false) Integer id) {
        String aliYunOssUrl = toolService.upload(file, id);

        log.info("文件上传成功：{}", aliYunOssUrl);
        return R.ok(aliYunOssUrl);
    }
}
