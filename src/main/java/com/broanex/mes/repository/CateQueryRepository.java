package com.broanex.mes.repository;

import com.broanex.mes.entity.Cate;

import java.util.HashMap;
import java.util.List;

public interface CateQueryRepository {
    List<Cate> findAllCategory(HashMap<String, Object> hashMap);

    List<String> findCateCodeByCateCode(String parentCateCode);

    boolean existsByCateCode(String catecode);
}
