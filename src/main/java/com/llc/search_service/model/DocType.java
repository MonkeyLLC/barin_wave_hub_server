package com.llc.search_service.model;

import lombok.Getter;

@Getter
public enum DocType {
    WORDS("docx", 1),
    WORD("doc", 1),
    PDF("pdf", 2),
    PPT("ppt", 3),
    PPTX("pptx", 3),
    ZIP("zip", 4);

    private final String type;
    private final int code;

    DocType(String type, int code) {
        this.type = type;
        this.code = code;
    }

    public static DocType getType(String type) {
        return switch (type.trim()) {
            case "docx" -> WORDS;
            case "doc" -> WORD;
            case "pdf" -> PDF;
            case "ppt" -> PPT;
            case "pptx" -> PPTX;
            case "zip" -> ZIP;
            default -> null;
        };
    }
}
