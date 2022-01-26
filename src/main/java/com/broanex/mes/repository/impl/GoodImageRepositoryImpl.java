package com.broanex.mes.repository.impl;

/*
 * 코드작성자 : 최태훈
 * 소스설명 : MES의 상품이미지를 관리하는 Repository 역활을 한다.
 * 관련 DB 테이블 :  mes_goods_img
 * */

import com.broanex.mes.repository.GoodImageQueryRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.broanex.mes.entity.QGoodImage.goodImage;

/*
 * 동작방식 (R: RETURN TYPE, P: PARAMETER TYPE)
 * 1. getLastIndexOfGoodImage R:[Optional<Long>] P:[없음] : goodImage의 마지막 Index를 조회한다, 없을 경우 존재하기 때문에 Optional로 리턴한다.
 */

@RequiredArgsConstructor
public class GoodImageRepositoryImpl implements GoodImageQueryRepository {
	private final JPAQueryFactory queryFactory;

	@Override
	public Optional<Long> getLastIndexOfGoodImage() {
		return Optional.ofNullable(
				queryFactory
						.select(goodImage.indexNo)
						.from(goodImage)
						.orderBy(goodImage.indexNo.desc())
						.fetchFirst()
		);
	}
}
