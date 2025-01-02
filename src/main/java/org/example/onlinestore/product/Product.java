package org.example.onlinestore.product;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.onlinestore.basket.ProductBasket;

@Data
@Setter
@Getter

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = DiscountedProduct.class, name = "discounted"),
        @JsonSubTypes.Type(value = FixPriceProduct.class, name = "fixed"),
        @JsonSubTypes.Type(value = SimpleProduct.class, name = "simple")
})
abstract public class Product {

    private String name;
    private boolean isSpecial;

    public Product(String name) {
        this.name = name;
    }

    abstract public void addProduct(ProductBasket basket);
    abstract public int getPrice();
    abstract public boolean getIsSpecial();

}
