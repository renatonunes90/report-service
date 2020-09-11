package com.reportservice.store;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.reportservice.builder.SalesmanBuilder;
import com.reportservice.entity.Salesman;
import com.reportservice.exception.ParserException;


public class SalesmanStoreTest {

	private SalesmanStore store;
	
	@BeforeEach
	public void setup(){
		store = SalesmanStore.getInstance();
		store.clear();
		store.addItem(SalesmanBuilder.defaults());
	}
	
	@Test
	public void shouldGetSalesmanByName() throws ParserException {
		final Salesman salesman = SalesmanBuilder.defaults();
		assertEquals(store.getByName(salesman.getName()), salesman);
	}
	
	@Test
	public void shouldNotFounSalesman() throws ParserException {
		assertNull(store.getByName("name"));
	}

}
