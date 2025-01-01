package com.ecommerce.productservicedecmwfeve.controllers;

import com.ecommerce.productservicedecmwfeve.commons.AuthenticationCommons;
import com.ecommerce.productservicedecmwfeve.models.Product;
import com.ecommerce.productservicedecmwfeve.services.ProductService;
import com.ecommerce.productservicedecmwfeve.exceptions.ProductNotExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;
    private RestTemplate restTemplate;
    private AuthenticationCommons authenticationCommons;

    @Autowired
    public ProductController(@Qualifier("selfProductService") ProductService productService,
                             RestTemplate restTemplate,
                             AuthenticationCommons authenticationCommons) {
        this.productService = productService;
        this.restTemplate = restTemplate;
        this.authenticationCommons = authenticationCommons;
    }

    @GetMapping() // localhost:8080/products
    public ResponseEntity<Page<Product>> getAllProducts(@RequestParam("pageNumber") int pageNumber,
                                                        @RequestParam("pageSize") int pageSize) {
////        restTemplate.delete(null);
//
//        UserDto userDto = authenticationCommons.validateToken(token);
//
//        if (userDto == null) {
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        }
//
//        boolean isAdmin = false;
//
//        for (Role role: userDto.getRoles()) {
//            if (role.getName().equals("ADMIN")) {
//                isAdmin = true;
//                break;
//            }
//        }
//
//        if (!isAdmin) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        Page<Product> products = productService.getAllProducts(pageNumber, pageSize); // o p q

        List<Product> finalProducts = new ArrayList<>();

        for (Product p: products) { // o  p q
            p.setTitle("Hello" + p.getTitle());
            finalProducts.add(p);
        }

        ResponseEntity<Page<Product>> response = new ResponseEntity<>(
                products, HttpStatus.FORBIDDEN
        );
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getSingleProduct(@PathVariable("id") Long id) throws ProductNotExistsException {
//        throw new RuntimeException("SOmething went wrong");
//        try {
            return new ResponseEntity<>(
                    productService.getSingleProduct(id),
                    HttpStatus.OK
            );
//        } catch (ArithmeticException exception) {
//            ResponseEntity<Product> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            return response;
//        } catch (ArrayIndexOutOfBoundsException exception) {
//
//        }

    }

    @PostMapping()
    public Product addNewProduct(@RequestBody Product product) {

        return productService.addNewProduct(product);
    }

    @PatchMapping("/{id}")
    public Product updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        return new Product();
    }

    @PutMapping("/{id}")
    public Product replaceProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        return new Product();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @ExceptionHandler(ProductNotExistsException.class)
//    public ResponseEntity<Void> handleProductNotExistException() {
//        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//    }
}
