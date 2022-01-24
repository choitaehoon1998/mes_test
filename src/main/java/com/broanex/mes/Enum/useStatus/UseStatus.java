package com.broanex.mes.Enum.useStatus;

/*
 * 코드작성자 : 최태훈
 * 소스설명 : MES상의 YN으로 구성되는 값들을 보기 쉽게 하기 위하여 만든 ENUM
 * 관련 DB 테이블 :  없음
 * */

import com.broanex.mes.Enum.CodeValue;

public enum UseStatus implements CodeValue {
    Y("Y", "사용"),
    N("N", "미사용");

    private String code;
    private String value;

    UseStatus(String code, String value) {
        this.code = code;
        this.value = value;
    }


    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getValue() {
        return value;
    }
}
