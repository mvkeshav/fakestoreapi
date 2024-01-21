package org.keshava.scalerecommerce.controllers;
import java.util.List;

import org.keshava.scalerecommerce.models.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.keshava.scalerecommerce.models.Product;
import org.keshava.scalerecommerce.models.Category;
import org.keshava.scalerecommerce.services.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getSingleProduct(@PathVariable("id") long id) {
        try {
            var response = productService.getSingleProduct(id);
            return response;
        }
        catch (ProductNotFoundException e) {
            var response = new ResponseEntity<Product>((Product)null, HttpStatus.NOT_FOUND);
            return response;
        }
    }

    @GetMapping(value = {"/", ""})
    public List<Product> getAllProducts() {
        var products = productService.getAllProducts();
        return products;
    }

    @GetMapping(params={"limit"})
    public List<Product> getLimitedProducts(@RequestParam("limit") Integer limit) {
        var products = productService.getLimitedProducts(limit);
        return products;
    }

    @GetMapping(params={"sort"})
    public List<Product> getSortedProducts(@RequestParam("sort") String order) {
        var products = productService.getSortedProducts(order);
        return products;
    }

    @GetMapping("/categories")
    public List<Category> getAllCategories() {
        var categories = productService.getAllCategories();
        return categories;
    }

    @GetMapping("/category/{categoryId}")
    public List<Product> getAllProductsInCategory(@PathVariable("categoryId") String categoryId) {
        var products = productService.getAllProductsInCategory(categoryId);
        return products;
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        var response = productService.addProduct(product);
        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProductFully(@PathVariable long id, @RequestBody Product product) {
        var response = productService.updateProductFully(product);
        return response;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> updateProductPartially(@PathVariable long id, @RequestBody Product product) {
        var response = productService.updateProductPartially(product);

        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable long id) {
        ResponseEntity<Product> response = productService.deleteProduct(id);
        ResponseEntity<Product> newResponse = new ResponseEntity<>(response.getBody(), HttpStatus.ACCEPTED);
        return response;
    }


/*    @DeleteMapping("/{id}")
    public Product deleteProduct(@PathVariable long id) {
        ResponseEntity<Product> response = productService.deleteProduct(id);
        return response.getBody();
    }
*/
}
