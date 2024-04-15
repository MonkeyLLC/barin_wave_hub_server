package com.llc.search_service.controller.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReaderRequest {
    @Schema(description = "文件路径")
    @NotNull
    private String path;
 /*   @NotNull
    @Schema(description = "文件id")
    private Integer id;*/
    @NotNull
    @Schema(description = "文件名")
    private String name;
}
