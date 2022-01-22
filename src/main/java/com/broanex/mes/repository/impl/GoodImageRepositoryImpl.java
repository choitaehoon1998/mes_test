package com.broanex.mes.repository.impl;

import com.broanex.mes.repository.GoodImageQueryRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GoodImageRepositoryImpl implements GoodImageQueryRepository {
    private final JPAQueryFactory queryFactory;
}
