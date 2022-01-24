package com.broanex.mes.service;

/*
 * 코드작성자 : 최태훈
 * 소스설명 : MES의 상품을 관리하는 Service 역활을 한다.
 * 관련 DB 테이블 :  mes_goods , mes_goods_op , mes_goods_image
 * */

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

/*
 * 동작방식 (R: RETURN TYPE, P: PARAMETER TYPE)
 * 1. isExist R:[boolean] P:[Long]                                              : 파리미터로 전달받은 goodIndexNo가 db에 실제로 존재하는지 확인함.
 * 2. isUse R:[boolean] P:[UseStatus]                                           : 파라미터로 전달받은 useStauts 가 Y가 맞는지 확인함.
 * 3. findAllGoods R:[List<Good>] P:[HashMap<String, Object>]                   : 파리미터로 전달받은 값들을 통하여 Good 를 조회함.
 * 4. saveWithFiles R:[HashMap<String, String>] P:[Good , List<MultipartFile>]  : 파라미터로 전달받은 good 과 File을 저장하고, 파일명과 , 파일의 위치를 Map 형식으로 리턴함 (ex: 파일명 : 파일위치 )
 * 5. saveOrUpdateGoods R:[Good] P:[GoodCateRequestDto]                         : 파라미터로 전달받은 good 을 업데이트하거나, 저장함.
 * 6. deleteGoods R:[없음] P:[Good]                                              : goods를 삭제함
 * 7. updateGoods R:[없음] P:[Good]                                              : goods을 업데이트함.
 * 8. deleteGoodsOpByGoodsIdx R:[없음] P:[Long]                                  : goodIdx에 해당하는 goodsOp를 전부 삭제함.
 */

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

    public HashMap<String, String> saveWithFiles(Good goods, List<MultipartFile> filesList) throws IOException {
        goods = saveOrUpdateGoods(goods);
        HashMap<String, String> filePathHashMap = fileService.uploadFiles(filesList);
        goodImageService.saveGoodImages(goods, filePathHashMap);
        return filePathHashMap;
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
