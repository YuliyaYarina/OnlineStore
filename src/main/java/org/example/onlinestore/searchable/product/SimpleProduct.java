package org.example.onlinestore.searchable.product;

import lombok.Getter;
import lombok.Setter;
import org.example.onlinestore.basket.ProductBasket;

import java.util.Objects;

@Setter
@Getter
public class SimpleProduct extends Product{

    private final int price;

    public SimpleProduct(String name, int price) {
        super(name);
        this.price = price;
    }

    @Override
    public void addProduct(ProductBasket basket) {
        basket.addProductBasket(this);
    }

    @Override
    public int getPrice() {
        return this.price;
    }

    @Override
    public boolean getIsSpecial() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SimpleProduct that = (SimpleProduct) o;
        return price == that.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), price);
    }

    @Override
    public String toString() {
        return "<" + getName() + ">: " +
                "<" + getPrice() +
                '>' + "\n";
    }

    @Override
    public String getSearchTerm() {
        return this.getName();
    }

    @Override
    public String getTypeContent() {
        return RETURN_THE_PRODUCT_NAME;
    }

    @Override
    public String getStringRepresentation() {
        return super.getStringRepresentation();
    }
}
