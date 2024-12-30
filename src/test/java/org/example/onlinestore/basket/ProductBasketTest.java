package org.example.onlinestore.basket;

import org.example.onlinestore.product.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ProductBasketTest {

    private final ProductBasket out = new ProductBasket();
    private final ProductBasketImpl impl = new ProductBasket();

    public static Stream<Arguments> provideProductsForAdd() {
        return Stream.of(
                Arguments.of(new Product("Milk", 75), true),
                Arguments.of(new Product("Beer", 175), false)
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
                Arguments.of( new Product[] { new Product("Apple", 10),
                        new Product("Banana", 20)} , 30)
        );
    }

    public static Stream<Arguments> provideBasketForSumProducts() {
        return Stream.of(
                Arguments.of(new Product[]{}, "в корзине пусто"),
                Arguments.of( new Product[] { new Product("Apple", 10),
                        new Product("Banana", 20)} , """
                        <Apple>: <10>
                        <Banana>: <20>
                        Итого: <30>""")
        );
    }
    public static Stream<Arguments> provideCheckedForTrueProducts() {
        return Stream.of(
                Arguments.of(new Product[]{}, "Shower", false),
                Arguments.of( new Product[] { new Product("Apple", 10),
                        new Product("Banana", 20)}, "Shower", false),
                Arguments.of( new Product[] { new Product("Apple", 10),
                        new Product("Banana", 20), new Product("Shower", 500)}, "Shower" , true)
        );
    }
    public static Stream<Arguments> provideDeletedAllForProducts() {
        return Stream.of(
                Arguments.of((Object) new Product[5]),
                Arguments.of((Object) new Product[]{}),
                Arguments.of((Object) new Product[] { new Product("Apple", 10),
                        new Product("Banana", 20)}),
                Arguments.of( (Object)new Product[] { new Product("Apple", 10),
                        new Product("Banana", 20), new Product("Shower", 500)})
        );
    }

    @ParameterizedTest
    @MethodSource("provideProductsForAdd")
    void testAddProductInBasket(Product product, boolean expectSuccess) throws RuntimeException {
        if (!expectSuccess) {
            for (int i = 0; i < out.getProduct().length; i++) {
                out.getProduct()[i] = new Product("Product" + i, 80);
            }
        }

        if (expectSuccess) {
            Product addedProduct = out.addProductBasket(product);
            assertEquals(product, addedProduct);
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
        out.setProduct(product);

        int result = out.getSalaryProductBasket();
        assertEquals(sum, result);

    }

    @ParameterizedTest
    @MethodSource("provideBasketForSumProducts")
    void testGetSumProductBasket(Product[] products, String expectedMessage) {
        out.setProduct(products);

        String result = out.getSumProductBasket();
        assertEquals(expectedMessage, result);

    }

    @ParameterizedTest
    @MethodSource("provideCheckedForTrueProducts")
    void checkedProductBasket(Product[] products, String productName, boolean expectSuccess) throws RuntimeException {
        out.setProduct(products);

        Boolean result = out.checkedProductBasket(productName);
        assertEquals(expectSuccess, result);
    }

    @ParameterizedTest
    @MethodSource("provideDeletedAllForProducts")
    void removeAllProductBasket(Product[] product) {
        out.setProduct(product);
        out.removeAllProductBasket();

        assertEquals(0, out.getProduct().length);
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