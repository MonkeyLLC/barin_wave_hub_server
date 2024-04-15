package com.llc.search_service.service.user.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.llc.search_service.controller.model.response.UserInfoResponse;
import com.llc.search_service.controller.model.response.UserListResponse;
import com.llc.search_service.entity.User;
import com.llc.search_service.mapper.UserMapper;
import com.llc.search_service.service.user.UserInfoService;
import com.llc.search_service.utlis.Md5Utils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserMapper, User> implements UserInfoService {
    private final String rootPath = System.getProperty("user.dir");

    @Override
    public UserListResponse userList() {
        List<User> list = this.list();
        List<UserListResponse.UserInfo> userInfoList = new ArrayList<>();
        for (User user : list) {
            UserListResponse.UserInfo userInfo = new UserListResponse.UserInfo();
            BeanUtils.copyProperties(user, userInfo);
            userInfoList.add(userInfo);
        }
        return new UserListResponse(list.size(), userInfoList);
    }

    @Override
    public UserInfoResponse userInfo(Integer userId) {
        User user = this.getById(userId);
        if (user != null) {
            UserInfoResponse userInfoResponse = new UserInfoResponse();
            BeanUtils.copyProperties(user, userInfoResponse);
            return userInfoResponse;
        }
        return null;
    }

    @Override
    public ResponseEntity<String> uploadAvatar(Integer userId, MultipartFile file) {
        long size = file.getSize();
        // 不能超过 2M
        if (size > 2 * 1024 * 1024) {
            throw new RuntimeException("文件大小不能超过 2M");
        }

      //  String fileName = file.getName();
        String filename = file.getOriginalFilename();

        // 获取后缀
        if (filename == null) {
            throw new RuntimeException("文件名不能为空");
        }
        String suffix = filename.substring(filename.lastIndexOf("."));

        String md5 = Md5Utils.stringToMD5(filename + userId);

        String filePath = String.format("data/avatar/%d/%s" + suffix, userId, md5);
        String fullPath = String.format("%s/%s", rootPath, filePath);

        File avatarFile = new File(fullPath);

        try {
            if (!avatarFile.exists()) {
                if (!avatarFile.getParentFile().exists()) {
                    FileUtils.createParentDirectories(avatarFile);
                }
            }
        } catch (IOException e) {
            log.error("创建文件失败", e);
            throw new RuntimeException(e);
        }

        File outputFile = new File(filePath);
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputStream.write(file.getBytes());
            byte[] byteArray = outputStream.toByteArray();
            String base64Image = Base64.getEncoder().encodeToString(byteArray);
            outputStream.close();

            OutputStream fileOutputStream = new FileOutputStream(outputFile);
            // 写到磁盘
            fileOutputStream.write(byteArray);

            // 把头像路径保存到数据库
            User user = this.getById(userId);
            user.setAvatarUrl(filePath);
            this.updateById(user);
            return ResponseEntity.ok().body(base64Image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
