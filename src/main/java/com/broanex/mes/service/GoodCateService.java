package com.broanex.mes.service;

import com.broanex.mes.dto.GoodCateRequestDto;
import com.broanex.mes.entity.GoodCate;
import com.broanex.mes.repository.CateRepository;
import com.broanex.mes.repository.GoodCateRepository;
import com.broanex.mes.repository.GoodRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class GoodCateService {
    private final GoodCateRepository goodCateRepository;
    private final GoodRepository goodRepository;
    private final CateRepository cateRepository;

    public GoodCateService(GoodCateRepository goodCateRepository,
                           GoodRepository goodRepository,
                           CateRepository cateRepository) {
        this.goodCateRepository = goodCateRepository;
        this.goodRepository = goodRepository;
        this.cateRepository = cateRepository;
    }

    private Boolean checkRequestDto(GoodCateRequestDto requestDto) {
        if (requestDto.getGoodsIdx() != null && goodRepository.existsById(requestDto.getGoodsIdx()) &&
                requestDto.getCatecode() != null && cateRepository.existsByCateCode(requestDto.getCatecode()) &&
                requestDto.getIndexNo() != null && !goodCateRepository.existsById(requestDto.getIndexNo())) {
            return true;
        }
        return false;
    }

    private boolean isExist(GoodCateRequestDto requestDto) {
        if (requestDto.getIndexNo() != null && goodCateRepository.existsById(requestDto.getIndexNo())) {
            return true;
        }
        return false;
    }

    public void saveGoodCate(GoodCateRequestDto requestDto) {
        if (checkRequestDto(requestDto)) {
            goodCateRepository.save(
                    GoodCate
                            .builder()
                            .indexNo(requestDto.getIndexNo())
                            .cate(cateRepository.findByCatecode(requestDto.getCatecode()))
                            .good(goodRepository.getById(requestDto.getGoodsIdx()))
                            .build());
        }
    }
    @Transactional
    public void deleteGoodCate(GoodCateRequestDto requestDto) {
        if (isExist(requestDto)) {
            goodCateRepository.deleteById(requestDto.getIndexNo());
        }
    }


}
