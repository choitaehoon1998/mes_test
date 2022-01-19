package com.broanex.mes.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.broanex.mes.entity.QGoodOp.goodOp;

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
