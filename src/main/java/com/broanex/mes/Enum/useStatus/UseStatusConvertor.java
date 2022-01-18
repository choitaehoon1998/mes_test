package com.broanex.mes.Enum.useStatus;

import javax.persistence.AttributeConverter;
import java.util.EnumSet;
import java.util.NoSuchElementException;

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
