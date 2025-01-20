package org.example.onlinestore.basket;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.onlinestore.searchable.product.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Data
@Getter
@Setter
@Service
public class ProductBasket implements ProductBasketImpl{

    Logger logger = LoggerFactory.getLogger(ProductBasket.class);

    private Map<String, List<Product>> products;
    private byte specialProducts;

    public ProductBasket() {
        this.products = new TreeMap<>();
        this.specialProducts = 0;
    }

    @Override
    public void addProductBasket(Product product){
        if (product == null) throw new NullPointerException("Product is null");
        List<Product> add;

        if (!checkedProductBasket(product.getName())){
            add = products.put(product.getName(), new LinkedList<>(){{
                add(product);
            }});
            if (product.getIsSpecial()) specialProducts++;
            logger.info(" products.get(product.getName()).add(product)==== " +  add);
        }else {
            products.get(product.getName()).add(product);  // добавляет только 1 экземпляр товара, данный метод предположительно не работает
            logger.info("Added product " + product.getName() + " to the basket");
            logger.info(" products.get(product.getName()).add(product)= " +  products.get(product.getName()).add(product));
        }
    }

    @Override
    public int getSalaryProductBasket() {
        int sum = 0;
        for(Map.Entry<String, List<Product>> product: products.entrySet()){
            if (!product.getValue().isEmpty()){
                sum += product.getValue().getFirst().getPrice();
                logger.info("sum = " + sum);
            }
        }
        return sum;
    }


    @Override
    public String getSumProductBasket() {
       StringBuilder su = new StringBuilder();
        if (getSalaryProductBasket() != 0) {
            for (Map.Entry<String, List<Product>> product: products.entrySet()) {
                su.append(product.getValue().getFirst().toString());
                logger.info("getSumProductBasket.su ==" + su);
            }
            return su + "Итого: " + '<' + getSalaryProductBasket() + '>' + "\n" +
                     "Специальных товаров: " + '<' + specialProducts + '>';
        }else {
            return "в корзине пусто";
        }
    }

    @Override
    public boolean checkedProductBasket(String productName) {
        return products.containsKey(productName);
    }

    @Override
    public void removeAllProductBasket() {
       products.clear();
    }

    @Override
    public List<Product> removeProductBasket(String productName) {
        List<Product> result = new LinkedList<>();
        for(Map.Entry<String, List<Product>> product: products.entrySet()){
            if (product.getKey().equals(productName)){
                result.addAll(product.getValue());
                product.getValue().removeFirst();
            }
        }
        return result;
    }
}
