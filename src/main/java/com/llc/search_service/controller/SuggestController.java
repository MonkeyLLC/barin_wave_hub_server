package com.llc.search_service.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Suggest", description = "查询词自动推荐")
@RestController
@RequestMapping( "/suggest")
@Slf4j
public class SuggestController {
}
