package com.llc.search_service.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.llc.search_service.controller.model.request.SearchPaperRequest;
import com.llc.search_service.controller.model.response.CollectsResponse;
import com.llc.search_service.controller.model.response.SearchResultResponse;
import com.llc.search_service.entity.Collect;
import com.llc.search_service.entity.EsPaper;
import com.llc.search_service.mapper.CollectMapper;
import com.llc.search_service.model.FieldMapping;
import com.llc.search_service.service.CollectService;
import com.llc.search_service.service.SearchService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CollectServiceImpl implements CollectService {
    private final CollectMapper collectMapper;
    private final SearchService searchService;

    public CollectServiceImpl(CollectMapper collectMapper,
                              SearchService searchService) {
        this.collectMapper = collectMapper;
        this.searchService = searchService;
    }

    @Override
    public boolean collect(Integer userId, Integer paperId) {

        LambdaQueryWrapper<Collect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Collect::getUserId, userId).eq(Collect::getPaperId, paperId);
        Collect selectOne = collectMapper.selectOne(queryWrapper);

        if (selectOne != null) {
            return false;
        }

        SearchPaperRequest request = new SearchPaperRequest();
        request.setField(FieldMapping.ID.getFiled());
        request.setQuery(String.valueOf(paperId));
        SearchResultResponse search = searchService.search(request);
        List<EsPaper> hits = search.getHits();
        if (hits.isEmpty()) {
            return false;
        }
        String paperName = hits.get(0).getName();

        Collect collect = new Collect();
        collect.setUserId(userId);
        collect.setPaperName(paperName);
        collect.setPaperId(paperId);
        collect.setCreatedAt(LocalDateTime.now());
        collectMapper.insert(collect);

        return true;
    }

    public boolean cancel(Integer userId, Integer paperId) {

        LambdaQueryWrapper<Collect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Collect::getUserId, userId).eq(Collect::getPaperId, paperId);
        Collect selectOne = collectMapper.selectOne(queryWrapper);

        if (selectOne == null) {
            return false;
        }
        collectMapper.delete(queryWrapper);

        return true;
    }

    @Override
    public Map<Integer, Boolean> isCollect(Integer userId, List<Integer> paperIds) {
        if (paperIds.isEmpty()) {
            return Map.of();
        }

        LambdaQueryWrapper<Collect> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Collect::getUserId, userId).in(Collect::getPaperId, paperIds);
        List<Collect> collects = collectMapper.selectList(queryWrapper);

        List<Integer> list = collects.stream().map(Collect::getPaperId).toList();
        Map<Integer, Boolean> collect = paperIds.stream().collect(Collectors.toMap(i -> i, list::contains));

        return collect;

    }

    public CollectsResponse list(Integer userId, Integer page) {
        Page<Collect> ppage = new Page<>(page, 10);

        LambdaQueryWrapper<Collect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Collect::getUserId, userId).orderByDesc(Collect::getCreatedAt);
        Page<Collect> collectPage = collectMapper.selectPage(ppage, queryWrapper);

        CollectsResponse collectsResponse = new CollectsResponse();
        collectsResponse.setTotal(collectPage.getTotal());

        List<CollectsResponse.Collect> collects = new ArrayList<>();
        for (Collect record : collectPage.getRecords()) {
            CollectsResponse.Collect collect = new CollectsResponse.Collect();
            BeanUtils.copyProperties(record, collect);
            collects.add(collect);
        }

        collectsResponse.setItems(collects);

        return collectsResponse;
    }

}
