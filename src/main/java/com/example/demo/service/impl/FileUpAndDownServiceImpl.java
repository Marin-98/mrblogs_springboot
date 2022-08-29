package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.demo.IStatusMessage;
import com.example.demo.MessageProperties;
import com.example.demo.service.FileUpAndDownService;
import net.coobird.thumbnailator.Thumbnails;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class FileUpAndDownServiceImpl implements FileUpAndDownService {

    @Autowired
    private MessageProperties config; //用来获取file-message.properties配置文件中的信息

    @Override
    public Map<String, Object> uploadPicture(MultipartFile file) throws ServiceException {
        try {
            Map<String, Object> resMap = new HashMap<>();
            String[] IMAGE_TYPE = config.getImageType().split(",");
            String path = null;
            boolean flag = false;
            for (String type : IMAGE_TYPE) {
                if (StringUtils.endsWithIgnoreCase(file.getOriginalFilename(), type)) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                resMap.put("result", IStatusMessage.SystemStatus.SUCCESS.getMessage());
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                // 获得文件类型
                String fileType = file.getContentType();
                // 获得文件后缀名称
                String imageName = fileType.substring(fileType.indexOf("/") + 1);
                // 原名称
                String oldFileName = file.getOriginalFilename();
                // 新名称
                String newFileName = uuid + "." + imageName;
                // 年月日文件夹
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                String basedir = sdf.format(new Date());
                Set<PosixFilePermission> perms = new HashSet<PosixFilePermission>();
                // 进行压缩(大于4M)
                if (file.getSize() > config.getFileSize()) {
                    // 重新生成
                    String newUUID = UUID.randomUUID().toString().replaceAll("-", "");
                    newFileName = newUUID + "." + imageName;
                    path = config.getUpPath() + "/" + basedir + "/" + newUUID + "." + imageName;
                    // 如果目录不存在则创建目录
                    File oldFile = new File(path);
//                    oldFile.setReadable(true,false);

                    if (!oldFile.exists()) {
                        oldFile.mkdirs();
                    }

                    file.transferTo(oldFile);

                    // 压缩图片
                    Thumbnails.of(oldFile).scale(config.getScaleRatio()).toFile(path);


                    // 显示路径
                    resMap.put("path", "http://www.mrblogs.top:8091/" + basedir + "/" + newUUID + "." + imageName);
                    String com = "cmd /c echo y|cacls " + oldFile.getAbsolutePath() + " /t /p everyone:F";
                    Runtime.getRuntime().exec(com);
                } else {
                    path = config.getUpPath() + "/" + basedir + "/" + uuid + "." + imageName;
                    // 如果目录不存在则创建目录
                    File uploadFile = new File(path);
//                    uploadFile.setReadable(true,false);
                    if (!uploadFile.exists()) {
                        uploadFile.mkdirs();
                    }

                    file.transferTo(uploadFile);
                    // 显示路径
                    resMap.put("path", "http://www.mrblogs.top:8091/" + basedir + "/" + uuid + "." + imageName);
                    String com = "cmd /c echo y|cacls " + uploadFile.getAbsolutePath() + " /t /p everyone:F";
                    Runtime.getRuntime().exec(com);
                }
                resMap.put("oldFileName", oldFileName);
                resMap.put("newFileName", newFileName);
                resMap.put("fileSize", file.getSize());
            } else {
                resMap.put("result", "图片格式不正确,支持png|jpg|jpeg");
            }
            return resMap;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }
    }
}
