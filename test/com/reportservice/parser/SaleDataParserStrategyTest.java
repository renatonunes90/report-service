package com.reportservice.parser;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.reportservice.builder.SaleBuilder;
import com.reportservice.entity.Sale;
import com.reportservice.entity.SaleItem;
import com.reportservice.entity.Salesman;
import com.reportservice.exception.ParserException;
import com.reportservice.store.SaleStore;
import com.reportservice.store.SalesmanStore;
import com.reportservice.util.ErrorMessage;

@ExtendWith(MockitoExtension.class)
public class SaleDataParserStrategyTest {

	private SaleDataParserStrategy component = new SaleDataParserStrategy();
	
	@Mock
	private SaleStore saleStore;
	
	@Mock
	private SalesmanStore salesmanStore;
	
	@Mock
	private Salesman salesman;
	
	@BeforeEach
	public void setup(){
		component = new SaleDataParserStrategy(salesmanStore, saleStore);		
	}
	
	@Test
	public void shouldSaveNewSale() throws ParserException {
		when(salesmanStore.getByName(anyString())).thenReturn(salesman);
		
		final Sale newItem = SaleBuilder.defaults();
		List<String> saleItems = newItem.getItems().stream()
			.map( saleItem -> String.format("%d-%d-%s", saleItem.getItemId(), saleItem.getQuantity(), saleItem.getPrice()))
			.collect(toList());
		
		component.parserAndSave( String.format("%dç[%s]çPedro", newItem.getSaleId(), String.join(",", saleItems)) );
		
    	verify(saleStore).addItem(newItem);
    	verify(salesman).addSale(newItem);
	}
	
	@Test
	public void shouldSaveNewSaleAndWarnMissItems() throws ParserException {
		when(salesmanStore.getByName(anyString())).thenReturn(salesman);
		
		final Sale newItem = SaleBuilder.defaults();
		List<String> saleItems = newItem.getItems().stream()
			.map( saleItem -> String.format("%d-%d", saleItem.getItemId(), saleItem.getQuantity()))
			.collect(toList());
		
		assertException(String.format("%dç[%s]çPedro", newItem.getSaleId(), String.join(",", saleItems)), ErrorMessage.SALE_MISS_ITEMS);
		
		newItem.setItems(new ArrayList<SaleItem>());
    	verify(saleStore).addItem(newItem);
    	verify(salesman).addSale(newItem);
	}
	
	@Test
	public void shouldThrowExceptionMissInformation() throws ParserException {
		assertException("ççç", ErrorMessage.SALE_MISSING_INFO);
		assertException("çnameç", ErrorMessage.SALE_MISSING_INFO);
		assertException("   ç   ç     ", ErrorMessage.SALE_MISSING_INFO);
		assertException("stringIdç[]çsalesmanName", ErrorMessage.SALE_MISSING_INFO);
	}
	
	@Test
	public void shouldThrowExceptionEmptyString() throws ParserException {		
		assertException(null, ErrorMessage.SALE_EMPTY_LINE);
		assertException("", ErrorMessage.SALE_EMPTY_LINE);
		assertException("    ", ErrorMessage.SALE_EMPTY_LINE);
	}
	
	@Test
	public void shouldThrowExceptionSalesmanNotFound() throws ParserException {
		when(salesmanStore.getByName(anyString())).thenReturn(null);
		
		assertException("1ç[]çPedro", ErrorMessage.SALESMAN_NOT_FOUND);
	}
	
	private void assertException(final String toBeParsed, final ErrorMessage expected) {
		final Exception exception = assertThrows(ParserException.class, () ->
			component.parserAndSave(toBeParsed));
		assertEquals(expected.getMessage(), exception.getMessage());
	}
	
}
