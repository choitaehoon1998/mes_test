package com.broanex.mes.repository.impl;

/*
 * 코드작성자 : 최태훈
 * 소스설명 : MES의 상품 추가 정보를 관리하는 Repository 역활을 한다.
 * 관련 DB 테이블 :  mes_goods_op
 * */


import com.broanex.mes.repository.GoodOpQueryRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.broanex.mes.entity.QGoodOp.goodOp;

/*
 * 동작방식 (R: RETURN TYPE, P: PARAMETER TYPE)
 * 1. findAllGoodOpIdxByGoodsIdxUsingQueryDsl R:[List<Long>] P:[Long] : 상품의 인덱스를 조회하여, 해당하는 상품 추가 정보의 index를 전부 리턴한다.
 */
@RequiredArgsConstructor
public class GoodOpRepositoryImpl implements GoodOpQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Long> findAllGoodOpIdxByGoodsIdxUsingQueryDsl(Long goodIdx) {
        return queryFactory.select(goodOp.indexNo).from(goodOp).where(
                goodOp.good.indexNo.eq(goodIdx)
        ).fetch();
    }
}
