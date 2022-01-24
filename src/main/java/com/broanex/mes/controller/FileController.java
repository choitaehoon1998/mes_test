package com.broanex.mes.controller;

/*
 * 코드작성자 : 최태훈
 * 소스설명 : MES의 파일을 관리하는 CONTROLLER 역활을 한다.
 * 관련 DB 테이블 : 없음.
 * */

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

/*
 * 동작방식
 * 1. /file  [POST]   : 여러개의 file를 post 로 전송받아 서버에 올리고, file명과 file의 위치를 Map형식으로 리턴한다.
 *                      ex: { 파일명: 파일위치 }
 */

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
