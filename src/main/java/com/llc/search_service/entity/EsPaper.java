package com.llc.search_service.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class EsPaper {

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

    private School school;
    @JsonProperty("exam_name")
    private String examName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("exam_time")
    private LocalDate examTime;

    private Root root;

    @JsonProperty("download_count")

    private Integer downloadCount;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("upload_time")
    private LocalDate uploadTime;

    private String url;
    @JsonProperty("is_vip")
    private Integer isVip;

    private Double expense;

    @JsonProperty("text_book")
    private String textBook;

    @JsonProperty("doc_type")
    private Integer docType;
    private Author author;
    @JsonProperty("abstract")
    private String abstracts;
    private Integer difficulty;
    @JsonProperty("view_count")
    private Integer viewCount;

    private String knowledge;

    @Data
    public static class Author {
        private String author;
        @JsonProperty("author_id")
        private Integer authorId;
    }


    @Data
    public static class School {
        @JsonProperty("school_name")
        private String schoolName;
        @JsonProperty("school_address")
        private String schoolAddress;
    }

    @Data
    public static class Root {
        private String province;

        private String city;

        private String districts;
    }

    public EsPaper(Paper paper) {
        this.id = paper.getId();
        this.grade = paper.getGrade();
        this.discipline = paper.getDiscipline();
        this.gradeCategory = paper.getGradeCategory();
        this.version = paper.getVersion();
        this.finalAim = paper.getFinalAim();
        this.scene = paper.getScene();
        this.name = paper.getName();
        this.isReal = paper.getIsReal();
        this.school = new School();
        this.school.schoolAddress = paper.getSchoolAddress();
        this.school.schoolName = paper.getSchoolName();
        this.examName = paper.getExamName();
        this.root = new Root();
        this.root.province = paper.getProvince();
        this.root.city = paper.getCity();
        this.root.districts = paper.getDistricts();
        this.downloadCount = paper.getDownloadCount();
        this.uploadTime = paper.getUploadTime();
        this.url = paper.getUrl();
        this.isVip = paper.getIsVip();
        this.expense = paper.getExpense();
        this.abstracts = paper.getAbstracts();
        this.viewCount = paper.getViewCount();
        this.docType = paper.getDocType();
        this.author = new Author();
        this.author.author = paper.getAuthor();
        this.author.authorId = paper.getAuthorId();
        this.difficulty = paper.getDifficulty();
        this.textBook = paper.getTextBook();
        this.knowledge = paper.getKnowledge();
        this.setExamTime(paper.getExamTime());
    }
}
