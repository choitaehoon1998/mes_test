package com.broanex.mes.service;

import com.broanex.mes.entity.Good;
import com.broanex.mes.entity.GoodImage;
import com.broanex.mes.repository.GoodImageRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.OptionalLong;

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
