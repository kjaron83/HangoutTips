package com.abixe.hangouttips.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@Converter
public class BooleanToStringConverter implements AttributeConverter<Boolean, String> {
	
	public static final String TRUE = "1";
	public static final String FALSE = "0";

	@NonNull
    @Override
    public String convertToDatabaseColumn(@Nullable Boolean value) {        
        return (value != null && value) ? TRUE : FALSE;            
    }    

	@NonNull
    @Override
    public Boolean convertToEntityAttribute(@Nullable String value) {
        return TRUE.equals(value);
    }
	
}
