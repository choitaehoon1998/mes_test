package com.broanex.mes.service;

import com.broanex.mes.Enum.useStatus.UseStatus;
import com.broanex.mes.entity.Good;
import com.broanex.mes.entity.GoodOp;
import com.broanex.mes.repository.GoodOpRepository;
import com.broanex.mes.repository.GoodRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class GoodService {
    private final GoodRepository goodRepository;
    private final GoodOpRepository goodOpRepository;

    public GoodService(GoodRepository goodRepository, GoodOpRepository goodOpRepository) {
        this.goodRepository = goodRepository;
        this.goodOpRepository = goodOpRepository;
    }

    private boolean isExist(Long indexNo) {
        if (indexNo != null && goodRepository.existsById(indexNo)) {
            return true;
        }
        return false;
    }

    private boolean isUse(UseStatus useStatus) {
        if (useStatus != null && useStatus.getValue().equals(UseStatus.Y.getValue())) {
            return true;
        }
        return false;
    }

    public List<Good> findAllGoods(HashMap<String, Object> hashMap) {
        List<Good> GoodsList = goodRepository.findAllUsingQueryDsl(hashMap);
        return GoodsList;
    }

    public void saveOrUpdateGoods(Good goods) {
        // 1. 신규로 (상품 1, 상품 옵션 1,2,3)을 저장한뒤,
        // 업데이트로 (상품 1, 상품 옵션 2,3) 을 보냈을경우 상품 옵션 1은 여전히 남아있음
        // GoodOP 관련 DELETE 부분을 추가하여 삭제하고싶을경우 해당하는 메소드를 이용하도록 .
        if (isUse(goods.getUseOp())) {
            for (GoodOp goodsOp : goods.getGoodOpList()) {
                goodsOp.setGood(goods);
            }
        } else {
            goods.setGoodOpList(new ArrayList<>());
        }
        if (goods.getRegidate() == null) {
            goods.setRegidate(LocalDateTime.now());
        }
        goodRepository.save(goods);
    }

    public void deleteGoods(Good goods) {
        if (isExist(goods.getIndexNo())) {
            goodRepository.deleteById(goods.getIndexNo());
        }
    }

    public void updateGoods(Good goods) {
        if (isExist(goods.getIndexNo())) {
            saveOrUpdateGoods(goods);
        }
    }

    public void deleteGoodsOpByGoodsIdx(Long goodsIdx) {
        if (isExist(goodsIdx)) {
            List<Long> goodOpIdxList = goodOpRepository.findAllGoodOpIdxByGoodsIdxUsingQueryDsl(goodsIdx);
            for (Long goodOpsIdx : goodOpIdxList) {
                goodOpRepository.deleteById(goodOpsIdx);
            }
        }
    }

}
