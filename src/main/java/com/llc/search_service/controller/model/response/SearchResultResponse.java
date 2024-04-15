package com.llc.search_service.controller.model.response;

import com.llc.search_service.entity.EsPaper;
import com.llc.search_service.model.AggResult;
import lombok.Data;

import java.util.List;

@Data
public class SearchResultResponse {
    private List<EsPaper> hits;
    private AggResult aggResult;
    private long total;
}