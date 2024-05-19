package com.llc.search_service.service;


import com.llc.search_service.controller.model.request.SearchPaperRequest;
import com.llc.search_service.controller.model.response.SearchResultResponse;

public interface SearchService {
    SearchResultResponse search(SearchPaperRequest request);

    long count();

    SearchResultResponse searchById(Integer id);
}
