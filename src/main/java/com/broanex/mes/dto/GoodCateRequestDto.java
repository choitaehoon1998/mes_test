package com.broanex.mes.dto;

import lombok.Getter;
import lombok.Setter;

//GoodCate를 좀더 쉽게, 저장, 삭제하기위하여 사용하는 Dto

@Getter
@Setter
public class GoodCateRequestDto {
    private Long indexNo;
    private Long goodsIdx;
    private String catecode;
}
