package com.reportservice.service;

import java.util.Comparator;
import java.util.Map;

import com.google.common.annotations.VisibleForTesting;
import com.reportservice.entity.Client;
import com.reportservice.entity.Report;
import com.reportservice.entity.Sale;
import com.reportservice.entity.Salesman;
import com.reportservice.store.ClientStore;
import com.reportservice.store.SaleStore;
import com.reportservice.store.SalesmanStore;

public class ReportService {

	private ClientStore clientStore;
	private SalesmanStore salesmanStore;
	private SaleStore saleStore;

	public ReportService() {
		clientStore = ClientStore.getInstance();
		salesmanStore = SalesmanStore.getInstance();
		saleStore = SaleStore.getInstance();
	}
	
	@VisibleForTesting
	protected ReportService(ClientStore clientStore, SalesmanStore salesmanStore, SaleStore saleStore) {
		this.clientStore = clientStore;
		this.salesmanStore = salesmanStore;
		this.saleStore = saleStore;
	}
	
	public Report generateReport() {
		final Map<Long, Sale> loadedSales = saleStore.getLoadedItems();
		final Map<String, Salesman> loadedSalesman = salesmanStore.getLoadedItems();
		final Map<String, Client> loadedClients = clientStore.getLoadedItems();

		final Report report = new Report();
		report.setClientSize(Long.valueOf(getClientsSize(loadedClients)));
		report.setSalesmanSize(Long.valueOf(getSalesmanSize(loadedSalesman)));
		report.setMostExpensiveSale(getMostExpensiveSale(loadedSales));
		report.setWorstSalesman(getWorstSalesman(loadedSalesman));

		return report;
	}
	
	private int getClientsSize(Map<String, Client> loadedClients) {
		return loadedClients.size();
	}
	
	private int getSalesmanSize(Map<String, Salesman> loadedSalesman) {
		return loadedSalesman.size();
	}
	
	private Sale getMostExpensiveSale(Map<Long, Sale> loadedSales) {		
		return loadedSales.size() > 0 ? loadedSales.values().stream().max(Comparator.comparing(Sale::getTotalValue)).get() : null;		
	}
	
	private Salesman getWorstSalesman(Map<String, Salesman> loadedSalesman) {
		return loadedSalesman.size() > 0 ? loadedSalesman.values().stream().min(Comparator.comparing(Salesman::getTotalSold)).get() : null;
	}
}
