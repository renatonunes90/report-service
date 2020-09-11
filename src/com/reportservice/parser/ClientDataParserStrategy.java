package com.reportservice.parser;

import java.util.Scanner;

import com.google.common.annotations.VisibleForTesting;
import com.reportservice.entity.Client;
import com.reportservice.exception.ParserException;
import com.reportservice.store.ClientStore;
import com.reportservice.util.ErrorMessage;

public class ClientDataParserStrategy extends DataParserStrategy {

	private ClientStore clientStore;

	public ClientDataParserStrategy() {
		clientStore = ClientStore.getInstance();
	}
	
	@VisibleForTesting
	protected ClientDataParserStrategy(ClientStore clientStore) {
		this.clientStore = clientStore;
	}
	
	@Override
	public void parserAndSave(final String toBeParsed) throws ParserException {
		if ( toBeParsed == null || toBeParsed.trim().isEmpty() ) {
			throw new ParserException(ErrorMessage.CLIENT_EMPTY_LINE);
		} 
		
		final Scanner scanner = this.createScanner(toBeParsed);

		final String cnpj = scanner.hasNext() ? scanner.next().trim() : "";
		final String name = scanner.hasNext() ? scanner.next().trim() : "";
		final String businessArea = scanner.hasNext() ? scanner.next().trim() : "";
		
		scanner.close();

		if ( cnpj.isEmpty() || name.isEmpty() || businessArea.isEmpty() ) {
			throw new ParserException(ErrorMessage.CLIENT_MISSING_INFO);
		} 
		
		final Client client = new Client();
		client.setCnpj(cnpj);
		client.setName(name);
		client.setBusinessArea(businessArea);	
		clientStore.addItem(client);
	}

}
