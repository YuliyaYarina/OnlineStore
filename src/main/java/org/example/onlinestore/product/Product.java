package org.example.onlinestore.product;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Data
@Setter
@Getter
public class Product {
//    private long id;
    private String name;
    private int price;

    public Product(String name, int price) {
//        this.id++;
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return  "<" + name +
                '>' + ": " +
                '<' + price +
                '>';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return price == product.price && Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }
}
