package com.broanex.mes.Enum.useStatus;

/*
 * 코드작성자 : 최태훈
 * 소스설명 : MES상의 YN으로 구성되는 값들을 쉽게 변경해주는 클래스
 * 관련 DB 테이블 :  없음
 * */


import javax.persistence.AttributeConverter;
import java.util.EnumSet;
import java.util.NoSuchElementException;

/*
 * 동작방식
 * 1. convertToDatabaseColumn  : UseStatus를 사용하는 컬럼에서 , DB에 저장할 경우 값을 변경하여 저장시켜주는 메서드
 * 2. convertToEntityAttribute : UseStatus를 사용하는 컬럼에서 , DB에서 값을 SELECT 할경우 UseStatus 객체로 변경시켜주는 메서드
 */

public class UseStatusConvertor implements AttributeConverter<UseStatus, String> {

	@Override
	public String convertToDatabaseColumn(UseStatus attribute) {
		return attribute.getCode();
	}

	@Override
	public UseStatus convertToEntityAttribute(String dbData) {
		return EnumSet.allOf(UseStatus.class).stream()
				.filter(e -> e.getCode().equals(dbData))
				.findAny()
				.orElseThrow(NoSuchElementException::new);
	}
}
