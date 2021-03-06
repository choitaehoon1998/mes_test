package com.broanex.mes.service;

/*
 * 코드작성자 : 최태훈
 * 소스설명 : MES의 카테고리를 관리하는 Service 역활을 한다.
 * 관련 DB 테이블 :  mes_cate
 * */


import com.broanex.mes.entity.Cate;
import com.broanex.mes.repository.CateRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/*
 * 동작방식 (R: RETURN TYPE, P: PARAMETER TYPE)
 * 1. isExist R:[Cate] P:[boolean]                                : 파라미터로 전달 받은 cate가 DB에 존재하는 Cate인지 확인하는 메서드
 * 2. isExistByCateCode R:[Cate] P:[boolean]                      : 파라미터로 전달 받은 cate의 해당하는 cateCode가 DB에 존재하는 Cate인지 확인하는 메서드
 * 3. createCateCode R:[String] P:[String]                        : 파라미터로 전달받은 parentCateCode를 통하여, 하위 CateCode를 생성함
 *                                                                  파리미터로 전달받은 parentCateCode가 5자리 초과라면 IllegalArugement Exception을 던짐
 * 4. findAllCategory R:[List<Cate>] P:[HashMap<String, Object>]  : 전달 받은 파리미터로 모든 category 를 검색함
 * 5. saveOrUpdate R:[없음] P:[Cate]                               : 전달 받은 파리미터를 저장하거나 , 업데이트 함
 * 6. deleteCategory R:[없음] P:[Cate]                             : 전달 받은 파라미터를 삭제함 .
 */


@Service
public class CateService {
	private final CateRepository cateRepository;

	public CateService(CateRepository cateRepository) {
		this.cateRepository = cateRepository;
	}

	private boolean isExist(Cate cate) {
		if (cate.getIndexNo() != null && cateRepository.existsById(cate.getIndexNo())) {
			return true;
		}
		return false;
	}

	private boolean isExistByCateCode(Cate cate) {
		if (cate.getCatecode() != null && cateRepository.existsByCateCode(cate.getCatecode())) {
			return true;
		}
		return false;
	}

	private String createCateCode(String parentCateCode) {
		if (parentCateCode.length() > 5) {
			throw new IllegalArgumentException("하위 카테고리를 추가할수없는 카테고리를 선택하셨습니다.");
		}
		StringBuilder builder = new StringBuilder();
		builder.append(parentCateCode);
		List<String> startsWithParentCateCodeStringList = cateRepository.findCateCodeByCateCode(parentCateCode);
		if (startsWithParentCateCodeStringList.isEmpty()) {
			builder.append("01");
		} else {
			String lastString = startsWithParentCateCodeStringList.get(startsWithParentCateCodeStringList.size() - 1);
			String lastStringLastTwo = lastString.substring(lastString.length() - 2);
			int hexInt = Integer.parseInt(lastStringLastTwo, 16) + 1;
			String hexString = Integer.toHexString(hexInt);
			if (hexString.length() == 1) {
				hexString = "0" + hexString;
			}
			builder.append(hexString);
		}
		String newHexString = builder.toString();
		return newHexString;
	}

	public List<Cate> findAllCategory(HashMap<String, Object> hashMap) {
		List<Cate> cateList = cateRepository.findAllCategory(hashMap);
		return cateList;
	}

	public void saveOrUpdate(Cate cate) {
		Cate parentCate = cate.getParentCate();
		if (parentCate != null && isExistByCateCode(parentCate)) {
			parentCate = cateRepository.findByCatecode(parentCate.getCatecode());
			cate.setCatecode(createCateCode(parentCate.getCatecode()));
			cate.setParentCate(parentCate);
		} else {
			cate.setParentCate(null);
			cate.setCatecode(createCateCode(""));
		}
		cateRepository.save(cate);
	}

	public void deleteCategory(Cate cate) {
		if (isExist(cate)) {
			cateRepository.deleteById(cate.getIndexNo());
		}
	}

}
