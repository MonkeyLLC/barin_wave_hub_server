package com.llc.search_service.service.es.Impl;


import com.llc.search_service.model.Agg;
import com.llc.search_service.model.FieldMapping;
import com.llc.search_service.service.es.AggCreate;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.springframework.stereotype.Component;


@Component
public class TermsAggCreate implements AggCreate {
    @Override
    public AggregationBuilder aggCreate(Agg agg) {

        // 目前只对只有一级聚合查询的情况做处理

        if (agg.getSubAgg() != null) {
            return null;
        }

        String aggName = agg.getAggName();
        String aggKeyword = FieldMapping.getFiled(aggName).getKeyword();

        TermsAggregationBuilder aggregationBuilder = AggregationBuilders.terms(aggName).field(aggKeyword).size(agg.getSize());

        return aggregationBuilder;
    }
}
