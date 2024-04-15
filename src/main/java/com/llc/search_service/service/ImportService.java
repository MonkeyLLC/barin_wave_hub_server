package com.llc.search_service.service;

import com.llc.search_service.entity.Paper;
import org.springframework.web.multipart.MultipartFile;

public interface ImportService {
    String importSingle(Paper paper, MultipartFile file);
}
