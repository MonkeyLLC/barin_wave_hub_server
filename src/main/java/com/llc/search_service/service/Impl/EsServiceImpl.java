package com.llc.search_service.service.Impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.llc.search_service.constants.EsConstants;
import com.llc.search_service.entity.EsPaper;
import com.llc.search_service.entity.Paper;
import com.llc.search_service.mapper.EsMapper;
import com.llc.search_service.service.EsService;
import com.llc.search_service.service.ToolService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Service
@Slf4j
@Transactional
public class EsServiceImpl implements EsService {
    private final EsMapper esMapper;
    private final RestHighLevelClient client;
    private final ToolService toolService;

    public EsServiceImpl(EsMapper esMapper,
                         RestHighLevelClient client,
                         ToolService toolService) {
        this.esMapper = esMapper;
        this.client = client;
        this.toolService = toolService;
    }

    @Override
    public void addDocAll(Integer minId, Integer maxId) {
        LambdaQueryWrapper<Paper> queryWrapper = new LambdaQueryWrapper<>();

        if (minId == null && maxId != null) {
            queryWrapper.le(Paper::getId, maxId);
        } else if (minId != null && maxId == null) {
            queryWrapper.ge(Paper::getId, minId);
        } else if (minId != null && maxId != null) {
            queryWrapper.ge(Paper::getId, minId).le(Paper::getId, maxId);
        } else {
            return;
        }

        List<Paper> papers = esMapper.selectList(queryWrapper);

        ObjectMapper objectMapper = new ObjectMapper();
        for (Paper paper : papers) {
            EsPaper esPaper = new EsPaper(paper);
            String paperJson;
            try {
                paperJson = objectMapper.writeValueAsString(esPaper);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }


            IndexRequest request = new IndexRequest(EsConstants.INDEX_NAME, "_doc").id(paper.getId().toString());
            request.source(paperJson, XContentType.JSON);

            try {
                client.index(request, RequestOptions.DEFAULT);
                log.info("文档添加成功");
            } catch (Exception e) {
                throw new RuntimeException();
            }
        }

    }

    @Override
    @Transactional
    public boolean addDocToDao(Paper paper, MultipartFile file) {
        log.info("接收到文件：{}", file.getOriginalFilename());
        esMapper.insert(paper);

        Integer id = esMapper.selectOne(new LambdaQueryWrapper<Paper>().eq(Paper::getName, paper.getName())).getId();

        String url = toolService.upload(file, id);
        paper.setUrl(url);
        paper.setId(id);

        esMapper.updateById(paper);

        addDoc(id);

        return true;
    }

    @Override
    @Transactional
    public void addDoc(Integer id) {
        Paper paper = esMapper.selectOne(new LambdaQueryWrapper<Paper>()
                .eq(Paper::getId, id));

        EsPaper esPaper = new EsPaper(paper);

        ObjectMapper objectMapper = new ObjectMapper();

        String paperJson;

        try {
            paperJson = objectMapper.writeValueAsString(esPaper);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        IndexRequest request = new IndexRequest(EsConstants.INDEX_NAME, "_doc").id(id.toString());
        request.source(paperJson, XContentType.JSON);

        try {
            client.index(request, RequestOptions.DEFAULT);
            log.info("文档添加成功");
        } catch (Exception e) {
            throw new RuntimeException();
        }


    }
}
