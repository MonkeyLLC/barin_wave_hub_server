package com.llc.search_service.service;

import com.llc.search_service.controller.model.request.UploadRequest;
import com.llc.search_service.controller.model.response.UploadedResponse;
import com.llc.search_service.domain.auth.AuthUser;
import com.llc.search_service.entity.Paper;
import com.llc.search_service.entity.Upload;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImportService {
    String importSingle(Paper paper, MultipartFile file);

    void upload(AuthUser user, UploadRequest request, MultipartFile file);

    UploadedResponse getUploadRecord(Integer userId, Integer page);

    void uploadRecord(Integer userId, Integer paperId, String name);
}
