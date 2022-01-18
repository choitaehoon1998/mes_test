package com.broanex.mes.service;

import com.broanex.mes.entity.GoodOp;
import com.broanex.mes.repository.GoodOpRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GoodOpService {
    private final GoodOpRepository goodOpRepository;

    public GoodOpService(GoodOpRepository goodOpRepository) {
        this.goodOpRepository = goodOpRepository;
    }

    private boolean isExist(GoodOp goodOp) {
        if (goodOp.getIndexNo() != null && goodOpRepository.existsById(goodOp.getIndexNo())) {
            return true;
        }
        return false;
    }

    public List<GoodOp> deleteAllByList(List<GoodOp> goodopList) {
        List<GoodOp> undeletedGoodOp = new ArrayList<>();
        for (GoodOp goodOp : goodopList) {
            if (isExist(goodOp)) {
                goodOpRepository.deleteById(goodOp.getIndexNo());
            } else {
                undeletedGoodOp.add(goodOp);
            }
        }
        return undeletedGoodOp;
    }

    public void updateGoodOp(GoodOp goodOp) {
        if (isExist(goodOp)) {
            goodOpRepository.save(goodOp);
        }
    }
}
