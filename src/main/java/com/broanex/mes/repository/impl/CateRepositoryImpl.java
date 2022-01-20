package com.broanex.mes.repository.impl;

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

// findAllCateGory        -> indexNo, cateName, cateCode, upccate로 Category 를 조회하는 메소드
// findCateCodeByCateCode -> parentCateCode 를 이용하여,
//                           parentCateCode% 로 라이크 검색하고, 길이가 parentCateCode.length+ 2인것만 조회함.
// existsByCateCode       -> CateCode를 통하여, Cate가 존재하는지 확인함.
// 이하 private 메소드      -> 검색 조건을 처리하기위한 메소드들

@RequiredArgsConstructor
public class CateRepositoryImpl implements CateQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Cate> findAllCategory(HashMap<String, Object> hashMap) {
        return queryFactory.selectFrom(cate).where(
                        eqIndexNo((Long) hashMap.get("indexNo")),
                        eqCateName((String) hashMap.get("cateName")),
                        likeCateCode((String) hashMap.get("cateCode")),
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

    private BooleanExpression likeCateCode(String cateCode) {
        if (StringUtils.hasText(cateCode)) {
            return cate.catecode.eq(cateCode);
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
