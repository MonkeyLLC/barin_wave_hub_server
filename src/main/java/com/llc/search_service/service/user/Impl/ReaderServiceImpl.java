package com.llc.search_service.service.user.Impl;


import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;
import com.llc.search_service.controller.model.request.ReaderRequest;
import com.llc.search_service.service.ReaderService;


import com.llc.search_service.utlis.Md5Utils;
import lombok.extern.slf4j.Slf4j;


import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class ReaderServiceImpl implements ReaderService {
    private final RestTemplate restTemplate;
    private final com.aliyun.credentials.Client credentialClient;
    private final String rootPath = System.getProperty("user.dir");

    public ReaderServiceImpl(RestTemplate restTemplate,
                             com.aliyun.credentials.Client credentialClient) {
        this.restTemplate = restTemplate;
        this.credentialClient = credentialClient;
    }

    /*@Override
    public void read(String fileUrl) {

        byte[] fileBytes = restTemplate.getForObject(fileUrl, byte[].class);
        if (fileBytes == null) {
            throw new RuntimeException("文件不存在");
        }

        ByteArrayInputStream bis = new ByteArrayInputStream(fileBytes);
        try {
            XWPFDocument document = new XWPFDocument(bis);
            XHTMLOptions options = XHTMLOptions.create();

            OutputStream out = new FileOutputStream("output.html");
            XHTMLConverter.getInstance().convert(document, out, options);
            out.close();
            bis.close();
        } catch (IOException e) {
            log.error("读取文件失败", e);
            throw new RuntimeException(e);
        }

    }*/

    @Override
    public List<String> read(Integer id, ReaderRequest request) {
        String name = request.getName();
        String fileUrl = request.getPath();
        String md5 = Md5Utils.stringToMD5(name + id);

        String filePath = String.format("data/output/%d/%s.pdf", id, md5);
        String fullPath = String.format("%s/%s", rootPath, filePath);
        File file = new File(fullPath);

        try {
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    FileUtils.createParentDirectories(file);
                }
            }
        } catch (IOException e) {
            log.error("创建文件失败", e);
            throw new RuntimeException(e);
        }

        if (fileUrl.endsWith(".pdf")) {
            return convertPDFToImages(fileUrl, filePath.replace(".pdf", ""));
        }

        File inputWord = new File(fileUrl);
        File outputFile = new File(filePath);
        List<String> imagesPaths = null;
        try {
            InputStream docxInputStream = new FileInputStream(inputWord);
            OutputStream outputStream = new FileOutputStream(outputFile);
            IConverter converter = LocalConverter.builder().build();
            converter.convert(docxInputStream)
                    .as(DocumentType.DOCX)
                    .to(outputStream)
                    .as(DocumentType.PDF).schedule().get();

            imagesPaths = convertPDFToImages(outputFile.getAbsolutePath(), filePath.replace(".pdf", ""));
            outputStream.close();
            docxInputStream.close();

            log.info("转换完毕 targetPath = {}", outputFile.getAbsolutePath() + ",targetPath = " + outputFile.getAbsolutePath());
            converter.shutDown();

        } catch (Exception e) {
            log.error("word转pdf失败:" + e.getMessage(), e);
        }
        return imagesPaths;
    }

    public List<String> convertPDFToImages(String inputFilePath, String outputImagePathPrefix) {
        List<String> imagesPaths = new ArrayList<>();
        try (PDDocument document = PDDocument.load(new File(inputFilePath))) {

            PDFRenderer pdfRenderer = new PDFRenderer(document);
            for (int page = 0; page < document.getNumberOfPages(); ++page) {
                BufferedImage image = pdfRenderer.renderImageWithDPI(page, 300); // 300 DPI
                String outputImagePath = outputImagePathPrefix + "-" + (page + 1) + ".png";
                boolean png = ImageIO.write(image, "png", new File(outputImagePath));
                if (png) {


                    Path source = Paths.get(outputImagePath);
                    Path target = Paths.get("D:/llc/code/vue-elment-plus-demo-01/src/assets/" + outputImagePath);
                    Path targetDirPath = Paths.get(target.toString());
                    if (!Files.exists(targetDirPath)) {
                        Files.createDirectories(targetDirPath);
                    }
                    Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
                    imagesPaths.add("/src/assets/" + outputImagePath);
                }
            }
            log.info("pdf转图片成功");

        } catch (IOException e) {
            log.error("pdf转图片失败:" + e.getMessage(), e);
        }
        return imagesPaths;
    }


}
