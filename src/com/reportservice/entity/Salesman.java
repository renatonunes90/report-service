package com.reportservice.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Salesman extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -3894583886251702343L;

	private String cpf;
	private String name;
	private Double salary;
	private List<Sale> sales;

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public void addSale(Sale sale) {
		if ( sales == null ) {
			sales = new ArrayList<>();
		}
		sales.add(sale);
	}

	public double getTotalSold() {
		return sales != null ? sales.stream().map( sale -> sale.getTotalValue() ).reduce(0.0, Double::sum) : 0.0;
	}

	@Override
	public int hashCode() {
		return createHashCode(cpf, name, salary);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || !(obj instanceof Salesman)) {
			return false;
		}
		Salesman other = (Salesman) obj;
		boolean result = compareProp(cpf, other.cpf);
		result &= compareProp(name, other.name);
		result &= compareProp(salary, other.salary);
		return result;
	}

	@Override
	public String toString() {
		return stringify(this);
	}


}
