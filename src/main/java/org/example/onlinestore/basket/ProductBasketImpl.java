package org.example.onlinestore.basket;

import org.example.onlinestore.searchable.product.Product;

import java.util.List;

public interface ProductBasketImpl {

    void addProductBasket(Product productAdd) throws RuntimeException;

    int getSalaryProductBasket();

    String getSumProductBasket();

    boolean checkedProductBasket(String productName);

    void removeAllProductBasket();

    List<Product> removeProductBasket(String productName);
}
