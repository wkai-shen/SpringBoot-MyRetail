package com.myRetail.persistence.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.Date;

/**
 * This is a model bean with data entity mapping. Values are set up in the constructor and Getters are used for
 * serialization into JSON format during REST calls.
 * 
 * @author WenKai
 *
 */
@Entity
@Table(name = "PRODUCT")
@SequenceGenerator(name = "ProductIdSeqGenerator", sequenceName = "PRODUCT_ID_SEQ", initialValue = 1, allocationSize = 10)
public class Product {
	public enum CategoryEnum {
		BABY, TOYS, FOOD, TOOL
	}

	@Id
	@GenericGenerator(name = "seq_product_id", strategy = "com.myRetail.persistence.model.ProductIdGenerator", parameters = {
			@Parameter(name = "sequence", value = "PRODUCT_ID_SEQ") })
	@GeneratedValue(generator = "seq_product_id")
	@Column(name = "PRODUCT_ID")
	protected long id;

	@Column(name = "SKU", unique = true, nullable = false)
	protected String sku;

	@Column(name = "NAME", nullable = false)
	protected String name;

	@Enumerated(EnumType.STRING)
	@Column(name = "CATEGORY")
	protected CategoryEnum category;

	@Temporal(TemporalType.DATE)
	@Column(name = "LAST_UPDATED")
	protected Date lastUpdated;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	// Don't insert or update this FK(PRODUCT_ID) in this table because its PK is also its FK!
	@JoinColumn(name = "PRODUCT_ID", insertable = false, updatable = false)
	protected ProductDetail productDetail;

	protected Product() {

	}

	public Product(Product product) {
		this.id = product.id;
		this.sku = product.sku;
		this.name = product.name;
		this.category = product.category;
		this.lastUpdated = product.lastUpdated;
		this.productDetail = product.productDetail;
	}

	public Product(String sku, String name, CategoryEnum category) {
		super();
		this.sku = sku;
		this.name = name;
		this.category = category;
		this.lastUpdated = new Date();
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	public int getIdInt() {
		return Math.toIntExact(this.id);
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the sku
	 */
	public String getSku() {
		return sku;
	}

	/**
	 * @param sku
	 *            the sku to set
	 */
	public void setSku(String sku) {
		this.sku = sku;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the category
	 */
	public CategoryEnum getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(CategoryEnum category) {
		this.category = category;
	}

	/**
	 * @return the lastUpdated
	 */
	public Date getLastUpdated() {
		return lastUpdated;
	}

	/**
	 * @param lastUpdated
	 *            the lastUpdated to set
	 */
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	/**
	 * @return the productDetail
	 */
	public ProductDetail getProductDetail() {
		return productDetail;
	}

	/**
	 * @param productDetail
	 *            the productDetail to set
	 */
	public void setProductDetail(ProductDetail productDetail) {
		this.productDetail = productDetail;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Product [id=" + id + ", sku=" + sku + ", name=" + name + ", category=" + category + ", lastUpdated="
				+ lastUpdated + ", productDetail=" + productDetail + "]";
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
		result = prime * result + (int) (id ^ (id >>> 32));
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
		Product other = (Product) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
