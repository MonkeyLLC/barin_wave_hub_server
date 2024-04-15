package com.llc.search_service.service.es;

import com.llc.search_service.model.Agg;
import org.elasticsearch.search.aggregations.AggregationBuilder;

public class AggCreateContext {

    private AggCreate aggCreate;

    public void setAggCreate(AggCreate aggCreate) {
        this.aggCreate = aggCreate;
    }

    public AggregationBuilder execute(Agg agg) {
        if (aggCreate != null) {
            return aggCreate.aggCreate(agg);
        } else {
            return null;
        }
    }
}
