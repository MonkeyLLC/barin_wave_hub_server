package com.llc.search_service.controller.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MessagesResponse {
    private long total;

    private List<Message> messages;

    @Data
    public static class Message {
        private long id;

        private long userId;

        private String title;

        private String content;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private LocalDateTime createdAt;

    }
}
