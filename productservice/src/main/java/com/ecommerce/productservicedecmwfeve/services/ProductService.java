package com.ecommerce.productservicedecmwfeve.services;

import com.ecommerce.productservicedecmwfeve.models.Product;
import com.ecommerce.productservicedecmwfeve.exceptions.ProductNotExistsException;
import org.springframework.data.domain.Page;

public interface ProductService {

    Product getSingleProduct(Long id) throws ProductNotExistsException;

    Page<Product> getAllProducts(int pageSize, int pageNumber);

    Product updateProduct(Long id, Product product);

    Product replaceProduct(Long id, Product product);

    Product addNewProduct(Product product);

    boolean deleteProduct(Long id);

}
