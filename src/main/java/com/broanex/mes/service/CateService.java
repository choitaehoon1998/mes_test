package com.broanex.mes.service;

import com.broanex.mes.entity.Cate;
import com.broanex.mes.repository.CateRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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

    private String createCateCode(String parentCateCode) {
        // 이미 6자리라면 , 자식을 가질수없기때문에 IllegalArgumentException 던져야함
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
