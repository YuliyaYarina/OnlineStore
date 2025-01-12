package org.example.onlinestore.basket;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.onlinestore.searchable.product.Product;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Data
@Getter
@Setter
@Service
public class ProductBasket implements ProductBasketImpl{

    private List<Product> products;
//    private int currentCount;
    private byte specialProducts;

    public ProductBasket() {
        this.products = new LinkedList<>();
//        this.currentCount = 0;
        this.specialProducts = 0;
    }

    @Override
    public void addProductBasket(Product product){
        if (product == null) throw new NullPointerException("Product is null");
        boolean add = products.add(product);
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
       products.clear();
    }

    @Override
    public List<Product> removeProductBasket(String productName) {
        List<Product> result = new LinkedList<>();
        Iterator<Product> iterable = products.iterator();
        while (iterable.hasNext() && iterable.next().getName().equals(productName)) {
            result.add(iterable.next());
            products.remove(iterable.next());
        }
        return result;
    }

}
