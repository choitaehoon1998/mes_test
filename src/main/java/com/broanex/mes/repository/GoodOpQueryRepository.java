package com.broanex.mes.repository;

import java.util.List;

public interface GoodOpQueryRepository {
    List<Long> findAllGoodOpIdxByGoodsIdxUsingQueryDsl(Long goodIdx);
}
