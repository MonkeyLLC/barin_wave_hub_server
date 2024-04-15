package com.llc.search_service.service.es.Impl;


import com.llc.search_service.model.Agg;
import com.llc.search_service.model.AggType;
import com.llc.search_service.service.es.AggBuilder;
import com.llc.search_service.service.es.AggCreateContext;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AggBuilderImpl implements AggBuilder {
    private final TermsAggCreate termsAggCreate;

    public AggBuilderImpl(TermsAggCreate termsAggCreate) {
        this.termsAggCreate = termsAggCreate;
    }

    @Override
    public List<AggregationBuilder> build(List<Agg> aggs) {
        AggCreateContext context = new AggCreateContext();
        List<AggregationBuilder> aggregationBuilders = new ArrayList<>();
        for (Agg agg : aggs) {
            AggregationBuilder aggregationBuilder = null;
            if (agg.getAggType().equals(AggType.TERMS)) {
                context.setAggCreate(termsAggCreate);
                aggregationBuilder = context.execute(agg);
            }
            aggregationBuilders.add(aggregationBuilder);
        }
        return aggregationBuilders;
    }

    public AggregationBuilder build(Agg agg) {

        return null;
    }
}
