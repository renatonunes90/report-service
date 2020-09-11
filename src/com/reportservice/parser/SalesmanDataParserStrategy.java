package com.reportservice.parser;

import java.util.Scanner;

import com.google.common.annotations.VisibleForTesting;
import com.reportservice.entity.Salesman;
import com.reportservice.exception.ParserException;
import com.reportservice.store.SalesmanStore;
import com.reportservice.util.ErrorMessage;

public class SalesmanDataParserStrategy extends DataParserStrategy {

	private SalesmanStore salesmanStore;

	public SalesmanDataParserStrategy() {
		salesmanStore = SalesmanStore.getInstance();
	}
	
	@VisibleForTesting
	protected SalesmanDataParserStrategy(SalesmanStore salesmanStore) {
		this.salesmanStore = salesmanStore;
	}
	
	@Override
	public void parserAndSave(final String toBeParsed) throws ParserException {
		if ( toBeParsed == null || toBeParsed.trim().isEmpty() ) {
			throw new ParserException(ErrorMessage.SALESMAN_EMPTY_LINE);
		} 
		
		final Scanner scanner = this.createScanner(toBeParsed);

		final String cpf = scanner.hasNext() ? scanner.next() : "";
		final String name = scanner.hasNext() ? scanner.next() : "";
		final Double salary = scanner.hasNextDouble() ? scanner.nextDouble() : null;
		
		scanner.close();

		if ( cpf.isEmpty() || name.isEmpty() || salary == null ) {
			throw new ParserException(ErrorMessage.SALESMAN_MISSING_INFO);
		} 

		final Salesman salesman = new Salesman();
		salesman.setCpf(cpf);
		salesman.setName(name);
		salesman.setSalary(salary);
		salesmanStore.addItem(salesman);
	}

}
