package org.example.onlinestore.controller;

import org.example.onlinestore.basket.ProductBasketImpl;
import org.example.onlinestore.searchable.product.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class ProductBasketController {

    private final ProductBasketImpl productBasket;

    public ProductBasketController(ProductBasketImpl productBasket) {
        this.productBasket = productBasket;
    }

    @PostMapping
    public ResponseEntity<Product> addProductBasket(@RequestBody Product product) throws RuntimeException{
     productBasket.addProductBasket(product);
    return ResponseEntity.ok().build();
    }

    @GetMapping("/salary")
    public int getSalaryProductBasket(){
        return productBasket.getSalaryProductBasket();
    }

    @GetMapping("/sum_Salary")
    public String getProductBasket(){
        return productBasket.getSumProductBasket();
    }

    @GetMapping("/checked")
    public boolean checkedProductBasket(@RequestParam String productName) {
        return productBasket.checkedProductBasket(productName);
    }

    @DeleteMapping
    public void removeAllProductBasket(){
        productBasket.removeAllProductBasket();
    }
}
