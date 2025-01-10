package org.example.onlinestore.searchable.product;

import lombok.Getter;
import lombok.Setter;
import org.example.onlinestore.basket.ProductBasket;

import java.util.Objects;

@Setter
@Getter
public class DiscountedProduct extends Product {

   private int basePrice;
   private byte salaryInPercents;

    public DiscountedProduct(String name, int basePrice, byte salaryInPercents){
        super(name);
        if (basePrice > 0){
            this.basePrice = basePrice;
        }else
           throw new IllegalArgumentException("basePrice must be greater than zero");

        if (salaryInPercents >= 0 && salaryInPercents <= 100) {
            this.salaryInPercents = salaryInPercents;
        }else
            throw new IllegalArgumentException("salaryInPercents must be between 0 and 100");
    }

    @Override
    public void addProduct(ProductBasket basket) {
        basket.addProductBasket(this);
    }

    @Override
    public int getPrice() {
        return basePrice - (basePrice * salaryInPercents / 100) ;
    }

    @Override
    public boolean getIsSpecial() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DiscountedProduct that = (DiscountedProduct) o;
        return basePrice == that.basePrice && salaryInPercents == that.salaryInPercents;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), basePrice, salaryInPercents);
    }

    @Override
    public String toString() {
        return "<" + getName() + ">:" +
                "<" + getPrice() +
                '>' + " (<" + salaryInPercents +
                ">%)" + "\n";
    }

    @Override
    public String getSearchTerm() {
        return this.getName();
    }

    @Override
    public String getTypeContent() {
        return "PRODUCT";
    }

    @Override
    public String getStringRepresentation() {
        return super.getStringRepresentation();
    }
}
