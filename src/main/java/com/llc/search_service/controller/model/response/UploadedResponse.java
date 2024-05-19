package com.llc.search_service.controller.model.response;

import com.llc.search_service.entity.Upload;
import lombok.Data;

import java.util.List;

@Data
public class UploadedResponse {
    private long total;
    private List<Upload> items;
}
