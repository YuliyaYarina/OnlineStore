package org.example.onlinestore.basket;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.onlinestore.product.Product;
import org.springframework.stereotype.Service;

@Data
@Getter
@Setter
@Service
public class ProductBasket implements ProductBasketImpl{

    private Product[] product = new Product[5];

    @Override
    public Product addProductBasket(Product productAdd) throws RuntimeException {
        if (productAdd == null) throw new NullPointerException("Product is null");

        for (int i = 0; i < product.length ; i++) {
            if (product[i] == null) {
                product[i] = productAdd;
                return product[i];
            }
        }
        throw new IndexOutOfBoundsException("Невозможно добавить продукт");
    }

    @Override
    public int getSalaryProductBasket() {
        int sum = 0;
        for (Product value : product) {
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
            for (Product value : product) {
                if (value != null) su += value + "\n";
            }
            return su + "Итого: " + '<' + getSalaryProductBasket() + '>';
        }else {
            return "в корзине пусто";
        }
    }

    @Override
    public boolean checkedProductBasket(String productName) {
        for (Product value : product) {
            if (value == null) continue;
            if (value.getName().equals(productName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void removeAllProductBasket() {
        for (int i = 0; i < product.length; i++) {
            product[i] = null;
        }
        product = new Product[]{};
    }

}
