package com.llc.search_service.service;

import com.llc.search_service.controller.model.response.DownloadHistoryResponse;
import com.llc.search_service.controller.model.response.SearchHistoryResponse;

import java.util.List;

public interface HistoryService {
    SearchHistoryResponse search(Integer id, Integer page);

    Boolean record(Integer userId, String query);

    List<String> hot();

    DownloadHistoryResponse downloadList(Integer userId, Integer page);

    Boolean view(Integer userId, Integer paperId);
}
