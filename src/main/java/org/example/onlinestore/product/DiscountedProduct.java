package org.example.onlinestore.product;

import lombok.Getter;
import lombok.Setter;
import org.example.onlinestore.basket.ProductBasket;

import java.util.Objects;

@Setter
@Getter
public class DiscountedProduct extends Product {

   private int basePrice;
   private byte salaryInPercents;

    public DiscountedProduct(String name, int basePrice, byte salaryInPercents) {
        super(name);
        this.basePrice = basePrice;
        this.salaryInPercents = salaryInPercents;
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
}
