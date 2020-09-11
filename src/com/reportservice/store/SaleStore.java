package com.reportservice.store;

import com.reportservice.entity.Sale;

/**
 * Store to encapsulate all sales loaded from file.
 */
public class SaleStore extends BaseStore<Long, Sale> {

	private static SaleStore instance;
	
	private SaleStore() {
		super();
	}
	
	public static SaleStore getInstance() {
		if ( instance == null ) {
			instance = new SaleStore();
		}
		return instance;
	}
	
	@Override
	public Long getKeyValue(Sale newItem) {
		return newItem.getSaleId();
	}
}
