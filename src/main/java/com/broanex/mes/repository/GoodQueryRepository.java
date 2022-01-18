package com.broanex.mes.repository;

import com.broanex.mes.entity.Good;

import java.util.HashMap;
import java.util.List;

public interface GoodQueryRepository {
    List<Good> findAllUsingQueryDsl(HashMap<String, Object> hashMap);
}
