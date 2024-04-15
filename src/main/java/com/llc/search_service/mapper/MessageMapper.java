package com.llc.search_service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.llc.search_service.entity.Message;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessageMapper extends BaseMapper<Message> {
}
