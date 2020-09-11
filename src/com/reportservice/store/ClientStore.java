package com.reportservice.store;

import com.reportservice.entity.Client;

/**
 * Store to encapsulate all clients loaded from file.
 */
public class ClientStore extends BaseStore<String, Client> {

	private static ClientStore instance;
	
	private ClientStore() {
		super();
	}
	
	public static ClientStore getInstance() {
		if ( instance == null ) {
			instance = new ClientStore();
		}
		return instance;
	}
	
	@Override
	public String getKeyValue(Client newItem) {
		return newItem.getCnpj();
	}
}
