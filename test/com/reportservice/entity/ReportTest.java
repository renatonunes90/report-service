package com.reportservice.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.reportservice.builder.ReportBuilder;
import com.reportservice.builder.SaleBuilder;
import com.reportservice.builder.SalesmanBuilder;
import com.reportservice.util.ReportMessage;

public class ReportTest {

	@Test
	public void shouldMountReport() {
		Report report = ReportBuilder.defaults();
		
		final StringBuilder sb = new StringBuilder();
		sb.append(String.format(ReportMessage.CLIENT_SIZE.getMessage(), report.getClientSize()));
		sb.append(String.format(ReportMessage.SALESMAN_SIZE.getMessage(), report.getSalesmanSize()));
		sb.append(String.format(ReportMessage.MOST_EXPENSIVE_SALE.getMessage(), report.getMostExpensiveSale().getSaleId()));
		sb.append(String.format(ReportMessage.WORST_SALESMAN.getMessage(), report.getWorstSalesman().getName()));
		
		assertEquals(sb.toString(), report.mount());
	}
	
	@Test
	public void shouldMountReportWithMissingItems() {
		Report report = ReportBuilder.defaults();
		report.setSalesmanSize(0L);
		report.setMostExpensiveSale(null);
		report.setWorstSalesman(null);
		
		final StringBuilder sb = new StringBuilder();
		sb.append(String.format(ReportMessage.CLIENT_SIZE.getMessage(), report.getClientSize()));
		sb.append(String.format(ReportMessage.SALESMAN_SIZE.getMessage(), 0));
		sb.append(String.format(ReportMessage.MOST_EXPENSIVE_SALE.getMessage(), ""));
		sb.append(String.format(ReportMessage.WORST_SALESMAN.getMessage(), ""));
		
		assertEquals(sb.toString(), report.mount());
	}
	
	@Test
	public void shouldIsValidReturnsTrue() {
		Report report = new Report();
		report.setClientSize(1L);
		assertTrue(report.isValid());
		
		report = new Report();
		report.setMostExpensiveSale(SaleBuilder.defaults());
		assertTrue(report.isValid());
		
		report = new Report();
		report.setSalesmanSize(1L);
		assertTrue(report.isValid());
		
		report = new Report();
		report.setWorstSalesman(SalesmanBuilder.defaults());
		assertTrue(report.isValid());
	}
	
	@Test
	public void shouldIsValidReturnsFalse() {
		Report report = new Report();
		assertFalse(report.isValid());
	}
}
