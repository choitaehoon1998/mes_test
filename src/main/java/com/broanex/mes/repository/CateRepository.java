package com.broanex.mes.repository;

import com.broanex.mes.entity.Cate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CateRepository  extends JpaRepository<Cate,Long> , CateQueryRepository {
    Cate findByCatecode(String catecode);
}
