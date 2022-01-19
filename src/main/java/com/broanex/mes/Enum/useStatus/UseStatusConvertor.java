package com.broanex.mes.Enum.useStatus;

import javax.persistence.AttributeConverter;
import java.util.EnumSet;
import java.util.NoSuchElementException;

// UseStatus를 사용하는 컬럼에서 굳이 y -> 사용 , n -> 미사용 과 같은 방식으로 값을 변경할 필요가 없도록
// AttributeConverter를 상속받아 이용함.
// ConvertToDatabaseColumn 메소드는 Db에 Y,N 값을 저장할수있도록 값을 변경하여 주는 메소드 
// converToEntityAttribute는 String에서 UseStatus객체로 변경해주는 메소드
// TODO 현재 AttributeConverter 를 상속 받을 클래스가 하나 뿐이지만, 사용하는 Enum 이 많아지면,
//      Converter 도 여러개로 늘어나게됨, AttributeConverter를 상속받아 추상화 시킨뒤, 좀더 쉽게 처리할수있도록.
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
