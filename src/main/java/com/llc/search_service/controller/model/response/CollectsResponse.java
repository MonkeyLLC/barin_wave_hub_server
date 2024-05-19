package com.llc.search_service.controller.model.response;

import com.llc.search_service.entity.Collect;
import lombok.Data;

import java.util.List;

@Data
public class CollectsResponse {
    private long total;

    private List<Collect> items;

    @Data
    public static class Collect {
        private Integer id;

        private Integer paperId;

        private String paperName;

        private Integer docType;
    }
}
