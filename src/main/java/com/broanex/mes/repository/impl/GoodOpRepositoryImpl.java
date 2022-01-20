package com.broanex.mes.repository.impl;

import com.broanex.mes.repository.GoodOpQueryRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.broanex.mes.entity.QGoodOp.goodOp;

// findAllGoodOpIdxByGoodsIdxUsingQueryDsl -> goodIdx로 검색하여, goodop 의  indexNo를 리스트 형태로 리턴

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
