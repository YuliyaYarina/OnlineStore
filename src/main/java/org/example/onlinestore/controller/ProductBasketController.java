package org.example.onlinestore.controller;

import org.example.onlinestore.basket.ProductBasketImpl;
import org.example.onlinestore.product.DiscountedProduct;
import org.example.onlinestore.product.FixPriceProduct;
import org.example.onlinestore.product.Product;
import org.example.onlinestore.product.SimpleProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.example.onlinestore.product.FixPriceProduct.FIX_PRICE;

@RestController
@RequestMapping
public class ProductBasketController {

    @Autowired
    private ProductBasketImpl productBasket;

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
