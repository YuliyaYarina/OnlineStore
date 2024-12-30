package org.example.onlinestore.controller;

import org.example.onlinestore.basket.ProductBasketImpl;
import org.example.onlinestore.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class ProductBasketController {

    @Autowired
    private ProductBasketImpl productBasket;

    @PostMapping
    public ResponseEntity<?> addProductBasket(@RequestBody Product product) throws Exception {
        return ResponseEntity.ok(productBasket.addProductBasket(product));
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
