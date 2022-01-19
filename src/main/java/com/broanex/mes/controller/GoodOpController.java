package com.broanex.mes.controller;

import com.broanex.mes.entity.GoodOp;
import com.broanex.mes.service.GoodOpService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
public class GoodOpController {
    private final GoodOpService goodOpService;

    public GoodOpController(GoodOpService goodOpService) {
        this.goodOpService = goodOpService;
    }

    // 삭제할경우 저장되지않은 goodsOp가 있을수도 있기때문에 삭제 되지않은 list를 리턴함.
    @DeleteMapping(value = "goodOp", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GoodOp>> deleteGoodsOp(@RequestBody List<GoodOp> goodopList) {
        List<GoodOp> undeletedGoodOp = goodOpService.deleteAllByList(goodopList);
        return ok(undeletedGoodOp);
    }

    @PostMapping(value = "goodOp", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateGoodsOp(@RequestBody GoodOp goodOp) {
        goodOpService.updateGoodOp(goodOp);
        return ok(null);
    }
}
