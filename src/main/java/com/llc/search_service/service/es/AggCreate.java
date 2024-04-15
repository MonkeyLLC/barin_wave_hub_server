package com.llc.search_service.service.es;


import com.llc.search_service.model.Agg;
import org.elasticsearch.search.aggregations.AggregationBuilder;

public interface AggCreate {
    AggregationBuilder aggCreate(Agg agg);
}
