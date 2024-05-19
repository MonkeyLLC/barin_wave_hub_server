package com.llc.search_service.controller.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UploadRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String gradeCategory;

    @NotBlank
    private String grade;

    @NotBlank
    private String discipline;

    @NotBlank
    private String scene;

    @NotBlank
    private String version;

    @NotBlank
    private String knowledge;

    @NotBlank
    private String textBook;

    @NotBlank
    private String province;

    @NotBlank
    private String city;
}
