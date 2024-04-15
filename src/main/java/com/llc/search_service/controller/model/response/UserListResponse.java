package com.llc.search_service.controller.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class UserListResponse {

    private Integer total;

    private List<UserInfo> items;

    @Data
    public static class UserInfo {
        private Long id;

        private String username;

        private String phone;

        private String email;

        private Date createdAt;

        private Date updatedAt;

        private String lastLoginAt;

        private Integer available;
    }


}
