package com.reportservice.util;

import java.util.Arrays;

public enum DataType {
	SALESMAN_DATA("001"), CLIENT_DATA("002"), SALE_DATA("003");

	private final String value;

	DataType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	} 
	
    public static DataType fromSigla(final String value){
        for (DataType dataType : values()) {
            if (dataType.getValue().equalsIgnoreCase(value)) {
                return dataType;
            }
        }

        throw new IllegalArgumentException(String.format(ErrorMessage.INVALID_DATA_TYPE.getMessage(), value, Arrays.toString(values())));
	}
}
