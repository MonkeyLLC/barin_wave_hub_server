package com.llc.search_service.controller.model.request;


import com.llc.search_service.model.Agg;
import com.llc.search_service.model.SortOrder;
import lombok.Data;

import java.util.List;

@Data
public class SearchPaperRequest {
    private String field;
    private String query;
    private Integer page = 1;
    private Integer size = 10;
    private String sort;
    private String gradeCategory;
    private SortOrder order;
    private List<Filter> filter;
    private List<Agg> aggs;
    private Expense expense;

    public Expense getExpense() {
        if (expense == null) {
            expense = new Expense();
        }
        return expense;
    }

    @Data
    public static class Filter {
        private String field;
        private String filter;
    }

    @Data
    public static class Expense {
        private float min = 0;
        private float max = Float.MAX_VALUE;
    }
}
