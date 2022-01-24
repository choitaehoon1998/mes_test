package com.broanex.mes.service;

/*
 * 코드작성자 : 최태훈
 * 소스설명 : MES의 상품 이미지를 관리하는 Service 역활을 한다.
 * 관련 DB 테이블 :  mes_goods_Image
 * */

import com.broanex.mes.entity.Good;
import com.broanex.mes.entity.GoodImage;
import com.broanex.mes.repository.GoodImageRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.OptionalLong;

/*
 * 동작방식 (R: RETURN TYPE, P: PARAMETER TYPE)
 * 1. saveGoodImages R:[없음] P:[Good , HashMap<String, String> ]  : 저장된, Good 과 HashMap의 담긴 Image의 이름을 통하여 mes_goods_image 테이블에 저장함.
 */

@Service
public class GoodImageService {
    private final GoodImageRepository goodImageRepository;

    public GoodImageService(GoodImageRepository goodImageRepository) {
        this.goodImageRepository = goodImageRepository;
    }

    public void saveGoodImages(Good goods, HashMap<String, String> filePathHashMap) {
        Long orders = 1L;
        Long lastIndex = goodImageRepository.getLastIndexOfGoodImage().orElse(0L);

        for (String key : filePathHashMap.keySet()) {
            GoodImage goodImage = GoodImage.builder().indexNo(++lastIndex).good(goods).filename(key).order(orders++).build();
            goodImageRepository.save(goodImage);
        }
    }
}
