package org.example.onlinestore.searchable.product;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.onlinestore.basket.ProductBasket;
import org.example.onlinestore.searchable.Searchable;

@Data
@Setter
@Getter

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = DiscountedProduct.class, name = "discounted"),
        @JsonSubTypes.Type(value = FixPriceProduct.class, name = "fixed"),
        @JsonSubTypes.Type(value = SimpleProduct.class, name = "simple")
})
abstract public class Product implements Searchable {

    public static final String RETURN_THE_PRODUCT_NAME = "PRODUCT";

    private String name;
    private boolean isSpecial;

    public Product(String name) {
        if (!name.isBlank()) {
            this.name = name;
        }else
            throw new IllegalArgumentException("Product name cannot be blank.");
    }

    abstract public void addProduct(ProductBasket basket);
    abstract public int getPrice();
    abstract public boolean getIsSpecial();

}
