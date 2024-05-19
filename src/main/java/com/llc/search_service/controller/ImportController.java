package com.llc.search_service.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.llc.search_service.controller.model.request.UploadRequest;
import com.llc.search_service.controller.model.response.UploadedResponse;
import com.llc.search_service.domain.auth.AuthUser;
import com.llc.search_service.entity.Paper;
import com.llc.search_service.entity.Upload;
import com.llc.search_service.service.ImportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Tag(name = "HomeStatistics", description = "首页统计接口")
@RestController
@Slf4j
@RequestMapping("import")
public class ImportController {
    private final RestHighLevelClient client;
    private final ImportService importService;

    public ImportController(RestHighLevelClient client, ImportService importService) {
        this.client = client;
        this.importService = importService;
    }

    @GetMapping("batch")
    @Operation(method = "GET", summary = "导入数据")
    public void importData(String filePath) throws JsonProcessingException {
        List<String> ids = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // 使用 Files 类的 readAllBytes 方法一次性读取整个文件的内容
            byte[] bytes = Files.readAllBytes(Paths.get(filePath));
            String content = new String(bytes, StandardCharsets.UTF_8);


            JsonNode jsonNode = objectMapper.readTree(content);
            JsonNode jsonHisArr = jsonNode.get("hits").get("hits");

            List<JsonNode> jsonNodeList = new ArrayList<>();
            for (JsonNode node : jsonHisArr) {
                jsonNodeList.add(node);
            }
            int startIndex = 1000;
            for (int i1 = startIndex; i1 <= jsonHisArr.size(); i1 += 1000) {
                BulkRequest bulkRequest = new BulkRequest();
                log.info("开始导入数据的请求的：{},大小：{}", bulkRequest, bulkRequest.requests().size());
                //int j = jsonHisArr.size() / 1000;

                loop:
                for (int i2 = i1 - 1000; i2 < i1; i2++) {
                    JsonNode source = jsonNodeList.get(i2).get("_source");
                    String _id = jsonNodeList.get(i2).get("_id").asText();

                    String sourceStr = source.toString();
                    IndexRequest indexRequest = new IndexRequest("citation_data_extract_record_optimized_v5").id(_id).type("doc").source(sourceStr, XContentType.JSON);
                    bulkRequest.add(indexRequest);

                    String articleId = source.get("article").get("nstl_article_id").asText();
                    ids.add(articleId);
                    log.info("当前文档添加成功，id:{},当前为第：{}个", articleId, i2);

                }
                if (bulkRequest.requests().isEmpty()) {
                    continue;
                }
                client.bulk(bulkRequest, RequestOptions.DEFAULT);
                log.info("导入一千条数据成功");


            }


        } catch (IOException ex) {
            log.info("数据导入成功");
            String idsStr = null;

            idsStr = objectMapper.writeValueAsString(ids);

            log.info("本次数据的id集合为：{}", idsStr);
            throw new RuntimeException(ex);
        }


        // reader.close();
    }


    @PutMapping("single")
    @Operation(method = "PUT", summary = "导入单条数据")
    public String importSingleData(Paper paper, @RequestPart MultipartFile file) {

        return importService.importSingle(paper, file);

    }

    /**
     * 分类：年级，学科，题型，难度进行分类。
     * @param user
     * @param request
     * @param file
     * @return
     */

    @PutMapping("test")
    @Operation(method = "PUT", summary = "导入单条数据")
    public String uploadSingle(@AuthenticationPrincipal AuthUser user, UploadRequest request, @RequestPart MultipartFile file) {

        log.info("接收到文件，文件名：{}", file.getOriginalFilename());

        importService.upload(user, request, file);
        return null;

    }


    @GetMapping("record")
    @Operation(method = "GET", summary = "获取上传记录")
    public UploadedResponse getUploadRecord(@AuthenticationPrincipal AuthUser user, Integer page) {
        Integer userId = user.getId();
        return importService.getUploadRecord(userId, page);
    }

    @PostMapping("record")
    @Operation(method = "POST", summary = "上传记录")
    public void uploadRecord(@AuthenticationPrincipal AuthUser user, String name) {
        Integer paperId = 999;
        importService.uploadRecord(user.getId(), paperId, name);
    }

}
