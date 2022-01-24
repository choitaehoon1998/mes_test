package com.broanex.mes.controller;

/*
 * 코드작성자 : 최태훈
 * 소스설명 : MES의 상품을 관리하는 CONTROLLER 역활을 한다.
 * 관련 DB 테이블 : mes_goods , mes_goods_op
 * */

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

/*
 * 동작방식
 * 1. /goods                [GET]    : good의 정보를 인자(gname, indexNo, account,useop)로 받아 mes_goods에서 조회후, JSON 형태로 리턴해준다.
 * 2. /goods                [POST]   : good의 정보를 form-date 형태로 post 로 전달받아 mes_goods 에 저장후 , image 가 있을경우 서버에 해당 파일을 저장하고 mes_goods_img 테이블에 삽입
 * 3. /goods                [PUT]    : good의 정보를 전달받아 mes_goods에 해당하는 내용을 업데이트한다..
 * 4. /goods                [DELETE] : good의 정보를 전달받아 해당하는 내용을 mes_goods 에서 삭제한다.
 * 5. /goods/{seq}/goodsOp  [DELETE] : uri 정보로 전달받은 good의 Index의 해당하는 goodsOp의 내용을 전체 삭제한다.
 */
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
    public ResponseEntity<HashMap<String, String>> postGoods(@RequestPart(value = "good") Good goods,
                                                             @RequestPart(value = "fileList", required = false) List<MultipartFile> fileList)
            throws IOException {
        HashMap<String, String> stringHashMap = goodService.saveWithFiles(goods, fileList);
        return ok(stringHashMap);
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
