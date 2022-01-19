package com.broanex.mes.service;

import com.broanex.mes.entity.GoodOp;
import com.broanex.mes.repository.GoodOpRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// isExist         -> 파라미터로 전달받은 goodOp가 DB에 실제로 존재하는 메서드 인지 확인함.
// deleteAllByList -> 파라미터로 전달받은 goodOp 리스트 전부 삭제함 , 삭제되지 않은 리스트는 다시 리턴함.
// updateGoodOp    -> 파라미터로 전달받은 goodOp를 업데이트함.

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
