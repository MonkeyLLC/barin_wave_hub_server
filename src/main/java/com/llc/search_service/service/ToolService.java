package com.llc.search_service.service;

import org.springframework.web.multipart.MultipartFile;

public interface ToolService {
    String upload(MultipartFile file, Integer id);
}
