package com.llc.search_service.model;

import lombok.Getter;

@Getter
public enum SortOrder {
    DESC("desc", org.elasticsearch.search.sort.SortOrder.DESC),
    ASC("asc", org.elasticsearch.search.sort.SortOrder.ASC);
    private final String key;
    private final org.elasticsearch.search.sort.SortOrder value;

    SortOrder(String key, org.elasticsearch.search.sort.SortOrder value) {
        this.key = key;
        this.value = value;
    }
}