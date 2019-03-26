package com.myRetail.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This is a model bean with data entity mapping. Values are set up in the constructor and Getters are used for
 * serialization into JSON format during REST calls.
 * 
 * @author WenKai
 *
 */
@Entity
@Table(name = "PRODUCT_DETAIL")
public class ProductDetail {
	@Id
	@Column(name = "PRODUCT_ID")
	private long productId;

	@Column(name = "PRICE")
	private double price;

	protected ProductDetail() {

	}

	public ProductDetail(double price) {
		super();
		this.price = price;
	}

	/**
	 * @param productId
	 *            the productId to set
	 */
	public void setProductId(long productId) {
		this.productId = productId;
	}

	/**
	 * @return the productId
	 */
	public long getProductId() {
		return productId;
	}

	public int getProductIdInt() {
		return Math.toIntExact(this.productId);
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ProductDetail [productId=" + productId + ", price=" + price + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (productId ^ (productId >>> 32));
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductDetail other = (ProductDetail) obj;
		if (productId != other.productId)
			return false;
		return true;
	}

}
