package com.llc.search_service.controller.model.response;

import com.llc.search_service.entity.DownloadHistory;
import lombok.Data;

import java.util.List;

@Data
public class DownloadHistoryResponse {
    private long total;

    private List<DownloadHistory> items;
}
