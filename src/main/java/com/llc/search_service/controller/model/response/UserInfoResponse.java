package com.llc.search_service.controller.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoResponse {

    private Long id;

    private String nickname;

    private String username;

    private String phone;

    private String email;

    private String qq;

    private String avatarUrl;

    private Date createdAt;

    private Date updatedAt;

    private String lastLoginAt;

    private Integer available;
}
