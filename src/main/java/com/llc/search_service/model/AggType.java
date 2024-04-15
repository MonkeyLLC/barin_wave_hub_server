package com.llc.search_service.model;

public enum AggType {
    TERMS("terms"),
    HISTOGRAM("Histogram");
    private final String aggType;

    public String getAggType() {
        return aggType;
    }

    AggType(String aggType) {
        this.aggType = aggType;
    }

    public static String getAggType(AggType aggType) {
        return aggType.getAggType();
    }
}
