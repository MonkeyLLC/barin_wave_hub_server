package com.llc.search_service.model;

import lombok.Getter;

@Getter
public enum FieldMapping {
    ID("id", "id", "id"),
    UPLOAD_TIME("上传时间", "upload_time", "upload_time"),
    DOWNLOAD_COUNT("下载量", "download_count", "download_count"),
    NAME("试卷名", "name", "name.keyword"),
    VERSION("教材版本", "version", "version.keyword"),
    GRADE_CATEGORY("年级分类", "grade_category", "grade_category.keyword"),
    EXPENSE("下载费用", "expense", "expense"),
    GRADE("年级", "grade", "grade.keyword"),
    DOC_TYPE("文档类型", "doc_type", "doc_type"),
    PROVINCE("省份", "root.province", "root.province.keyword"),
    CITY("城市", "root.city", "root.city.keyword"),
    DISTRICT("区县", "root.district", "root.district.keyword"),
    SCENE("适用场景", "scene", "scene.keyword"),
    VIEW_COUNT("浏览量", "view_count", "view_count"),
    TEXT_BOOK("教材版本", "text_book", "text_book.keyword");


    public static FieldMapping getFiled(String name) {
        return switch (name.trim()) {
            case "id" -> ID;
            case "上传时间" -> UPLOAD_TIME;
            case "下载量" -> DOWNLOAD_COUNT;
            case "试卷名" -> NAME;
            case "教材版本" -> VERSION;
            case "年级分类" -> GRADE_CATEGORY;
            case "年级" -> GRADE;
            case "下载费用" -> EXPENSE;
            case "文档类型" -> DOC_TYPE;
            case "省份" -> PROVINCE;
            case "城市" -> CITY;
            case "区县" -> DISTRICT;
            case "适用场景" -> SCENE;
            case "浏览量" -> VIEW_COUNT;
            default -> null;
        };
    }

    private final String name;
    private final String filed;
    private final String keyword;

    FieldMapping(String name, String filed, String keyword) {
        this.keyword = keyword;
        this.filed = filed;
        this.name = name;
    }

    // 静态方法，根据name获取对应的filed
    public static String getFiledByName(String name) {
        for (FieldMapping mapping : FieldMapping.values()) {
            if (mapping.getName().equals(name)) {
                return mapping.getFiled();
            }
        }
        // 如果找不到匹配的name，可以抛出异常或者返回一个默认值，这里返回空字符串
        return "";
    }

    // 静态方法，根据name获取对应的keyword
    public static String getKeywordByName(String name) {
        for (FieldMapping mapping : FieldMapping.values()) {
            if (mapping.getName().equals(name)) {
                return mapping.getKeyword();
            }
        }
        // 如果找不到匹配的name，可以抛出异常或者返回一个默认值，这里返回空字符串
        return "";
    }
}
