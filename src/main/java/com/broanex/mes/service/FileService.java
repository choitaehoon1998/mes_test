package com.broanex.mes.service;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class FileService implements InitializingBean {

    @Value("${spring.file.upload.filePath}")
    private String FilePath;

    private File ToFile(MultipartFile multipartFile) throws IOException {
        String baseDir = System.getProperty("java.io.tmpdir") + "/" + UUID.randomUUID();

        org.apache.commons.io.FileUtils.forceMkdir(new File(baseDir));

        String tmpFileName = baseDir + "/" + FilenameUtils.getName(multipartFile.getOriginalFilename());

        File file = new File(tmpFileName);

        multipartFile.transferTo(file);
        return file;
    }

    private String getSavePath(String saveName) {
        return FilePath + "/" + saveName;
    }

    public HashMap<String, String> uploadFiles(List<MultipartFile> fileList) throws IOException {
        HashMap<String, String> NamePathMap = new HashMap<>();
        for (MultipartFile multiPartFile : fileList) {
            File uploadFile = ToFile(multiPartFile);
            String fileName = FilenameUtils.getName(uploadFile.getName());
            String extension = FilenameUtils.getExtension(fileName);
            String baseName = UUID.randomUUID().toString();
            String saveName = baseName + "." + extension;
            String savePath = getSavePath(saveName);
            File file = new File(savePath);
            FileUtils.copyFile(uploadFile, file);
            NamePathMap.put(fileName, savePath);
        }
        return NamePathMap;
    }

    @Override
    public void afterPropertiesSet() {
        try {
            FileUtils.forceMkdir(new File(FilePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
