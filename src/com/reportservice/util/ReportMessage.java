package com.reportservice.util;

public enum ReportMessage {

	CLIENT_SIZE("Quantidade de clientes no arquivo de entrada: %d \n"),
	SALESMAN_SIZE("Quantidade de vendedores no arquivo de entrada: %d \n"),
	MOST_EXPENSIVE_SALE("ID da venda mais cara: %s \n"),
	WORST_SALESMAN("O pior vendedor: %s")
	;
	
    private final String message;
    
	private ReportMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
