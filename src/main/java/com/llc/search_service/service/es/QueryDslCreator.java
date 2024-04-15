package com.llc.search_service.service.es;

import com.llc.search_service.controller.model.request.SearchPaperRequest;
import org.elasticsearch.search.builder.SearchSourceBuilder;

public interface QueryDslCreator {
    SearchSourceBuilder creator(SearchPaperRequest request);
}
