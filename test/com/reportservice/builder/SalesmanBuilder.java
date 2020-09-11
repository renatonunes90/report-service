package com.reportservice.builder;

import com.reportservice.entity.Salesman;

public class SalesmanBuilder {

	private SalesmanBuilder() {}
	
	public static Salesman defaults() {
		final Salesman item = new Salesman();
		item.setCpf("0000");
		item.setName("salesmanName");
		item.setSalary(100.0);
		return item;
	}
	
	public static Salesman withParams(final String cpf, final String name) {
		final Salesman item = new Salesman();
		item.setCpf(cpf);
		item.setName(name);
		item.setSalary(100.0);
		return item;
	}
}
