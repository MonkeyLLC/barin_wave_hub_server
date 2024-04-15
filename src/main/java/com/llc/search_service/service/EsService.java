package com.llc.search_service.service;


import com.llc.search_service.entity.Paper;
import org.springframework.web.multipart.MultipartFile;

public interface EsService {
    void addDoc(Integer id);

    void addDocAll(Integer minId, Integer maxId);

    boolean addDocToDao(Paper paper, MultipartFile file);
}
