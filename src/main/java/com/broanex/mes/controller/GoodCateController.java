package com.broanex.mes.controller;

import com.broanex.mes.dto.GoodCateRequestDto;
import com.broanex.mes.service.GoodCateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
public class GoodCateController {
    private final GoodCateService goodCateService;

    public GoodCateController(GoodCateService goodCateService) {
        this.goodCateService = goodCateService;
    }

    @PostMapping(value = "goodCate", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveGoodCate(@RequestBody GoodCateRequestDto requestDto) {
        goodCateService.saveGoodCate(requestDto);
        return ok(null);
    }

    @DeleteMapping(value="goodCate", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteGoodCate(@RequestBody GoodCateRequestDto requestDto){
        goodCateService.deleteGoodCate(requestDto);
        return ok(null);
    }
}
