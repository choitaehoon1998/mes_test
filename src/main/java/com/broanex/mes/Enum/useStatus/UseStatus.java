package com.broanex.mes.Enum.useStatus;


import com.broanex.mes.Enum.CodeValue;

// Good에서 UseOp라는 컬럼에서 사용하는 Enum

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
