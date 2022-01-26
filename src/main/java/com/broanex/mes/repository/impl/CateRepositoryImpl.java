package com.broanex.mes.repository.impl;

/*
 * 코드작성자 : 최태훈
 * 소스설명 : MES의 카테고리를 관리하는 Repository 역활을 한다.
 * 관련 DB 테이블 :  mes_cate
 * */

import com.broanex.mes.entity.Cate;

import static com.broanex.mes.entity.QCate.cate;

import com.broanex.mes.repository.CateQueryRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/*
 * 동작방식 (R: RETURN TYPE, P: PARAMETER TYPE)
 * 1. findAllCategory R:[List<Cate>] P:[HashMap<String,Object>]  : mes_cate 에서 hashMap 에 담긴 정보들에 부합하는 정보들만 조회한다.
 * 2. findCateCodeByCateCode R:[List<String>] P:[String]         : mes_cate 에서 parentCateCode의 내용을 포함하는것 만을 조회하고 조회한것중 parentCateCode보다 2자리 긴것만 찾아 리턴한다.
 * 3. existsByCateCode R:[boolean] P:[String]                    : catecode로 검색하여 존재한다면 true , 아니라면 false 를 리턴한다.
 * 4. eqIndexNo R:[BooleanExpression] P:[Long]                   : indexNo가 같은것을 조회하기위하여 사용하는 메서드
 * 5. eqCateName R:[BooleanExpression] P:[String]                : cateName을 포함하는것을 조회하기위하여 사용하는 메서드
 * 6. eqUpcCate R:[BooleanExpression] P:[Long]                   : upccate가 같은것을 조회하기위하여 사용하는 메서드
 * 7. tailLikeCateCode R:[BooleanExpression] P:[String]          : 파라미터로 전달받은 catecode과 앞부분이 같은 것을 조회하기위하여 사용하는 메서드
 * 8. eqCateCode R:[BooleanExpression] P:[String]                : 파리미터 전달받아느 catecode와 동일한것을 조회하기위하여 사용하는 메서드,
 */

@RequiredArgsConstructor
public class CateRepositoryImpl implements CateQueryRepository {
	private final JPAQueryFactory queryFactory;

	@Override
	public List<Cate> findAllCategory(HashMap<String, Object> hashMap) {
		return queryFactory.selectFrom(cate).where(
						eqIndexNo((Long) hashMap.get("indexNo")),
						eqCateName((String) hashMap.get("cateName")),
						eqCateCode((String) hashMap.get("cateCode")),
						eqUpcCate((Long) hashMap.get("upccate")))
				.leftJoin(cate)
				.on(cate.indexNo.eq(cate.parentCate.indexNo))
				.fetchJoin()
				.fetch();
	}

	@Override
	public List<String> findCateCodeByCateCode(String parentCateCode) {
		return queryFactory
				.select(cate.catecode)
				.from(cate)
				.where(tailLikeCateCode(parentCateCode))
				.orderBy(cate.catecode.asc())
				.fetch()
				.stream()
				.filter(targetString -> targetString.length() == parentCateCode.length() + 2)
				.collect(Collectors.toList());
	}

	@Override
	public boolean existsByCateCode(String catecode) {
		Integer result = queryFactory
				.selectOne()
				.from(cate)
				.where(eqCateCode(catecode))
				.fetchFirst();
		return result != null;
	}


	private BooleanExpression eqIndexNo(Long indexNo) {
		if (indexNo == null) {
			return null;
		}
		return cate.indexNo.eq(indexNo);
	}

	private BooleanExpression eqCateName(String cateName) {
		if (StringUtils.hasText(cateName)) {
			return cate.cateName.contains(cateName);
		}
		return null;
	}

	private BooleanExpression eqUpcCate(Long upccate) {
		if (upccate == null) {
			return null;
		}
		return cate.parentCate.indexNo.eq(upccate);
	}

	private BooleanExpression tailLikeCateCode(String cateCode) {
		if (StringUtils.hasText(cateCode)) {
			return cate.catecode.like(cateCode + "%");
		}
		return null;
	}

	private BooleanExpression eqCateCode(String cateCode) {
		if (StringUtils.hasText(cateCode)) {
			return cate.catecode.eq(cateCode);
		}
		return null;
	}
}
