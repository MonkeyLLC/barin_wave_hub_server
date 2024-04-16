package com.llc.search_service.service;

import com.llc.search_service.controller.model.response.CollectsResponse;

import java.util.List;
import java.util.Map;

public interface CollectService {
    boolean collect(Integer userId, Integer paperId);

    CollectsResponse list(Integer userId, Integer page);

    boolean cancel(Integer userId, Integer paperId);

    Map<Integer,Boolean> isCollect(Integer userId, List<Integer> paperId);
}
