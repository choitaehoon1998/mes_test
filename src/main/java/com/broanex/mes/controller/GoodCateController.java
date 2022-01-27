package com.broanex.mes.controller;

/*
 * 코드작성자 : 최태훈
 * 소스설명 : MES의 상품 카테고리를 관리하는 CONTROLLER 역활을 한다.
 * 관련 DB 테이블 : mes_goods_cate
 * */

import com.broanex.mes.dto.GoodCateRequestDto;
import com.broanex.mes.service.GoodCateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.http.ResponseEntity.ok;

/*
 * 동작방식
 * 1. /goodCate  [POST]   : goodCate의 정보를 인자(indexNo, goodsIdx, cateCode)로 받아 mes_goods_cate 에 저장한다.
 * 2. /goodCate  [DELETE] : goodCate의 정보를 인자(indexNo, goodsIdx, cateCode)로 받아 mes_goods_cate 에서 삭제한다.
 */
@RestController
public class GoodCateController {
	private final GoodCateService goodCateService;

	public GoodCateController(GoodCateService goodCateService) {
		this.goodCateService = goodCateService;
	}

	@PostMapping(value = "goodCate", consumes = MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Void> saveGoodCate(@RequestPart(value = "requestDto") GoodCateRequestDto requestDto) {
		goodCateService.saveGoodCate(requestDto);
		return ok(null);
	}

	@DeleteMapping(value = "goodCate", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deleteGoodCate(@RequestBody GoodCateRequestDto requestDto) {
		goodCateService.deleteGoodCate(requestDto);
		return ok(null);
	}
}
