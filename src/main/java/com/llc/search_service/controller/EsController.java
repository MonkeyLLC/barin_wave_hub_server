package com.llc.search_service.controller;


import com.llc.search_service.entity.Paper;
import com.llc.search_service.model.response.R;
import com.llc.search_service.service.EsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "es", description = "向es添加数据")
@RestController
@RequestMapping("es")
@Slf4j
public class EsController {

    private final EsService esService;

    public EsController(EsService esService) {
        this.esService = esService;
    }

    @Operation(method = "POST", summary = "添加一个文档", description = "添加一个文档")
    @PostMapping("/addOne")
    public R<Void> addDocOne(Integer id) {
        esService.addDoc(id);
        return R.ok(null, "添加成功");
    }

    @Operation(method = "POST", summary = "添加所有文档", description = "添加所有文档")
    @PostMapping("/addAll")
    public R<Void> addDocAll(@RequestParam(required = false) Integer minId, @RequestParam(required = false) Integer maxId) {
        esService.addDocAll(minId, maxId);
        return R.ok(null, "添加成功");
    }


    @Operation(method = "POST", summary = "添加一张试卷到数据库", description = "添加一张试卷到数据库")
    @PostMapping(value = "/addDocToDao")
    public R<Boolean> addDocToDao(@RequestPart MultipartFile file, Paper paper) {

        return R.ok(esService.addDocToDao(paper, file), "添加成功");
    }


}
