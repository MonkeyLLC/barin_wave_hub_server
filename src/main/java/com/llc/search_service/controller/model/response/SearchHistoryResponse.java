package com.llc.search_service.controller.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class SearchHistoryResponse {
    private long total;

    private List<String> items;

    @Data
    public static class SearchHistory {
        private String id;

        private String query;

        @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
        private LocalDate createdAt;
    }
}
