package com.broanex.mes.repository;

import com.broanex.mes.entity.GoodOp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodOpRepository extends JpaRepository<GoodOp, Long>, GoodOpQueryRepository {
}
