// REST
// /products -> Create, Read
// /products/{id} -> Read, Update, Delete

package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.AuthenticationService;
import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductData;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductController {
    private final ProductService productService;

    private final AuthenticationService authenticationService;

    public ProductController(ProductService productService,
                             AuthenticationService authenticationService) {
        this.productService = productService;
        this.authenticationService = authenticationService;
    }

    @GetMapping
    public List<Product> list() {
        return productService.getProducts();
    }

    @GetMapping("{id}")
    public Product detail(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    // 데이터 변경 -> 누가 하는가? : Authentication
    // 로그인을 해야 데이터 변경이 가능하다 : Authorization
    public Product create(
            @RequestAttribute Long userId,
            @RequestBody @Valid ProductData productData
    ) {
        return productService.createProduct(productData);
    }

    @PatchMapping("{id}")
    // 데이터 변경 -> 누가 하는가? : Authentication
    // 로그인을 해야 데이터 변경이 가능하다 : Authorization
    public Product update(
            @RequestAttribute Long userId,
            @PathVariable Long id,
            @RequestBody @Valid ProductData productData,
            Authentication authentication

    ) {
        System.out.println(authentication);
        return productService.updateProduct(id, productData);
    }

    @DeleteMapping("{id}")
    // 데이터 변경 -> 누가 하는가? : Authentication
    // 로그인을 해야 데이터 변경이 가능하다 : Authorization
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(
            @RequestAttribute Long userId,
            @PathVariable Long id
    ) {
        productService.deleteProduct(id);
    }
}
