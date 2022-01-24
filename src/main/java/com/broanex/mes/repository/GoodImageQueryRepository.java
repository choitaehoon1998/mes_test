package com.broanex.mes.repository;


import java.util.Optional;

public interface GoodImageQueryRepository {
    Optional<Long> getLastIndexOfGoodImage();
}
