package com.llc.search_service.service.es;


import com.llc.search_service.model.Agg;
import org.elasticsearch.search.aggregations.AggregationBuilder;

import java.util.List;

public interface AggBuilder {
    List<AggregationBuilder> build(List<Agg> aggs);
}
