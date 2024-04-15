package com.llc.search_service.controller;

import com.llc.search_service.controller.model.response.MessagesResponse;
import com.llc.search_service.domain.auth.AuthUser;
import com.llc.search_service.model.response.R;
import com.llc.search_service.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Message", description = "消息接口")
@RestController
@Slf4j
@RequestMapping("message")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @Operation(method = "GET", summary = "获取消息列表", description = "消息列表")
    @GetMapping("list")
    public R<MessagesResponse> list(@AuthenticationPrincipal AuthUser user, @RequestParam Integer page) {
        return R.ok(messageService.list(user.getId(), page));
    }
}
