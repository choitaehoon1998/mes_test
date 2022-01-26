package com.broanex.mes.repository.impl;

/*
 * 코드작성자 : 최태훈
 * 소스설명 : MES의 상품을 관리하는 Repository 역활을 한다.
 * 관련 DB 테이블 :  mes_goods
 * */


import com.broanex.mes.Enum.useStatus.UseStatus;
import com.broanex.mes.entity.Good;
import com.broanex.mes.repository.GoodQueryRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;

import static com.broanex.mes.entity.QGood.good;
import static com.broanex.mes.entity.QGoodOp.goodOp;

/*
 * 동작방식 (R: RETURN TYPE, P: PARAMETER TYPE)
 * 1. findAllUsingQueryDsl R:[List<Good>] P:[HashMap<String,Object>]  : mes_goods 에서 hashMap 에 담긴 정보들에 부합하는 정보들만 조회한다.
 * 2. eqIndexNo R:[BooleanExpression] P:[Long]                        : indexNo가 같은것을 조회하기위하여 사용하는 메서드
 * 3. eqGName R:[BooleanExpression] P:[String]                        : gName을 포함하는것을 조회하기위하여 사용하는 메서드
 * 4. eqAccount R:[BooleanExpression] P:[Long]                        : account가 같은것을 조회하기위하여 사용하는 메서드
 * 5. eqUseOp R:[BooleanExpression] P:[UseStatus]                     : useop가 같은것을 조회하기위하여 사용하는 메서드
 */

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
