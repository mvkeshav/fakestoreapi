package org.keshava.scalerecommerce.services;

import org.keshava.scalerecommerce.models.Category;
import org.keshava.scalerecommerce.models.Product;
import org.keshava.scalerecommerce.models.ProductNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProductService {
    ResponseEntity<Product> getSingleProduct(long id) throws ProductNotFoundException;

    List<Product> getAllProducts();

    List<Product> getLimitedProducts(int limit);

    List<Product> getSortedProducts(String order);

    List<Category> getAllCategories();

    List<Product> getAllProductsInCategory(String categoryId);

    ResponseEntity<Product> addProduct(Product product);

    ResponseEntity<Product> updateProductFully(Product product);

    ResponseEntity<Product> updateProductPartially(Product product);

    ResponseEntity<Product> deleteProduct(long id);
}
