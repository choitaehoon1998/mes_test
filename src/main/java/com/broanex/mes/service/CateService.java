package com.broanex.mes.service;

import com.broanex.mes.entity.Cate;
import com.broanex.mes.repository.CateRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

// isExist         -> 파라미터로 전달 받은 cate가 DB에 존재하는 Cate인지 확인하는 메서드
// createCateCode  -> 파라미터로 전달받은 parentCateCode를 통하여, 하위 CateCode를 생성함
//                    파리미터로 전달받은 parentCateCode가 5자리 초과라면 IllegalArugement Exception을 던짐
// findAllCategory -> 전달 받은 파리미터로 모든 category 를 검색함
// saveOrUpdate    -> 전달 받은 파리미터를 저장하거나 , 업데이트 함
// deleteCategory  -> 전달 받은 파라미터를 삭제함 .

@Service
public class CateService {
    private final CateRepository cateRepository;

    public CateService(CateRepository cateRepository) {
        this.cateRepository = cateRepository;
    }

    private boolean isExist(Cate cate) {
        if (cate.getIndexNo() != null && cateRepository.existsById(cate.getIndexNo())) {
            return true;
        }
        return false;
    }

    // TODO createCateCode 추가 개선 현재 한메서드에서 여러가지 작업 처리중
    private String createCateCode(String parentCateCode) {
        if (parentCateCode.length() > 5) {
            throw new IllegalArgumentException("하위 카테고리를 추가할수없는 카테고리를 선택하셨습니다.");
        }
        StringBuilder builder = new StringBuilder();
        builder.append(parentCateCode);
        List<String> startsWithParentCateCodeStringList = cateRepository.findCateCodeByCateCode(parentCateCode);
        if (startsWithParentCateCodeStringList.isEmpty()) {
            builder.append("01");
        } else {
            String lastString = startsWithParentCateCodeStringList.get(startsWithParentCateCodeStringList.size() - 1);
            String lastStringLastTwo = lastString.substring(lastString.length() - 2);
            int hexInt = Integer.parseInt(lastStringLastTwo, 16) + 1;
            String hexString = Integer.toHexString(hexInt);
            if (hexString.length() == 1) {
                hexString = "0" + hexString;
            }
            builder.append(hexString);
        }
        String newHexString = builder.toString();
        return newHexString;
    }

    public List<Cate> findAllCategory(HashMap<String, Object> hashMap) {
        List<Cate> cateList = cateRepository.findAllCategory(hashMap);
        return cateList;
    }

    public void saveOrUpdate(Cate cate) {
        Cate parentCate = cate.getParentCate();
        if (parentCate != null && parentCate.getIndexNo() != null && isExist(parentCate)) {
            parentCate = cateRepository.findById(parentCate.getIndexNo()).get();
            cate.setCatecode(createCateCode(parentCate.getCatecode()));
            cate.setParentCate(parentCate);
        } else {
            cate.setParentCate(null);
            cate.setCatecode(createCateCode(""));
        }
        cateRepository.save(cate);
    }

    public void deleteCategory(Cate cate) {
        if (isExist(cate)) {
            cateRepository.deleteById(cate.getIndexNo());
        }
    }

}
