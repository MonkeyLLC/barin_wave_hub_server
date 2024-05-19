package com.llc.search_service.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.llc.search_service.controller.model.request.UploadRequest;
import com.llc.search_service.controller.model.response.DownloadHistoryResponse;
import com.llc.search_service.controller.model.response.UploadedResponse;
import com.llc.search_service.domain.auth.AuthUser;
import com.llc.search_service.entity.DownloadHistory;
import com.llc.search_service.entity.Paper;
import com.llc.search_service.entity.Upload;
import com.llc.search_service.mapper.EsMapper;
import com.llc.search_service.mapper.UploadMapper;
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
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ImportServiceImpl implements ImportService {
    private final EsService esService;
    private final EsMapper esMapper;
    private final String rootPath = System.getProperty("user.dir");
    private final UploadMapper uploadMapper;

    public ImportServiceImpl(EsService esService, EsMapper esMapper, UploadMapper uploadMapper) {
        this.esService = esService;
        this.esMapper = esMapper;
        this.uploadMapper = uploadMapper;
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

    @Override
    public void upload(AuthUser user, UploadRequest request, MultipartFile file) {

        var now = LocalDate.now();

        String fullName = request.getName();
        String[] split = fullName.split("\\.");
        String name = split[0];
        String type = split[1];

        if (type == null) {
            throw new RuntimeException("文件类型不支持");
        }

        Paper.PaperBuilder builder = Paper.builder();
        builder.province(request.getProvince())
                .name(name)
                .docType(DocType.getType(type).getCode())
                .city(request.getCity())
                .discipline(request.getDiscipline())
                .grade(request.getGrade())
                .gradeCategory(request.getGradeCategory())
                .version(request.getVersion())
                .scene(request.getScene())
                .textBook(request.getTextBook())
                .knowledge(request.getKnowledge())
                .authorId(user.getId())
                .author(user.getUsername());
        // .uploadTime(now);


        String finalAim = judgedFinalAim(request.getGradeCategory());
        builder.finalAim(finalAim);

        String uuid = UUID.randomUUID().toString();

        String gradeCategoryName = judgedGradeCategory(request.getGradeCategory());
        String filePath = String.format("data/source/%s/%s.%s", uuid, gradeCategoryName, type);
        String fullPath = String.format("%s/%s", rootPath, filePath);
        File inputFile = new File(fullPath);

        try {
            if (!inputFile.exists()) {
                if (!inputFile.getParentFile().exists()) {
                    FileUtils.createParentDirectories(inputFile);
                }
            }
            file.transferTo(inputFile);
            builder.url(filePath);
        } catch (IOException e) {
            log.error("创建文件失败", e);
        }

        Paper paper = Paper.builder().build();
        esMapper.insert(paper);

        Paper selectOne = esMapper.selectOne(new LambdaQueryWrapper<Paper>().eq(Paper::getUrl, filePath));
        Integer id = selectOne.getId();

        esService.addDoc(id);


    }

    @Override
    public UploadedResponse getUploadRecord(Integer userId, Integer page) {
        Page<Upload> ppage = new Page<>(page, 10);
        LambdaQueryWrapper<Upload> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Upload::getUserId, userId).orderByDesc(Upload::getCreatedAt);
        Page<Upload> selectPage = uploadMapper.selectPage(ppage, queryWrapper);
        UploadedResponse uploadedResponse = new UploadedResponse();
        uploadedResponse.setTotal(selectPage.getTotal());
        List<Upload> records = selectPage.getRecords();
        uploadedResponse.setItems(records);
        return uploadedResponse;
    }

    @Override
    public void uploadRecord(Integer userId, Integer paperId, String name) {
        var now = new Date();
        Upload upload = new Upload();
        upload.setUserId(userId);
        upload.setPaperId(paperId);
        upload.setName(name);
        upload.setStatus(0);
        upload.setCreatedAt(now);
        upload.setUpdatedAt(now);
        uploadMapper.insert(upload);
    }

    private String judgedFinalAim(String gradeCategory) {

        return switch (gradeCategory) {
            case "小学" -> "小升初";
            case "初中" -> "中考";
            case "高中" -> "高考";
            default -> "其他";
        };

    }

    private String judgedGradeCategory(String gradeCategory) {

        return switch (gradeCategory) {
            case "小学" -> "small";
            case "初中" -> "middle";
            case "高中" -> "high";
            default -> "other";
        };

    }
}
