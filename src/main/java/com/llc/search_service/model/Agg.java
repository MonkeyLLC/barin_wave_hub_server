package com.llc.search_service.model;

import lombok.Data;


@Data
public class Agg {
    private String aggName;
    private Integer size;
    private String sort;
    private SortOrder order;
    private AggType aggType = AggType.TERMS;
    private Agg subAgg;
}
