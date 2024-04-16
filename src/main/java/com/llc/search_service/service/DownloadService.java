package com.llc.search_service.service;

import jakarta.servlet.http.HttpServletResponse;

public interface DownloadService {
    void download(Integer userId, Integer paperId, HttpServletResponse response);
}
