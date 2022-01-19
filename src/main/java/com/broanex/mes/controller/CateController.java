package com.broanex.mes.controller;

import com.broanex.mes.entity.Cate;
import com.broanex.mes.service.CateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;

//GetMapping(value:Cate)    -> Cate를 조회하는 메소드
//PostMapping(value:Cate)   -> Cate를 저장하거나, 업데이트 하는 메소드
//DeleteMapping(value:Cate) -> Cate를 삭제하는 메소드

@RestController
public class CateController {

    private final CateService cateService;

    public CateController(CateService cateService) {
        this.cateService = cateService;
    }

    @GetMapping(value = "cate", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Cate>> getCategory(@RequestParam(required = false) Long indexNo,
                                                  @RequestParam(required = false) String cateName,
                                                  @RequestParam(required = false) String cateCode,
                                                  @RequestParam(required = false) Long upccate) {
        List<Cate> cateList = cateService.findAllCategory(new HashMap<String, Object>() {{
            put("indexNo", indexNo);
            put("cateName", cateName);
            put("cateCode", cateCode);
            put("upccate", upccate);
        }});
        return ok(cateList);
    }

    @PostMapping(value = "cate", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> postsCate(@RequestBody Cate cate) {
        cateService.saveOrUpdate(cate);
        return ok(null);
    }

    @DeleteMapping(value = "cate", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteCate(@RequestBody Cate cate) {
        cateService.deleteCategory(cate);
        return ok(null);
    }

}
