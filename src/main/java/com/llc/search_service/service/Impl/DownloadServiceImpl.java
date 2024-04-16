package com.llc.search_service.service.Impl;

import com.llc.search_service.controller.model.request.SearchPaperRequest;
import com.llc.search_service.controller.model.response.SearchResultResponse;
import com.llc.search_service.entity.DownloadHistory;
import com.llc.search_service.entity.EsPaper;
import com.llc.search_service.entity.Paper;
import com.llc.search_service.mapper.DownloadHistoryMapper;
import com.llc.search_service.mapper.EsMapper;
import com.llc.search_service.model.DocType;
import com.llc.search_service.model.FieldMapping;
import com.llc.search_service.service.DownloadService;
import com.llc.search_service.service.SearchService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.List;

@Service
public class DownloadServiceImpl implements DownloadService {

    private final SearchService searchService;
    private final DownloadHistoryMapper downloadHistoryMapper;


    public DownloadServiceImpl(SearchService searchService,
                               DownloadHistoryMapper downloadHistoryMapper) {

        this.searchService = searchService;
        this.downloadHistoryMapper = downloadHistoryMapper;
    }

    @Override
    public void download(Integer userId, Integer paperId, HttpServletResponse response) {

        SearchPaperRequest request = new SearchPaperRequest();
        request.setField(FieldMapping.ID.getFiled());
        request.setQuery(String.valueOf(paperId));
        SearchResultResponse search = searchService.search(request);
        List<EsPaper> hits = search.getHits();

        if (hits.isEmpty()) {
            return;
        }
        EsPaper esPaper = hits.get(0);

        String url = esPaper.getUrl();
        if (!StringUtils.hasText(url)) {
            return;
        }

        Integer docType = esPaper.getDocType();
        DocType type = DocType.getType(docType);
        if (type == null) {
            return;
        }
        String suffix = type.getType();

        File file = new File(url);
        if (!file.exists()) {
            return;
        }
        try (FileInputStream fis = new FileInputStream(file);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }

            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader("Content-Disposition", "attachment; filename=paper." + suffix);

            ServletOutputStream servletOutputStream = response.getOutputStream();
            servletOutputStream.write(outputStream.toByteArray());
            servletOutputStream.flush();
            record(userId, paperId);
        } catch (IOException e) {
            // Handle IOException
            throw new RuntimeException(e);
        }


    }

    private void record(Integer userId, Integer paperId) {
        DownloadHistory downloadHistory = new DownloadHistory();
        downloadHistory.setUserId(userId);
        downloadHistory.setPaperId(paperId);

        SearchPaperRequest request = new SearchPaperRequest();
        request.setField(FieldMapping.ID.getFiled());
        request.setQuery(String.valueOf(paperId));
        SearchResultResponse search = searchService.search(request);
        List<EsPaper> hits = search.getHits();
        if (hits.isEmpty()) {
            return;
        }
        String paperName = hits.get(0).getName();

        downloadHistory.setPaperName(paperName);

        downloadHistoryMapper.insert(downloadHistory);

    }
}
