package com.broanex.mes.repository.impl;

import com.broanex.mes.repository.GoodImageQueryRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.OptionalLong;

import static com.broanex.mes.entity.QGoodImage.goodImage;

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
