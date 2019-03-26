package com.myRetail.persistence.dao;

import com.myRetail.persistence.model.Product;
import com.myRetail.persistence.model.Product.CategoryEnum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Product DAO contains the customized queries for data access.
 * 
 * @author WenKai
 *
 */
@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
	/**
	 * Find a list of products by categories.
	 * 
	 * @param categories
	 * @return
	 */
	List<Product> findByCategoryIn(Collection<CategoryEnum> categories);
}
