package com.broanex.mes.controller;

/*
 * 코드작성자 : 최태훈
 * 소스설명 : MES의 카테고리를 관리하는 CONTROLLER 역활을 한다.
 * 관련 DB 테이블 : mes_cate
 * */

import com.broanex.mes.entity.Cate;
import com.broanex.mes.service.CateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

import static org.springframework.http.MediaType.*;
import static org.springframework.http.ResponseEntity.ok;

/*
 * 동작방식
 * 1. /cate  [GET]    : 카테고리의 필드(indexNo,cateName,cateCode,upccate)들을 인자로 받아 JSON 형태로 리턴해준다.
 * 2. /cate  [POST]   : 카테고리의 정보를 인자로 받아 mes_cate 에 INSERT 한다.
 * 3. /cate  [DELETE] : 카테고리의 indexNo를 인자로 받아 mes_cate 에서 DELETE 한다.
 */
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

	@PostMapping(value = "cate", consumes = MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Void> postsCate(Cate cate) {
		cateService.saveOrUpdate(cate);
		return ok(null);
	}

	@DeleteMapping(value = "cate", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deleteCate(@RequestBody Cate cate) {
		cateService.deleteCategory(cate);
		return ok(null);
	}

	@PostMapping(value = "exceptionTest")
	public void exceptionTest(HttpServletRequest request) {
		System.out.println(request.getAuthType());
		request.getContextPath();
		throw new ArithmeticException("aabbccddeeff");
	}
}
