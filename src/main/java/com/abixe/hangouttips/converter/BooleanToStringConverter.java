/*
 * BooleanToStringConverter.java
 * Create Date: Aug 11, 2019
 * Initial-Author: Janos Aron Kiss
 */
package com.abixe.hangouttips.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * This class can be used to convert boolean value into database column representation and back again.
 * @author kjaron83
 */
@Converter
public class BooleanToStringConverter implements AttributeConverter<Boolean, String> {

    public static final String TRUE = "1";
    public static final String FALSE = "0";

    @NonNull
    @Override
    public String convertToDatabaseColumn(@Nullable Boolean value) {
        return ( value != null && value ) ? TRUE : FALSE;
    }

    @NonNull
    @Override
    public Boolean convertToEntityAttribute(@Nullable String value) {
        return TRUE.equals(value);
    }

}
