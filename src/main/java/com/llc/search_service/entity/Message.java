package com.llc.search_service.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("message")
public class Message {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private long userId;

    private String title;

    private String content;

    private LocalDateTime createdAt;
}
