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

//DeleteMapping(value="goodOp") -> goodOp를 삭제하는 메서드 ,
//                                 파라미터로 받는 리스트중 저장되어있지않은 goodOp가 있을수도있기때문에,
//                                 삭제되지않은 List를 리턴함
//PostMapping(value="goodOp")   -> goodOp를 업데이트 하는 메서드,

@RestController
public class GoodOpController {
    private final GoodOpService goodOpService;

    public GoodOpController(GoodOpService goodOpService) {
        this.goodOpService = goodOpService;
    }

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
