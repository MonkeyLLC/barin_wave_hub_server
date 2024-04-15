package com.llc.search_service.controller;

import com.llc.search_service.controller.model.response.UserInfoResponse;
import com.llc.search_service.controller.model.response.UserListResponse;
import com.llc.search_service.domain.auth.AuthUser;
import com.llc.search_service.model.response.R;
import com.llc.search_service.service.user.UserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserInfoService userInfoService;

    public UserController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @Operation(method = "GET", summary = "查询用户列表")
    @GetMapping("/list")
    public R<UserListResponse> search(@AuthenticationPrincipal AuthUser authUser) {
        System.out.println(authUser);
        return R.ok(userInfoService.userList());
    }

    @Operation(method = "GET", summary = "用户信息")
    @GetMapping("/info")
    public R<UserInfoResponse> info(@AuthenticationPrincipal AuthUser authUser) {
        return R.ok(userInfoService.userInfo(authUser.getId()));
    }

    @Operation(method = "PUT", summary = "上传头像")
    @PutMapping("/uploadAvatar")
    public String uploadAvatar(@AuthenticationPrincipal AuthUser authUser, MultipartFile file) {
        ResponseEntity<String> base64 = userInfoService.uploadAvatar(authUser.getId(), file);
        return base64.getBody();
    }
}
