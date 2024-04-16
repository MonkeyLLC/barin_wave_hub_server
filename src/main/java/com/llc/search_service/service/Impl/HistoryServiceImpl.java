package com.llc.search_service.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.llc.search_service.constants.RedisConstants;
import com.llc.search_service.controller.model.response.DownloadHistoryResponse;
import com.llc.search_service.controller.model.response.SearchHistoryResponse;
import com.llc.search_service.entity.DownloadHistory;
import com.llc.search_service.entity.SearchHistory;
import com.llc.search_service.entity.ViewHistory;
import com.llc.search_service.mapper.DownloadHistoryMapper;
import com.llc.search_service.mapper.SearchHistoryMapper;
import com.llc.search_service.mapper.ViewHistoryMapper;
import com.llc.search_service.service.HistoryService;
import com.llc.search_service.utlis.Md5Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class HistoryServiceImpl implements HistoryService {
    private final SearchHistoryMapper searchHistoryMapper;
    private final RedisTemplate<String, String> redisTemplate;
    private final DownloadHistoryMapper downloadHistoryMapper;
    private final ViewHistoryMapper viewHistoryMapper;

    public HistoryServiceImpl(SearchHistoryMapper searchHistoryMapper,
                              RedisTemplate<String, String> redisTemplate,
                              DownloadHistoryMapper downloadHistoryMapper,
                              ViewHistoryMapper viewHistoryMapper) {
        this.searchHistoryMapper = searchHistoryMapper;
        this.redisTemplate = redisTemplate;
        this.downloadHistoryMapper = downloadHistoryMapper;
        this.viewHistoryMapper = viewHistoryMapper;
    }

    @Override
    public SearchHistoryResponse search(Integer id, Integer page) {

        Page<SearchHistory> ppage = new Page<>(page, 10);

        LambdaQueryWrapper<SearchHistory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SearchHistory::getUserId, id).orderByDesc(SearchHistory::getCreatedAt);

        Page<SearchHistory> selectPage = searchHistoryMapper.selectPage(ppage, queryWrapper);
        SearchHistoryResponse searchHistoryResponse = new SearchHistoryResponse();

        searchHistoryResponse.setTotal(selectPage.getTotal());

        List<SearchHistoryResponse.SearchHistory> searchHistories = new ArrayList<>();
        List<String> histories = new ArrayList<>();
        for (SearchHistory record : selectPage.getRecords()) {
            histories.add(record.getQuery());
            //SearchHistoryResponse.SearchHistory searchHistory = new SearchHistoryResponse.SearchHistory();
            //BeanUtils.copyProperties(record, searchHistory);
            //searchHistories.add(searchHistory);
        }
        searchHistoryResponse.setItems(histories.stream().distinct().toList());

        return searchHistoryResponse;
    }

    @Override
    public Boolean record(Integer userId, String query) {

        String queryMd5 = Md5Utils.stringToMD5(query);

        String key = RedisConstants.SEARCH_HISTORY_KEY + userId + ":" + queryMd5;
        String cache = redisTemplate.opsForValue().get(key);
        if (StringUtils.hasText(cache)) {
            return true;
        }

        SearchHistory searchHistory = new SearchHistory();
        searchHistory.setUserId(userId);
        searchHistory.setQuery(query);
        searchHistory.setCreatedAt(LocalDateTime.now());
        searchHistoryMapper.insert(searchHistory);

        redisTemplate.opsForValue().set(key, "1", 5L, TimeUnit.MINUTES);

        return true;
    }

    @Override
    public List<String> hot() {
        List<String> hots = searchHistoryMapper.selectTop5GroupByQueryOrderByCount();
        hots = hots.stream().distinct().toList();
        return hots;
    }

    @Override
    public DownloadHistoryResponse downloadList(Integer userId, Integer page) {
        Page<DownloadHistory> ppage = new Page<>(page, 10);
        LambdaQueryWrapper<DownloadHistory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DownloadHistory::getUserId, userId).orderByDesc(DownloadHistory::getCreatedAt);
        Page<DownloadHistory> selectPage = downloadHistoryMapper.selectPage(ppage, queryWrapper);
        DownloadHistoryResponse downloadHistoryResponse = new DownloadHistoryResponse();
        downloadHistoryResponse.setTotal(selectPage.getTotal());
        downloadHistoryResponse.setItems(selectPage.getRecords());
        return downloadHistoryResponse;
    }

    @Override
    public Boolean view(Integer userId, Integer paperId) {
        ViewHistory viewHistory = new ViewHistory();
        viewHistory.setUserId(userId);
        viewHistory.setPaperId(paperId);
        viewHistory.setCreatedAt(LocalDateTime.now());
        viewHistoryMapper.insert(viewHistory);
        return true;
    }
}
