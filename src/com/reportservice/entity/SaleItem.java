package com.reportservice.entity;

import java.io.Serializable;

public class SaleItem extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -504418193739858560L;

	private Long itemId;
	private Long quantity;
	private Double price;
	
	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public int hashCode() {
		return createHashCode(itemId, quantity, price);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || !(obj instanceof SaleItem)) {
			return false;
		}
		SaleItem other = (SaleItem) obj;
		boolean result = compareProp(itemId, other.itemId);
		result &= compareProp(quantity, other.quantity);
		result &= compareProp(price, other.price);
		return result;
	}

	@Override
	public String toString() {
		return stringify(this);
	}
}
