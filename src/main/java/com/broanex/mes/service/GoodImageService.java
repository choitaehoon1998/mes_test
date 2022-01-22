package com.broanex.mes.service;

import com.broanex.mes.entity.Good;
import com.broanex.mes.entity.GoodImage;
import com.broanex.mes.repository.GoodImageRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class GoodImageService {
    private final GoodImageRepository goodImageRepository;

    public GoodImageService(GoodImageRepository goodImageRepository) {
        this.goodImageRepository = goodImageRepository;
    }

    public void saveGoodImages(Good goods, HashMap<String, String> filePathHashMap) {
        List<GoodImage> goodImageList = new ArrayList<>();
        for(String key: filePathHashMap.keySet()){
            System.out.println(key);
            System.out.println(filePathHashMap.get(key));
            GoodImage.builder().good(goods).filename(key).build();
        }
    }
}
