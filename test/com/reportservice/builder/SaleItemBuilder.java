package com.reportservice.builder;

import com.reportservice.entity.SaleItem;

public class SaleItemBuilder {

	private SaleItemBuilder() {}
	
	public static SaleItem defaults() {
		return SaleItemBuilder.withParams(1L, 10.0, 10L);
	}
	
	public static SaleItem withParams(Long itemId, Double price, Long quantity) {
		final SaleItem item = new SaleItem();
		item.setItemId(itemId);
		item.setPrice(price);
		item.setQuantity(quantity);
		return item;
	}
}
