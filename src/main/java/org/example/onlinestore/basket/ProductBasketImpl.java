package org.example.onlinestore.basket;

import org.example.onlinestore.product.Product;

public interface ProductBasketImpl {

    void addProductBasket(Product productAdd) throws RuntimeException;

    int getSalaryProductBasket();

    String getSumProductBasket();

    boolean checkedProductBasket(String productName);

    void removeAllProductBasket();
}
