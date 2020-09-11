package com.reportservice.store;

import com.reportservice.entity.Salesman;

/**
 * Store to encapsulate all salesman loaded from file.
 */
public class SalesmanStore extends BaseStore<String, Salesman> {

	private static SalesmanStore instance;
	
	private SalesmanStore() {
		super();
	}
	
	public static SalesmanStore getInstance() {
		if ( instance == null ) {
			instance = new SalesmanStore();
		}
		return instance;
	}
	
	@Override
	public String getKeyValue(Salesman newItem) {
		return newItem.getCpf();
	}
	
	public Salesman getByName(String name){
		return getLoadedItems().values().stream().filter( salesman -> salesman.getName().equals(name) ).findFirst().orElse(null);
	}
}
