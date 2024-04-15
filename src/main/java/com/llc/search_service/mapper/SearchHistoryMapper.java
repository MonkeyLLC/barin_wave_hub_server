package com.llc.search_service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.llc.search_service.entity.SearchHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SearchHistoryMapper extends BaseMapper<SearchHistory> {

    @Select("select query from search_history group by query order by count(*) desc limit 5;")
    List<String> selectTop5GroupByQueryOrderByCount();
}
