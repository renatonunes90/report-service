package com.reportservice.builder;

import com.reportservice.entity.Client;

public class ClientBuilder {

	private ClientBuilder() {}
	
	public static Client defaults() {
		final Client item = new Client();
		item.setBusinessArea("businessArea");
		item.setCnpj("0000");
		item.setName("clientName");
		return item;
	}
	
	public static Client withCNPJ(final String cnpj) {
		final Client item = new Client();
		item.setBusinessArea("businessArea");
		item.setCnpj(cnpj);
		item.setName("clientName");
		return item;
	}
}
