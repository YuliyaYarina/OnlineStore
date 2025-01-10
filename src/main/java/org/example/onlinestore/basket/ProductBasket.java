package org.example.onlinestore.basket;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.onlinestore.searchable.product.Product;
import org.springframework.stereotype.Service;

@Data
@Getter
@Setter
@Service
public class ProductBasket implements ProductBasketImpl{

    private Product[] products;
    private int maxSize = 5;
    private int currentCount;
    private byte specialProducts;

    public ProductBasket() {
        this.products = new Product[maxSize];
        this.currentCount = 0;
        this.specialProducts = 0;
    }

    @Override
    public void addProductBasket(Product product){
        if (product == null) throw new NullPointerException("Product is null");

        if (currentCount < maxSize){
            products[currentCount++] = product;
            if (product.getIsSpecial()){
                specialProducts++;
            }
        }else {
            throw new IndexOutOfBoundsException("Невозможно добавить продукт");
        }
    }

    @Override
    public int getSalaryProductBasket() {
        int sum = 0;
        for (Product value : products) {
            if (value != null) {
                sum += value.getPrice();
            }else
                break;
        }
        return sum;
    }


    @Override
    public String getSumProductBasket() {
       String su = "";
        if (getSalaryProductBasket() != 0) {
            for (Product value : products) {
                if (value != null) su += value ;
            }
            return su + "Итого: " + '<' + getSalaryProductBasket() + '>' + "\n" +
                     "Специальных товаров: " + '<' + specialProducts + '>';
        }else {
            return "в корзине пусто";
        }
    }

    @Override
    public boolean checkedProductBasket(String productName) {
        for (Product value : products) {
            if (value == null) continue;
            if (value.getName().equals(productName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void removeAllProductBasket() {
        for (int i = 0; i < products.length; i++) {
            products[i] = null;
        }
        products = new Product[]{};
    }

}
