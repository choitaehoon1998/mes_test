package com.broanex.mes.controller;

import com.broanex.mes.Enum.useStatus.UseStatus;
import com.broanex.mes.entity.Good;
import com.broanex.mes.service.GoodService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.http.ResponseEntity.ok;

//GetMapping(value="goods")                  -> Good를 조회하는 메소드
//PostMapping(value="goods")                 -> Good를 저장하거나 업데이트하는 메소드
//PutMapping(value="goods")                  -> Good를 업데이트하는 메소드
//DeleteMapping(value="goods")               -> Good를 삭제하는 메소드
//DeleteMapping(value="goods/{seq}/goodsOp") -> Good의 pk로 goodsOp를 삭제하는 메소드

@RestController
public class GoodController {
    private final GoodService goodService;

    public GoodController(GoodService goodService) {
        this.goodService = goodService;
    }

    @GetMapping(value = "goods", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Good>> getAllGoods(@RequestParam(required = false) String gname,
                                                  @RequestParam(required = false) Long indexNo,
                                                  @RequestParam(required = false) Long account,
                                                  @RequestParam(required = false) UseStatus useop) {
        List<Good> GoodsList = goodService.findAllGoods(new HashMap<String, Object>() {{
            put("gname", gname);
            put("indexNo", indexNo);
            put("account", account);
            put("useop", useop);
        }});
        return ok(GoodsList);
    }

    @PostMapping(value = "goods", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> postGoods(@RequestPart(value = "good") Good goods,
                                          @RequestPart(value = "file", required = false) List<MultipartFile> fileList)
            throws IOException {
        goodService.saveOrUpdateWithFiles(goods, fileList);
        return ok(null);
    }

    @PutMapping(value = "goods", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> putGoods(@RequestBody Good goods) {

        goodService.updateGoods(goods);
        return ok(null);
    }

    @DeleteMapping(value = "goods", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteGoods(@RequestBody Good goods) {
        goodService.deleteGoods(goods);
        return ok(null);
    }

    @DeleteMapping(value = "goods/{seq}/goodsOp", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteGoodsOpByGoodsIdx(@PathVariable(name = "seq") Long goodsIdx) {
        goodService.deleteGoodsOpByGoodsIdx(goodsIdx);
        return ok(null);
    }
}
