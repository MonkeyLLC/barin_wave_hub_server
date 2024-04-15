package com.llc.search_service.domain.auth;

import lombok.Data;

import java.util.Date;

@Data
public class AuthUserResponse {
    private Long id;

    private String phone;

    private String email;

    private Date createdAt;

    private Date updatedAt;

    private Date lastLoginAt;

    private Integer available;
}
