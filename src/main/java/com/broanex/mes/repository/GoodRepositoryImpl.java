package com.broanex.mes.repository;

import com.broanex.mes.Enum.useStatus.UseStatus;
import com.broanex.mes.entity.Good;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;

import static com.broanex.mes.entity.QGood.good;
import static com.broanex.mes.entity.QGoodOp.goodOp;

@RequiredArgsConstructor
public class GoodRepositoryImpl implements GoodQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Good> findAllUsingQueryDsl(HashMap<String, Object> hashMap) {

        return queryFactory.selectFrom(good)
                .where(eqIndexNo((Long) hashMap.get("indexNo")),
                        eqGName((String) hashMap.get("gname")),
                        eqAccount((Long) hashMap.get("account")),
                        eqUseOp((UseStatus) hashMap.get("useop")))
                .leftJoin(good.goodOpList, goodOp).fetchJoin().fetch();
    }

    private BooleanExpression eqIndexNo(Long indexNo) {
        if (indexNo == null) {
            return null;
        }
        return good.indexNo.eq(indexNo);
    }

    private BooleanExpression eqGName(String gname) {
        if (StringUtils.hasText(gname)) {
            return good.gName.contains(gname);
        }
        return null;
    }

    private BooleanExpression eqAccount(Long account) {
        if (account == null) {
            return null;
        }
        return good.account.eq(account);
    }

    private BooleanExpression eqUseOp(UseStatus useStatus) {
        if (useStatus == null) {
            return null;
        }
        if (useStatus.getValue().equals(UseStatus.Y.getValue())) {
            return good.useOp.eq(UseStatus.Y);
        }
        if (useStatus.getValue().equals(UseStatus.N.getValue())) {
            return good.useOp.eq(UseStatus.N);
        }
        return null;
    }
}
