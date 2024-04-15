package com.llc.search_service.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class BucketTermsAggResult extends BucketAggResult {

    Map<String, List<Bucket>> aggs;

    @Data
    public static class Bucket {
        private String key;
        private long count;
        private Bucket children;
    }


}
