package com.broanex.mes.controller;

/*
 * 코드작성자 : 최태훈
 * 소스설명 : MES의 상품 추가 정보를 관리하는 CONTROLLER 역활을 한다.
 * 관련 DB 테이블 :  mes_goods_op
 * */

import com.broanex.mes.entity.GoodOp;
import com.broanex.mes.service.GoodOpService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.http.ResponseEntity.ok;

/*
 * 동작방식
 * 1. /goodOp  [DELETE] : goodOp리스트를 인자로 받아 mes_goods_op 에서 삭제후, 삭제되지않은 리스트는 다시 리턴한다.
 * 2. /goodOp  [POST]   : goodOp를 인자로받아 , 해당되는 내용을 mes_goods_op 에서 업데이트 한다.
 */
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

    @PostMapping(value = "goodOp", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateGoodsOp(GoodOp goodOp) {
        goodOpService.updateGoodOp(goodOp);
        return ok(null);
    }
}
