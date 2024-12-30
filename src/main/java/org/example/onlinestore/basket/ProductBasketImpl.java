package org.example.onlinestore.basket;

import org.example.onlinestore.product.Product;

public interface ProductBasketImpl {

    Product addProductBasket(Product productAdd) throws Exception;

    int getSalaryProductBasket();

    String getSumProductBasket();

    boolean checkedProductBasket(String productName);

    void removeAllProductBasket();
}
