package com.llc.search_service.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.llc.search_service.config.aliconfig.AliConfig;
import com.llc.search_service.utlis.Md5Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
public class ToolServiceImpl implements ToolService {

    private final String endpoint;
    private final String accessKeyId;
    private final String accessKeySecret;
    private final String bucketName;

    public ToolServiceImpl(AliConfig aliConfig) {
        this.endpoint = aliConfig.getEndpoint();
        this.accessKeyId = aliConfig.getAccessKeyId();
        this.accessKeySecret = aliConfig.getAccessKeySecret();
        this.bucketName = aliConfig.getBucketName();
    }

    /**
     * 实现上传图片到OSS
     */
    @Override
    public String upload(MultipartFile file, Integer id) {
        // 获取上传的文件的输入流
        try {
            log.info("接收到文件，文件名：{}", file.getOriginalFilename());
            InputStream inputStream = file.getInputStream();

            // 避免文件覆盖
            String originalFilename = file.getOriginalFilename();
            String fileName = Md5Utils.stringToMD5(id.toString()) + originalFilename.substring(originalFilename.lastIndexOf("."));

            //上传文件到 OSS
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            ossClient.putObject(bucketName, fileName, inputStream);

            //文件访问路径
            String url = endpoint.split("//")[0] + "//" + bucketName + "." + endpoint.split("//")[1] + "/" + fileName;
            // 关闭ossClient
            ossClient.shutdown();
            log.info("文件上传成功，文件路径：{}", url);
            return url;// 把上传到oss的路径返回
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
