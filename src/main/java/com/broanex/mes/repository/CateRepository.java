package com.broanex.mes.repository;

import com.broanex.mes.entity.Cate;
import org.springframework.data.jpa.repository.JpaRepository;

//findByCatecode -> cateCode로 Cate를 찾는 메서드

public interface CateRepository extends JpaRepository<Cate, Long>, CateQueryRepository {
    Cate findByCatecode(String catecode);
}
