package com.broanex.mes.service;

import com.broanex.mes.repository.GoodCateRepository;
import org.springframework.stereotype.Service;

@Service
public class GoodCateService {
    private final GoodCateRepository goodCateRepository;

    public GoodCateService(GoodCateRepository goodCateRepository) {
        this.goodCateRepository = goodCateRepository;
    }
}
