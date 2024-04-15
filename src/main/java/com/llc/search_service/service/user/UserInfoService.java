package com.llc.search_service.service.user;

import com.llc.search_service.controller.model.response.UserInfoResponse;
import com.llc.search_service.controller.model.response.UserListResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface UserInfoService {
    UserListResponse userList();

    UserInfoResponse userInfo(Integer userId);

    ResponseEntity<String> uploadAvatar(Integer userId, MultipartFile file);
}
