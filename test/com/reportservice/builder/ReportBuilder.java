package com.reportservice.builder;

import com.reportservice.entity.Report;

public class ReportBuilder {

	private ReportBuilder() {}
	
	public static Report defaults() {
		final Report item = new Report();
		item.setClientSize(1L);
		item.setMostExpensiveSale(SaleBuilder.defaults());
		item.setSalesmanSize(1L);
		item.setWorstSalesman(SalesmanBuilder.defaults());
		return item;
	}
}
