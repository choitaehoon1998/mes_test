package com.broanex.mes.repository;

import com.broanex.mes.entity.GoodCate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodCateRepository extends JpaRepository<GoodCate, Long>, GoodCateQueryRepository {
}
