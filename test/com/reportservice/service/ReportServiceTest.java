package com.reportservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.reportservice.builder.ClientBuilder;
import com.reportservice.builder.SaleBuilder;
import com.reportservice.builder.SaleItemBuilder;
import com.reportservice.builder.SalesmanBuilder;
import com.reportservice.entity.Client;
import com.reportservice.entity.Report;
import com.reportservice.entity.Sale;
import com.reportservice.entity.SaleItem;
import com.reportservice.entity.Salesman;
import com.reportservice.store.ClientStore;
import com.reportservice.store.SaleStore;
import com.reportservice.store.SalesmanStore;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {

	private ReportService component;
	
	@Mock
	private ClientStore clientStore;
	
	@Mock
	private SalesmanStore salesmanStore;
	
	@Mock
	private SaleStore saleStore;
	
	@BeforeEach
	public void setup(){
		component = new ReportService(clientStore, salesmanStore, saleStore);
	}
	
	@Test
	public void shouldGenerateEmptyReport() {
		when(clientStore.getLoadedItems()).thenReturn(new HashMap<String,Client>());
		when(salesmanStore.getLoadedItems()).thenReturn(new HashMap<String,Salesman>());
		when(saleStore.getLoadedItems()).thenReturn(new HashMap<Long,Sale>());
		
		final Report report = component.generateReport();
		
		assertEquals(report.getClientSize(), new Long(0));
		assertNull(report.getMostExpensiveSale());
		assertEquals(report.getSalesmanSize(), new Long(0));
		assertNull(report.getWorstSalesman());
		assertFalse(report.isValid());
	}
	
	@Test
	public void shouldGenerateSimpleReport() {
		
		final HashMap<String, Client> clients = createClientData(5);
		final HashMap<String, Salesman> salesmen = createSalesmanData(5);
		final HashMap<Long, Sale> sales = new HashMap<Long, Sale>();
		
		List<SaleItem> saleItems = new ArrayList<>();
		saleItems.add(SaleItemBuilder.withParams(1L, 10.0, 1L));
		saleItems.add(SaleItemBuilder.withParams(2L, 5.0, 5L));
		Sale sale = SaleBuilder.withParams(1L, saleItems);
		salesmen.get("0000").addSale(sale);
		sales.put(sale.getSaleId(), sale);
		
		saleItems = new ArrayList<>();
		saleItems.add(SaleItemBuilder.withParams(1L, 10.0, 1L));
		sale = SaleBuilder.withParams(2L, saleItems);
		salesmen.get("0001").addSale(sale);
		sales.put(sale.getSaleId(), sale);
		
		saleItems = new ArrayList<>();
		saleItems.add(SaleItemBuilder.withParams(1L, 5.0, 1L));
		sale = SaleBuilder.withParams(3L, saleItems);
		salesmen.get("0002").addSale(sale);
		sales.put(sale.getSaleId(), sale);
		
		saleItems = new ArrayList<>();
		saleItems.add(SaleItemBuilder.withParams(1L, 2.0, 1L));
		sale = SaleBuilder.withParams(4L, saleItems);
		salesmen.get("0003").addSale(sale);
		sales.put(sale.getSaleId(), sale);
		
		saleItems = new ArrayList<>();
		saleItems.add(SaleItemBuilder.withParams(1L, 1.0, 1L));
		sale = SaleBuilder.withParams(5L, saleItems);
		salesmen.get("0004").addSale(sale);
		sales.put(sale.getSaleId(), sale);
		
		when(clientStore.getLoadedItems()).thenReturn(clients);
		when(salesmanStore.getLoadedItems()).thenReturn(salesmen);
		when(saleStore.getLoadedItems()).thenReturn(sales);
		
		final Report report = component.generateReport();
		
		assertEquals(report.getClientSize(), new Long(5));
		assertEquals(report.getMostExpensiveSale().getSaleId(), new Long(1));
		assertEquals(report.getSalesmanSize(), new Long(5));
		assertEquals(report.getWorstSalesman().getName(), "Salesman 4");
		assertTrue(report.isValid());
	}
	
	@Test
	public void shouldGenerateSimpleReport2() {
		
		final HashMap<String, Client> clients = createClientData(5);
		final HashMap<String, Salesman> salesmen = createSalesmanData(2);
		final HashMap<Long, Sale> sales = new HashMap<Long, Sale>();
		
		List<SaleItem> saleItems = new ArrayList<>();
		saleItems.add(SaleItemBuilder.withParams(1L, 10.0, 1L));
		saleItems.add(SaleItemBuilder.withParams(2L, 5.0, 5L));
		Sale sale = SaleBuilder.withParams(1L, saleItems);
		salesmen.get("0000").addSale(sale);
		sales.put(sale.getSaleId(), sale);
		
		saleItems = new ArrayList<>();
		saleItems.add(SaleItemBuilder.withParams(1L, 10.0, 1L));
		sale = SaleBuilder.withParams(2L, saleItems);
		salesmen.get("0000").addSale(sale);
		sales.put(sale.getSaleId(), sale);
		
		saleItems = new ArrayList<>();
		saleItems.add(SaleItemBuilder.withParams(1L, 40.0, 1L));
		sale = SaleBuilder.withParams(3L, saleItems);
		salesmen.get("0001").addSale(sale);
		sales.put(sale.getSaleId(), sale);
		
		when(clientStore.getLoadedItems()).thenReturn(clients);
		when(salesmanStore.getLoadedItems()).thenReturn(salesmen);
		when(saleStore.getLoadedItems()).thenReturn(sales);
		
		final Report report = component.generateReport();
		
		assertEquals(report.getClientSize(), new Long(5));
		assertEquals(report.getMostExpensiveSale().getSaleId(), new Long(3));
		assertEquals(report.getSalesmanSize(), new Long(2));
		assertEquals(report.getWorstSalesman().getName(), "Salesman 1");
		assertTrue(report.isValid());
	}
	
	@Test
	public void shouldGenerateReportWithoutClients() {
		
		final HashMap<String, Client> clients = createClientData(0);
		final HashMap<String, Salesman> salesmen = createSalesmanData(2);
		final HashMap<Long, Sale> sales = new HashMap<Long, Sale>();
		
		List<SaleItem> saleItems = new ArrayList<>();
		saleItems.add(SaleItemBuilder.withParams(1L, 10.0, 1L));
		saleItems.add(SaleItemBuilder.withParams(2L, 5.0, 5L));
		Sale sale = SaleBuilder.withParams(1L, saleItems);
		salesmen.get("0000").addSale(sale);
		sales.put(sale.getSaleId(), sale);
		
		when(clientStore.getLoadedItems()).thenReturn(clients);
		when(salesmanStore.getLoadedItems()).thenReturn(salesmen);
		when(saleStore.getLoadedItems()).thenReturn(sales);
		
		final Report report = component.generateReport();
		
		assertEquals(report.getClientSize(), new Long(0));
		assertEquals(report.getMostExpensiveSale().getSaleId(), new Long(1));
		assertEquals(report.getSalesmanSize(), new Long(2));
		assertEquals(report.getWorstSalesman().getName(), "Salesman 1");
		assertTrue(report.isValid());
	}
	
	private HashMap<String,Client> createClientData(final int sizeOfMap) {
		final HashMap<String,Client> clientData = new HashMap<String,Client>();
		
		if ( sizeOfMap > 0 ) {
			for ( int i = 0; i < sizeOfMap; i++ ) {
				Client client = ClientBuilder.withCNPJ("000" + i);
				clientData.put(client.getCnpj(), client);
			}
		}
		
		return clientData;
	}
	
	private HashMap<String,Salesman> createSalesmanData(final int sizeOfMap) {
		final HashMap<String,Salesman> salesmanData = new HashMap<String,Salesman>();
		
		if ( sizeOfMap > 0 ) {
			for ( int i = 0; i < sizeOfMap; i++ ) {
				Salesman salesman = SalesmanBuilder.withParams("000" + i, "Salesman " + i);
				salesmanData.put(salesman.getCpf(), salesman);
			}
		}
		
		return salesmanData;
	}
	
}
