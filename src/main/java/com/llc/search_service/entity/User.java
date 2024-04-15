package com.llc.search_service.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class User {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String nickname;

    private String username;

    private String password;

    private String phone;

    private String email;

    private String qq;

    private String avatarUrl;

    private Date createdAt;

    private Date updatedAt;

    private Date lastLoginAt;

    private Integer available;
}
