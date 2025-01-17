package org.example.onlinestore.searchable.product;

import lombok.Getter;
import lombok.Setter;
import org.example.onlinestore.basket.ProductBasket;

@Setter
@Getter
public class FixPriceProduct extends Product{
    public static final int FIX_PRICE = 99;

    public FixPriceProduct(String name) {
        super(name);
    }

    @Override
    public void addProduct(ProductBasket basket) {
        basket.addProductBasket(this);
    }

    @Override
    public int getPrice() {
        return FIX_PRICE;
    }

    @Override
    public boolean getIsSpecial() {
        return true;
    }

    @Override
    public String toString() {
        return "<" + getName() + ">: Фиксированная цена " +
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
