package com.llc.search_service.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.llc.search_service.entity.Paper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EsMapper extends BaseMapper<Paper> {
}
