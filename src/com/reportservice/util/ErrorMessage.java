package com.reportservice.util;

public enum ErrorMessage {

	CLIENT_EMPTY_LINE("Empty line cannot be parser to a client."),
	CLIENT_MISSING_INFO("Missing information to parser a client."),
	ERROR_CREATING_REPORT_FILE("Error creating report file: %s."),
	ERROR_LOADING_INPUT_FILE("Error loading input file: %s."),
	INVALID_DATA_TYPE("Unknown enum type %s for DataType Enum. Allowed values are %s."),
	INVALID_DATA_FORMAT("Invalid Data format in file: %s"),
	SALE_EMPTY_LINE("Empty line cannot be parser to a sale."),
	SALE_ITEM_MISSING_INFO("Missing information to parser a sale item."),
	SALE_MISS_ITEMS("Some items of sale cannot be parsed."),
	SALE_MISSING_INFO("Missing information to parser a sale."),
	SALESMAN_EMPTY_LINE("Empty line cannot be parser to a salesman."),
	SALESMAN_NOT_FOUND("Salesman not found for sale."),
	SALESMAN_MISSING_INFO("Missing information to parser a salesman.")
	;
	
    private final String message;
    
	private ErrorMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
