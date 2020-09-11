package com.reportservice.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.reportservice.builder.SaleBuilder;
import com.reportservice.builder.SalesmanBuilder;

public class SalesmanTest {

	@Test
	public void shouldCalculateTotalSold() {
		Salesman salesman = SalesmanBuilder.defaults();
		assertEquals(salesman.getTotalSold(), 0.0);

		salesman.addSale(SaleBuilder.defaults());
		assertEquals(salesman.getTotalSold(), 35.0);
		
		salesman.addSale(SaleBuilder.defaults());
		salesman.addSale(SaleBuilder.defaults());
		assertEquals(salesman.getTotalSold(), 105.0);	
	}	
}
