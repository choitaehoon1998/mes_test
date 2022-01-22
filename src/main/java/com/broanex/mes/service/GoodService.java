package com.broanex.mes.service;

import com.broanex.mes.Enum.useStatus.UseStatus;
import com.broanex.mes.entity.Good;
import com.broanex.mes.entity.GoodOp;
import com.broanex.mes.repository.GoodOpRepository;
import com.broanex.mes.repository.GoodRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// isExist                 -> 파리미터로 전달받은 goodIndexNo가 db에 실제로 존재하는지 확인함.
// isUse                   -> 파라미터로 전달받은 useStauts 가 Y가 맞는지 확인함.
// findAllGoods            -> 파리미터로 전달받은 값들을 통하여 Good 를 조회함.
// saveOrUpdateGoods       -> 파라미터로 전달받은 good 을 업데이트하거나, 저장함.
// deleteGoods             -> goods를 삭제함
// updateGoods             -> goods을 업데이트함.
// deleteGoodsOpByGoodsIdx -> goodIdx에 해당하는 goodsOp를 전부 삭제함.
@Service
public class GoodService {
    private final GoodRepository goodRepository;
    private final GoodOpRepository goodOpRepository;
    private final FileService fileService;
    private final GoodImageService goodImageService;

    public GoodService(GoodRepository goodRepository,
                       GoodOpRepository goodOpRepository,
                       FileService fileService, GoodImageService goodImageService) {
        this.goodRepository = goodRepository;
        this.goodOpRepository = goodOpRepository;
        this.fileService = fileService;
        this.goodImageService = goodImageService;
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

    public void saveOrUpdateWithFiles(Good goods, List<MultipartFile> filesList) throws IOException {
        goods = saveOrUpdateGoods(goods);
        HashMap<String, String> filePathHashMap = fileService.uploadFiles(filesList);
        goodImageService.saveGoodImages(goods,filePathHashMap);
    }

    private Good saveOrUpdateGoods(Good goods) {
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
        return goodRepository.save(goods);
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
