package com.llc.search_service.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.llc.search_service.entity.Paper;
import com.llc.search_service.mapper.EsMapper;
import com.llc.search_service.model.DocType;
import com.llc.search_service.service.EsService;
import com.llc.search_service.service.ImportService;
import com.llc.search_service.utlis.Md5Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@Slf4j
public class ImportServiceImpl implements ImportService {
    private final EsService esService;
    private final EsMapper esMapper;
    private final String rootPath = System.getProperty("user.dir");

    public ImportServiceImpl(EsService esService, EsMapper esMapper) {
        this.esService = esService;
        this.esMapper = esMapper;
    }

    @Override
    @Transactional
    public String importSingle(Paper paper, MultipartFile file) {

        String fullName = paper.getName();
        String[] split = fullName.split("\\.");
        String name = split[0];
        String type = split[1];
        paper.setName(name);

        DocType docType = DocType.getType(type);
        if (docType == null) {
            return "文件类型不支持";
        }
        paper.setDocType(docType.getCode());

        esMapper.insert(paper);

        Integer id = esMapper.selectOne(new LambdaQueryWrapper<Paper>().eq(Paper::getName, paper.getName())).getId();

        String md5 = Md5Utils.stringToMD5(paper.getName() + paper.getId());

        String filePath = String.format("data/source/middle/%s.%s", md5, type);
        String fullPath = String.format("%s/%s", rootPath, filePath);
        File inputFile = new File(fullPath);

        try {
            if (!inputFile.exists()) {
                if (!inputFile.getParentFile().exists()) {
                    FileUtils.createParentDirectories(inputFile);
                }
            }
            file.transferTo(inputFile);
            paper.setUrl(filePath);
        } catch (IOException e) {
            log.error("创建文件失败", e);
        }

        paper.setId(id);

        esMapper.updateById(paper);

        esService.addDoc(id);

        return "success";
    }
}
