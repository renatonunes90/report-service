package com.reportservice.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.reportservice.builder.SaleBuilder;
import com.reportservice.builder.SaleItemBuilder;

public class SaleTest {

	@Test
	public void shouldCalculateTotalValue() {
		Sale sale = SaleBuilder.defaults();
		assertEquals(sale.getTotalValue(), 35.0);
		
		sale.setItems(Arrays.asList(SaleItemBuilder.withParams(1L, 25.0, 3L)));
		assertEquals(sale.getTotalValue(), 75.0);
		
		sale.setItems(Arrays.asList());
		assertEquals(sale.getTotalValue(), 0.0);
	}	
}
