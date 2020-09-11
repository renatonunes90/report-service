package com.reportservice.entity;

import java.io.Serializable;
import java.util.List;

public class Sale extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 5045934039189641443L;

	private Long saleId;
	private List<SaleItem> items;
	
	public Long getSaleId() {
		return saleId;
	}
	
	public void setSaleId(Long saleId) {
		this.saleId = saleId;
	}
	
	public List<SaleItem> getItems() {
		return items;
	}
	
	public void setItems(List<SaleItem> items) {
		this.items = items;
	}
	
	public double getTotalValue() {
		return items.stream().map( item -> item.getPrice() * item.getQuantity() ).reduce(0.0, Double::sum);	
	}
	
	@Override
	public int hashCode() {
		return createHashCode(saleId, items);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || !(obj instanceof Sale)) {
			return false;
		}
		Sale other = (Sale) obj;
		boolean result = compareProp(saleId, other.saleId);
		result &= items.equals(other.items);
		return result;
	}

	@Override
	public String toString() {
		return stringify(this);
	}
}
