package org.example.onlinestore.basket;

import org.example.onlinestore.searchable.product.DiscountedProduct;
import org.example.onlinestore.searchable.product.FixPriceProduct;
import org.example.onlinestore.searchable.product.Product;
import org.example.onlinestore.searchable.product.SimpleProduct;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ProductBasketTest {

    private final ProductBasket out = new ProductBasket();
    private final ProductBasketImpl impl = new ProductBasket();

    public static Stream<Arguments> provideProductsForAdd() {
        return Stream.of(
                Arguments.of(new SimpleProduct("Milk", 75), true),
                Arguments.of(new DiscountedProduct("Beer", 175, (byte) 10), true),
                Arguments.of(new FixPriceProduct("Beer"), true)
        );
    }

    public static Stream<Arguments> provideNullProduct() {
        return Stream.of(
                Arguments.of(null, "Product is null")
        );
    }
    public static Stream<Arguments> provideForSumProducts() {
        return Stream.of(
                Arguments.of(new Product[]{}, 0),
                Arguments.of( new Product[] { new FixPriceProduct("Apple"),
                        new SimpleProduct("Banana", 20)} , 119)
        );
    }

    public static Stream<Arguments> provideBasketForSumProducts() {
        return Stream.of(
                Arguments.of(new Product[]{}, "в корзине пусто"),
                Arguments.of( new Product[] { new SimpleProduct("Apple", 10),
                        new SimpleProduct("Banana", 20)} , """
                        <Apple>: <10>
                        <Banana>: <20>
                        Итого: <30>
                        Специальных товаров: <0>""")
        );
    }
    public static Stream<Arguments> provideCheckedForTrueProducts() {
        return Stream.of(
                Arguments.of(new Product[]{}, "Shower", false),
                Arguments.of( new Product[] { new SimpleProduct("Apple", 10),
                        new SimpleProduct("Banana", 20)}, "Shower", false),
                Arguments.of( new Product[] { new SimpleProduct("Apple", 10),
                        new SimpleProduct("Banana", 20), new SimpleProduct("Shower", 500)}, "Shower" , true)
        );
    }
    public static Stream<Arguments> provideDeletedAllForProducts() {
        return Stream.of(
                Arguments.of((Object) new Product[5]),
                Arguments.of((Object) new Product[]{}),
                Arguments.of((Object) new Product[] { new SimpleProduct("Apple", 10),
                        new SimpleProduct("Banana", 20)}),
                Arguments.of( (Object)new Product[] { new SimpleProduct("Apple", 10),
                        new SimpleProduct("Banana", 20), new SimpleProduct("Shower", 500)})
        );
    }

    @ParameterizedTest
    @MethodSource("provideProductsForAdd")
    void testAddProductInBasket(Product product, boolean expectSuccess) throws RuntimeException {
        if (!expectSuccess) {
            for (int i = 0; i < out.getProducts().length; i++) {
                out.getProducts()[i] = new SimpleProduct("Product" + i, 80);
            }
        }

        if (expectSuccess) {
            out.addProductBasket(product);
            assertEquals(product.getPrice(), out.getProducts()[0].getPrice());
        }else {
            RuntimeException exception = assertThrows(IndexOutOfBoundsException.class, () -> out.addProductBasket(product));
            assertEquals("Невозможно добавить продукт", exception.getMessage());
        }
    }

    @ParameterizedTest
    @MethodSource("provideNullProduct")
    void testAddNullProduct(Product product, String expectedMessage) {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> out.addProductBasket(product));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("provideForSumProducts")
    void getSalaryProductBasket(Product[] product, int sum) {
        out.setProducts(product);

        int result = out.getSalaryProductBasket();
        assertEquals(sum, result);

    }

    @ParameterizedTest
    @MethodSource("provideBasketForSumProducts")
    void testGetSumProductBasket(Product[] products, String expectedMessage) {
        out.setProducts(products);

        String result = out.getSumProductBasket();
        assertEquals(expectedMessage, result);

    }

    @ParameterizedTest
    @MethodSource("provideCheckedForTrueProducts")
    void checkedProductBasket(Product[] products, String productName, boolean expectSuccess) throws RuntimeException {
        out.setProducts(products);

        Boolean result = out.checkedProductBasket(productName);
        assertEquals(expectSuccess, result);
    }

    @ParameterizedTest
    @MethodSource("provideDeletedAllForProducts")
    void removeAllProductBasket(Product[] product) {
        out.setProducts(product);
        out.removeAllProductBasket();

        assertEquals(0, out.getProducts().length);
    }

    @Test
    void testEquals() {
    }

    @Test
    void canEqual() {
    }

    @Test
    void testHashCode() {
    }

    @Test
    void testToString() {
    }

    @Test
    void getProduct() {
    }

    @Test
    void setProduct() {
    }
}