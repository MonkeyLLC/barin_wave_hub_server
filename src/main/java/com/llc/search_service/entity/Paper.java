package com.llc.search_service.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@TableName("paper")
@Data
@Builder
public class Paper {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String grade;

    private String discipline;
    @JsonProperty("grade_category")
    private String gradeCategory;

    private String version;

    @JsonProperty("final_aim")
    private String finalAim;

    private String scene;

    private String name;

    @JsonProperty("is_real")
    private Integer isReal;
    @JsonProperty("exam_name")
    private String examName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("exam_time")
    private LocalDate examTime;

    private String province;

    private String city;

    private String districts;
    @JsonProperty("download_count")

    private Integer downloadCount;
    @JsonProperty("upload_time")
    /*@DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")*/
    private Date uploadTime;

    private String url;
    @JsonProperty("is_vip")
    private Integer isVip;

    private Double expense;

    @JsonProperty("text_book")
    private String textBook;

    @JsonProperty("doc_type")
    private Integer docType;

    @TableField("abstract")
    @JsonProperty("abstract")
    private String abstracts;
    private Integer difficulty;
    @JsonProperty("view_count")
    private Integer viewCount;

    private String author;
    @JsonProperty("author_id")
    private Integer authorId;

    @JsonProperty("school_name")
    private String schoolName;
    @JsonProperty("school_address")
    private String schoolAddress;

    private String knowledge;


}