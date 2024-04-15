package com.llc.search_service.service.es.Impl;


import com.llc.search_service.controller.model.request.SearchPaperRequest;
import com.llc.search_service.model.Agg;
import com.llc.search_service.model.FieldMapping;
import com.llc.search_service.service.es.AggBuilder;
import com.llc.search_service.service.es.QueryDslCreator;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class QueryDslCreatorImpl implements QueryDslCreator {
    private final AggBuilder aggBuilder;

    public QueryDslCreatorImpl(AggBuilder aggBuilder) {
        this.aggBuilder = aggBuilder;
    }

    /**
     * @param request
     * @return
     * @Author 李利超
     */
    @Override
    public SearchSourceBuilder creator(SearchPaperRequest request) {
        QueryBuilder queryBuilder;
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        int from = (request.getPage() - 1) * request.getSize();
        from = Math.max(from, 0);

        if (!StringUtils.hasText(request.getField()) || !StringUtils.hasText(request.getQuery())) {
            queryBuilder = QueryBuilders.matchAllQuery();
        } else {
            FieldMapping fieldMapping = FieldMapping.getFiled(request.getField());

            String query = request.getQuery();

            queryBuilder = QueryBuilders.matchQuery(fieldMapping.getFiled(), query);

            queryBuilder = handleExpense(queryBuilder, request.getExpense());

            queryBuilder = handleGradeCategory(queryBuilder, request.getGradeCategory());

            queryBuilder = handleFilter(queryBuilder, request);

        }
        sourceBuilder.query(queryBuilder).from(from).size(request.getSize());

        sourceBuilder = createAgg(sourceBuilder, request.getAggs());

        String sort = request.getSort();

        if (sort != null) {
            FieldMapping filed = FieldMapping.getFiled(sort);

            sourceBuilder.sort(filed.getFiled(), request.getOrder().getValue());
        }

        return sourceBuilder;
    }

    private QueryBuilder handleGradeCategory(QueryBuilder queryBuilder, String gradeCategory) {
        if (StringUtils.hasText(gradeCategory)) {
            TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(FieldMapping.GRADE_CATEGORY.getFiled(), gradeCategory);
            queryBuilder = QueryBuilders.boolQuery().must(queryBuilder).must(termQueryBuilder);
            return queryBuilder;
        }
        return queryBuilder;
    }

    /**
     * 处理过滤
     *
     * @param queryBuilder
     * @param request
     * @return
     * @Author 李利超
     */
    private QueryBuilder handleFilter(QueryBuilder queryBuilder, SearchPaperRequest request) {

        List<SearchPaperRequest.Filter> filters = request.getFilter();
        if (filters == null || filters.isEmpty()) {
            return queryBuilder;
        }

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        for (SearchPaperRequest.Filter filter : filters) {
            FieldMapping filed = FieldMapping.getFiled(filter.getField());

            TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(filed.getKeyword(), filter.getFilter());

            boolQueryBuilder.must(termQueryBuilder);

        }

        return QueryBuilders.boolQuery().must(queryBuilder).filter(boolQueryBuilder);
    }

    /***
     * 处理费用区间
     * @Author 李利超
     * @param queryBuilder
     * @param expense
     * @return
     */
    private QueryBuilder handleExpense(QueryBuilder queryBuilder, SearchPaperRequest.Expense expense) {

        QueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(FieldMapping.EXPENSE.getFiled()).gte(expense.getMin()).lte(expense.getMax());

        return QueryBuilders.boolQuery().must(queryBuilder).must(rangeQueryBuilder);
    }

    private SearchSourceBuilder createAgg(SearchSourceBuilder sourceBuilder, List<Agg> aggs) {

        if (aggs == null || aggs.isEmpty()) {
            return sourceBuilder;
        }

        List<AggregationBuilder> aggregationBuilders = aggBuilder.build(aggs);

        for (AggregationBuilder aggregationBuilder : aggregationBuilders) {
            sourceBuilder.aggregation(aggregationBuilder);
        }

        return sourceBuilder;
    }
}
