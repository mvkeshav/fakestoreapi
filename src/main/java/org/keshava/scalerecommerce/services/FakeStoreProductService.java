package org.keshava.scalerecommerce.services;

import org.keshava.scalerecommerce.models.Category;
import org.keshava.scalerecommerce.models.Product;
import org.keshava.scalerecommerce.models.ProductNotFoundException;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class FakeStoreProductService implements ProductService {
    final String url = "https://fakestoreapi.com/products/";

    @Override
    public ResponseEntity<Product> getSingleProduct(long id) throws ProductNotFoundException {

        RestTemplate template = new RestTemplate();
        var response = template.getForObject(url + id, Product.class);

        if (response == null) {
            throw new ProductNotFoundException("No such product exists: " + url + id);
        }
        return new ResponseEntity<Product>(response, HttpStatus.OK);
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = getProductResultList(url);
        return products;
    }

    @Override
    public List<Product> getLimitedProducts(int limit) {
        final String urlLimit = "https://fakestoreapi.com/products?limit=" + limit;
        var products = getProductResultList(urlLimit);
        return products;
    }

    @Override
    public List<Product> getSortedProducts(String order) {
        final String sortedProductsUrl = "https://fakestoreapi.com/products?sort=" + order;
        var products = getProductResultList(sortedProductsUrl);
        return products;
    }

    @Override
    public List<Category> getAllCategories() {
        ParameterizedTypeReference<List<Category>> responseType = new ParameterizedTypeReference<List<Category>>() {};
        RestTemplate template = new RestTemplate();
        final String categoryUrl ="https://fakestoreapi.com/products/categories";
        ResponseEntity<List<Category>> response = template.exchange(categoryUrl, HttpMethod.GET, null, responseType);
        var categories = response.getBody();
        return categories;
    }

    @Override
    public List<Product> getAllProductsInCategory(String categoryId) {
        ParameterizedTypeReference<List<Product>> responseType = new ParameterizedTypeReference<List<Product>>() {};
        RestTemplate template = new RestTemplate();
        final String categoryUrl ="https://fakestoreapi.com/products/category";
        ResponseEntity<List<Product>> response = template.exchange(categoryUrl+"/" + categoryId, HttpMethod.GET, null, responseType);
        var products = response.getBody();
        return products;
    }

    @Override
    public ResponseEntity<Product> addProduct(Product product) {
        RestTemplate template = new RestTemplate();
        var response = template.exchange("https://fakestoreapi.com/products", HttpMethod.POST, new HttpEntity<>(product), Product.class);
        return response;
    }

    @Override
    public ResponseEntity<Product> updateProductFully(Product product) {
        RestTemplate template = new RestTemplate();
        var response = template.exchange("https://fakestoreapi.com/products"+"/"+product.getId(),
                HttpMethod.PUT, new HttpEntity<>(product), Product.class);
        return response;
    }

    @Override
    public ResponseEntity<Product> updateProductPartially(Product product) {
        RestTemplate template = new RestTemplate();
        template.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        var response = template.exchange("https://fakestoreapi.com/products/"+product.getId(),
                HttpMethod.PATCH, new HttpEntity<>(product), Product.class);
        return response;

    }

    @Override
    public ResponseEntity<Product> deleteProduct(long id) {
        RestTemplate template = new RestTemplate();
        var response = template.exchange("https://fakestoreapi.com/products/" + id,
                HttpMethod.DELETE, null, Product.class);
        return response;
    }

    private List<Product> getProductResultList(String url) {
        ParameterizedTypeReference<List<Product>> responseType = new ParameterizedTypeReference<List<Product>>() {};
        RestTemplate template = new RestTemplate();
        ResponseEntity<List<Product>> response = template.exchange(url, HttpMethod.GET, null, responseType);
        var products = response.getBody();

        return products;
    }
}
