package com.reportservice.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.google.common.annotations.VisibleForTesting;
import com.reportservice.entity.Sale;
import com.reportservice.entity.SaleItem;
import com.reportservice.entity.Salesman;
import com.reportservice.exception.ParserException;
import com.reportservice.store.SaleStore;
import com.reportservice.store.SalesmanStore;
import com.reportservice.util.ErrorMessage;

public class SaleDataParserStrategy extends DataParserStrategy {

	private static final String ITEM_DELIMITER = "-";

	private SalesmanStore salesmanStore;
	private SaleStore saleStore;

	public SaleDataParserStrategy() {
		salesmanStore = SalesmanStore.getInstance();
		saleStore = SaleStore.getInstance();
	}
	
	@VisibleForTesting
	protected SaleDataParserStrategy(SalesmanStore salesmanStore, SaleStore saleStore) {
		this.salesmanStore = salesmanStore;
		this.saleStore = saleStore;
	}
	
	@Override
	public void parserAndSave(final String toBeParsed) throws ParserException {
		if ( toBeParsed == null || toBeParsed.trim().isEmpty() ) {
			throw new ParserException(ErrorMessage.SALE_EMPTY_LINE);
		} 

		final Scanner scanner = this.createScanner(toBeParsed);

		final Long saleId = scanner.hasNextLong() ? scanner.nextLong() : null;
		final String items = scanner.hasNext() ? scanner.next().trim() : "";
		final String salesmanName = scanner.hasNext() ? scanner.next().trim() : "";
		
		scanner.close();

		if ( saleId == null || items.isEmpty() || salesmanName.isEmpty() ) {
			throw new ParserException(ErrorMessage.SALE_MISSING_INFO);
		}
		
		final Sale sale = new Sale();
		sale.setSaleId(saleId);
		
		final List<SaleItem> saleItems = new ArrayList<>();
		final List<String> itemsSplitted = Arrays.asList(items.replace("[", "").replace("]", "").split(","));
		itemsSplitted.stream().forEach(item -> {
			SaleItem saleItem;
			try {
				saleItem = getSaleItem(item);
				saleItems.add(saleItem);
			} catch (ParserException e) {
			}
		});

		final Salesman salesman = salesmanStore.getByName(salesmanName);
		if ( salesman == null ) {
			throw new ParserException(ErrorMessage.SALESMAN_NOT_FOUND);
		}
		salesman.addSale(sale);

		sale.setItems(saleItems);
		saleStore.addItem(sale);
		
		if ( saleItems.size() < itemsSplitted.size() ) {
			throw new ParserException(ErrorMessage.SALE_MISS_ITEMS);
		}
	}
	
	private SaleItem getSaleItem(String item) throws ParserException {
		final SaleItem saleItem = new SaleItem();
		final Scanner itemsScanner = this.createScanner(item, ITEM_DELIMITER);
		
		final Long itemId = itemsScanner.hasNextLong() ? itemsScanner.nextLong() : null;
		final Long quantity = itemsScanner.hasNextLong() ? itemsScanner.nextLong() : null;
		final Double price = itemsScanner.hasNextDouble() ? itemsScanner.nextDouble() : null;
		
		itemsScanner.close();

		if ( itemId == null || quantity == null || price == null ) {
			throw new ParserException(ErrorMessage.SALE_ITEM_MISSING_INFO);
		} 
		
		saleItem.setItemId(itemId);
		saleItem.setQuantity(quantity);
		saleItem.setPrice(price);				

		return saleItem;
	}

}
