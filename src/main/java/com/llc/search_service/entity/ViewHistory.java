package com.llc.search_service.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ViewHistory {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private Integer paperId;

    private LocalDateTime createdAt;
}
