package com.broanex.mes.Enum;

/*
 * 코드작성자 : 최태훈
 * 소스설명 : MES상의 기준이 되는 값들을 관리하기 위하여 사용하는 클래스를 추상화 하기위하여 만든 인터페이스 
 * 관련 DB 테이블 :  없음
 * */

public interface CodeValue {
    String getCode();
    String getValue();
}
