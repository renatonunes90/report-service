package com.reportservice.entity;

import java.io.Serializable;

import com.reportservice.util.ReportMessage;

public class Report extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 3616252125766064750L;
	
	private Long clientSize;
	private Long salesmanSize;
	private Sale mostExpensiveSale;
	private Salesman worstSalesman;
	
	public Long getClientSize() {
		return clientSize;
	}

	public void setClientSize(Long clientSize) {
		this.clientSize = clientSize;
	}

	public Long getSalesmanSize() {
		return salesmanSize;
	}

	public void setSalesmanSize(Long salesmanSize) {
		this.salesmanSize = salesmanSize;
	}

	public Sale getMostExpensiveSale() {
		return mostExpensiveSale;
	}

	public void setMostExpensiveSale(Sale mostExpensiveSale) {
		this.mostExpensiveSale = mostExpensiveSale;
	}

	public Salesman getWorstSalesman() {
		return worstSalesman;
	}

	public void setWorstSalesman(Salesman worstSalesman) {
		this.worstSalesman = worstSalesman;
	}

	public String mount() {
		final StringBuilder report = new StringBuilder();
		report.append(String.format(ReportMessage.CLIENT_SIZE.getMessage() , getClientSize()));
		report.append(String.format(ReportMessage.SALESMAN_SIZE.getMessage(), getSalesmanSize()));
		report.append(String.format(ReportMessage.MOST_EXPENSIVE_SALE.getMessage(), getMostExpensiveSale() != null ? getMostExpensiveSale().getSaleId() : "" ));
		report.append(String.format(ReportMessage.WORST_SALESMAN.getMessage(), getWorstSalesman() != null ? getWorstSalesman().getName() : ""));
		return report.toString();
	}
	
	public boolean isValid() {
		return (getClientSize() != null && getClientSize() > 0) || (getSalesmanSize() != null && getSalesmanSize() > 0)
				|| getMostExpensiveSale() != null || getWorstSalesman() != null;
	}
	
	@Override
	public int hashCode() {
		return createHashCode(clientSize, salesmanSize, mostExpensiveSale, worstSalesman);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || !(obj instanceof Report)) {
			return false;
		}
		Report other = (Report) obj;
		boolean result = compareProp(clientSize, other.clientSize);
		result &= compareProp(salesmanSize, other.salesmanSize);
		result &= mostExpensiveSale.equals(other.mostExpensiveSale);
		result &= worstSalesman.equals(other.worstSalesman);
		return result;
	}

	@Override
	public String toString() {
		return stringify(this);
	}
}
