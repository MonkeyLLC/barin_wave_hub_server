package com.llc.search_service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.llc.search_service.entity.Collect;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CollectMapper extends BaseMapper<Collect> {
    @Select("select * from collect where user_id = #{userId} and paper_id in (#{paperIds})")
    List<Collect> selectByUserIdAndPaperIds(Integer userId, List<Integer> paperIds);
}
