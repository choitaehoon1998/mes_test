package com.broanex.mes.dto;

/*
 * 코드작성자 : 최태훈
 * 소스설명 : MES의 상품 추가 정보를 GoodCate 를 좀더 쉽게, 저장, 삭제하기위하여 사용하는 Dto
 * 관련 DB 테이블 :  mes_goods_cate
 * */

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodCateRequestDto {
    private Long indexNo;
    private Long goodsIdx;
    private String catecode;
}
