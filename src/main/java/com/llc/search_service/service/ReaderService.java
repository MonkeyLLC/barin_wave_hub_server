package com.llc.search_service.service;

import com.llc.search_service.controller.model.request.ReaderRequest;

import java.util.List;

public interface ReaderService {
    List<String> read(Integer id,ReaderRequest request);
}
