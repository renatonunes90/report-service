package com.reportservice.entity;

import java.io.Serializable;

public class Client extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -5446050475474288685L;

	private String cnpj;
	private String name;
	private String businessArea;

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getBusinessArea() {
		return businessArea;
	}
	
	public void setBusinessArea(String businessArea) {
		this.businessArea = businessArea;
	}
	
	@Override
	public int hashCode() {
		return createHashCode(cnpj, name, businessArea);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || !(obj instanceof Client)) {
			return false;
		}
		Client other = (Client) obj;
		boolean result = compareProp(cnpj, other.cnpj);
		result &= compareProp(name, other.name);
		result &= compareProp(businessArea, other.businessArea);
		return result;
	}

	@Override
	public String toString() {
		return stringify(this);
	}
}
