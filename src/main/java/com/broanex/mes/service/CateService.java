package com.broanex.mes.service;

import com.broanex.mes.repository.CateRepository;
import org.springframework.stereotype.Service;

@Service
public class CateService {
    private final CateRepository cateRepository;

    public CateService(CateRepository cateRepository) {
        this.cateRepository = cateRepository;
    }
}
