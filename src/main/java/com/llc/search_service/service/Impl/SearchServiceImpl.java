package com.llc.search_service.service.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.llc.search_service.constants.EsConstants;
import com.llc.search_service.controller.model.request.SearchPaperRequest;
import com.llc.search_service.controller.model.response.SearchResultResponse;
import com.llc.search_service.entity.EsPaper;
import com.llc.search_service.model.Agg;
import com.llc.search_service.model.AggResult;
import com.llc.search_service.model.BucketTermsAggResult;
import com.llc.search_service.service.SearchService;
import com.llc.search_service.service.es.QueryDslCreator;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SearchServiceImpl implements SearchService {
    private final RestHighLevelClient client;
    private final ObjectMapper objectMapper;
    private final QueryDslCreator queryDslCreator;

    public SearchServiceImpl(RestHighLevelClient client, ObjectMapper objectMapper, QueryDslCreator queryDslCreator) {
        this.client = client;
        this.objectMapper = objectMapper;
        this.queryDslCreator = queryDslCreator;
    }

    @Override
    public SearchResultResponse search(SearchPaperRequest request) {

        SearchSourceBuilder sourceBuilder = queryDslCreator.creator(request);

        log.info("\nquery dsl :\n{}", sourceBuilder);

        SearchRequest searchRequest = new SearchRequest();

        searchRequest.indices(EsConstants.INDEX_NAME);

        searchRequest.source(sourceBuilder);
        SearchResponse search;
        SearchResultResponse searchResultResponse = new SearchResultResponse();
        try {
            search = client.search(searchRequest, RequestOptions.DEFAULT);

            Aggregations aggregations = search.getAggregations();

            AggResult aggResult = converter(aggregations, request.getAggs());
            searchResultResponse.setAggResult(aggResult);

            long total = search.getHits().getTotalHits();

            SearchHits hits = search.getHits();
            SearchHit[] hitsHits = hits.getHits();

            searchResultResponse.setTotal(total);

            List<EsPaper> records = new ArrayList<>();
            for (SearchHit hitsHit : hitsHits) {
                Map<String, Object> sourceAsMap = hitsHit.getSourceAsMap();
                EsPaper esPaper = setEsPaper(sourceAsMap);
                records.add(esPaper);
            }
            searchResultResponse.setHits(records);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return searchResultResponse;
    }

    private AggResult converter(Aggregations aggregations, List<Agg> aggsRequest) {

        if (aggsRequest== null||aggsRequest.isEmpty()){
            return null;
        }

        Map<String, List<BucketTermsAggResult.Bucket>> aggs = new LinkedHashMap<>();

        for (Agg agg : aggsRequest) {
            String aggName = agg.getAggName();
            Terms terms = aggregations.get(aggName);
            List<? extends Terms.Bucket> buckets = terms.getBuckets();

            List<BucketTermsAggResult.Bucket> bucketList = new ArrayList<>();

            for (Terms.Bucket bucket : buckets) {
                BucketTermsAggResult.Bucket bucketResult = new BucketTermsAggResult.Bucket();
                long count = bucket.getDocCount();
                String key = bucket.getKeyAsString();

                bucketResult.setKey(key);
                bucketResult.setCount(count);

                bucketList.add(bucketResult);
            }

            aggs.put(aggName, bucketList);
        }

        BucketTermsAggResult aggResult = new BucketTermsAggResult();
        aggResult.setAggs(aggs);

        return aggResult;
    }

    @Override
    public long count() {
        CountRequest request = new CountRequest(EsConstants.INDEX_NAME);

        try {
            CountResponse count = client.count(request, RequestOptions.DEFAULT);
            return count.getCount();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private SearchResponse quadraticSearch(String query) {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(EsConstants.INDEX_NAME);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchPhraseQuery("name", query));
        sourceBuilder.from(0);
        sourceBuilder.size(10);
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse;
        try {
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return searchResponse;
    }


    public EsPaper setEsPaper(Map<String, Object> sourceAsMap) {


        EsPaper esPaper = objectMapper.convertValue(sourceAsMap, EsPaper.class);
        return esPaper;
    }
}
