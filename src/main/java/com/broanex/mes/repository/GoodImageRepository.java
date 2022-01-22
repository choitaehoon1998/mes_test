package com.broanex.mes.repository;

import com.broanex.mes.entity.GoodImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodImageRepository extends JpaRepository<GoodImage, Long>, GoodImageQueryRepository {
}
