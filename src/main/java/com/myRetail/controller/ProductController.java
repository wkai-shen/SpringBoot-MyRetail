package com.myRetail.controller;

import com.google.common.collect.Lists;
import com.myRetail.persistence.dao.ProductRepository;
import com.myRetail.persistence.model.Product;
import com.myRetail.persistence.model.Product.CategoryEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Provide different services based on different requests.
 * 
 * Spring MVC uses the <b>HttpMessageConverter</b> interface to convert HTTP requests and responses. Sensible defaults
 * are included out of the box, for example Objects can be automatically converted to JSON (using the Jackson library)
 * or XML (using the Jackson XML extension if available, else using JAXB). Strings are encoded using UTF-8 by default.
 * 
 * The @ResponseBody annotation [...] can be put on a method and indicates that the return type should be written
 * straight to the HTTP response body for RESTful web services (and not placed in a Model, or interpreted as a view name)
 * 
 * @author WenKai
 *
 */
@RestController // This annotation is equal to @Controller + @ResponseBody
@RequestMapping("/products/v1")
public class ProductController {
	private final ProductRepository repository;
	private final String servicePort;

	@Autowired
	public ProductController(ProductRepository repository, @Value("${server.port}") String servicePort) {
		this.repository = repository;
		this.servicePort = servicePort;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Iterable<Product> findAll() {
		Iterable<Product> products = repository.findAll();
		return products;
	}

	@RequestMapping(method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Iterable<Product> findByCategories(
			@RequestParam(value = "category", required = true) Set<CategoryEnum> categories) {
		List<Product> products = repository.findByCategoryIn(categories);
		return products;
	}

	@GetMapping(value = "/{productIds}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Iterable<Product> findByIdsAndCategories(@PathVariable("productIds") List<Long> productIds,
			@RequestParam(value = "category", required = false) Set<CategoryEnum> categories) {
		Iterable<Product> products = repository.findAllById(productIds);

		if (categories == null)
			return products;
		else {
            List<Product> productList = StreamSupport.stream(products.spliterator(), false)
                    .filter(product -> categories.contains(product.getCategory()))
                    .collect(Collectors.toList());

			return productList;
		}
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView getAll() {
		Iterable<Product> products = repository.findAll();

		ModelAndView modelAndView = new ModelAndView("productSearch");
		modelAndView.addObject("products", products);
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getByCstegories(
			@RequestParam(value = "category", required = true) Set<CategoryEnum> categories) {
		List<Product> products = repository.findByCategoryIn(categories);

		ModelAndView modelAndView = new ModelAndView("productSearch");
		modelAndView.addObject("products", products);
		return modelAndView;
	}

	@RequestMapping(value = "/{productIds}", method = RequestMethod.GET)
	public ModelAndView getByIdsAndCategories(@PathVariable("productIds") List<Long> productIds,
			@RequestParam(value = "category", required = false) Set<CategoryEnum> categories) {
		Iterable<Product> products = this.findByIdsAndCategories(productIds, categories);

		ModelAndView modelAndView = new ModelAndView("productSearch");
		modelAndView.addObject("products", products);
		return modelAndView;
	}

	@RequestMapping(value = "/demo", method = RequestMethod.GET)
	public ModelAndView getDemo() {
		List<Pair<String, String>> urlList = Lists.newArrayListWithCapacity(10);
		urlList.add(Pair.of("Find all products", "http://localhost:" + servicePort + "/products/v1/"));
		urlList.add(Pair.of("Find the products by one category", "http://localhost:" + servicePort + "/products/v1?category=FOOD"));
		urlList.add(Pair.of("Find the products by multiple categories", "http://localhost:" + servicePort + "/products/v1?category=FOOD&category=TOOL"));
		urlList.add(Pair.of("Find one product by its ID", "http://localhost:" + servicePort + "/products/v1/22"));
		urlList.add(Pair.of("Find the products by multiple IDs", "http://localhost:" + servicePort + "/products/v1/30,22,1,15,33,10,38"));
		urlList.add(Pair.of("Find the products by multipe IDs and categories", "http://localhost:" + servicePort + "/products/v1/30,22,1,15,33,10,38?category=BABY&category=TOOL"));

		ModelAndView modelAndView = new ModelAndView("productDemo");
		modelAndView.addObject("urlList", urlList);
		return modelAndView;
	}
}
