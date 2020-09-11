package com.reportservice.builder;

import java.util.ArrayList;
import java.util.List;

import com.reportservice.entity.Sale;
import com.reportservice.entity.SaleItem;

public class SaleBuilder {

	private SaleBuilder() {}
	
	public static Sale defaults() {
		final Sale item = new Sale();
		
		final List<SaleItem> saleItems = new ArrayList<>();
		saleItems.add(SaleItemBuilder.withParams(1L, 10.0, 1L));
		saleItems.add(SaleItemBuilder.withParams(2L, 5.0, 5L));
		
		item.setItems(saleItems);
		item.setSaleId(1L);
		return item;
	}
	
	public static Sale withParams(final Long saleId, final List<SaleItem> items) {
		final Sale item = new Sale();		
		item.setItems(items);
		item.setSaleId(saleId);
		return item;
	}
}
