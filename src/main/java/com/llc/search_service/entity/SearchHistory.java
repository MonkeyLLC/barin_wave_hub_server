package com.llc.search_service.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("search_history")
public class SearchHistory {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private String query;

    private LocalDateTime createdAt;
}
