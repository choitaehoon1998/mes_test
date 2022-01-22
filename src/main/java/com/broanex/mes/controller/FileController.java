package com.broanex.mes.controller;

import com.broanex.mes.service.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MultipartFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "file", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HashMap<String, String>> uploadFileList(@RequestPart(value = "fileList") List<MultipartFile> fileList) throws IOException {
        HashMap<String, String> filePathMap = fileService.uploadFiles(fileList);
        return ResponseEntity.ok(filePathMap);
    }

}
