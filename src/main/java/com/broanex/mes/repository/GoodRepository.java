package com.broanex.mes.repository;

import com.broanex.mes.entity.Good;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodRepository extends JpaRepository<Good,Long> ,GoodQueryRepository {
}
