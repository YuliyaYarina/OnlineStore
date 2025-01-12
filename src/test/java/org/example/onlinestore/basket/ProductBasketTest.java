package org.example.onlinestore.basket;

import org.example.onlinestore.searchable.product.DiscountedProduct;
import org.example.onlinestore.searchable.product.FixPriceProduct;
import org.example.onlinestore.searchable.product.Product;
import org.example.onlinestore.searchable.product.SimpleProduct;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductBasketTest {

    private final ProductBasket out = new ProductBasket();

    public static Stream<Arguments> provideProductsForAdd() {
        return Stream.of(
                Arguments.of(new SimpleProduct("Milk", 75), false),
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
                Arguments.of(new LinkedList<Product>(), 0),
                Arguments.of(new LinkedList<Product>(){{
                    add(new FixPriceProduct("Apple"));
                    add(new SimpleProduct("Banana", 20));
                }}, 119)
        );
    }

    public static Stream<Arguments> provideBasketForSumProducts() {
        return Stream.of(
                Arguments.of(new LinkedList<Product>(), "в корзине пусто"),
                Arguments.of( new LinkedList<Product>() {{
                    add(new SimpleProduct("Apple", 10));
                    add(new SimpleProduct("Banana", 20));
                }} , """
                        <Apple>: <10>
                        <Banana>: <20>
                        Итого: <30>
                        Специальных товаров: <0>""")
        );
    }
    public static Stream<Arguments> provideCheckedForTrueProducts() {
        return Stream.of(
                Arguments.of(new LinkedList<Product>(), "Shower", false),
                Arguments.of( new LinkedList<Product>(){{
                    add(new SimpleProduct("Apple", 10));
                    add(new SimpleProduct("Banana", 20));
                }}, "Shower", false),
                Arguments.of( new LinkedList<Product>(){{
                    add(new SimpleProduct("Apple", 10));
                            add(new SimpleProduct("Banana", 20));
                            add(new SimpleProduct("Shower", 500));
                }}, "Shower" , true)
        );
    }
    public static Stream<Arguments> provideDeletedAllForProducts() {
        return Stream.of(
                Arguments.of(new LinkedList<Product>()),
                Arguments.of(new LinkedList<Product>(){{
                        add(new SimpleProduct("Apple", 10));
                        add(new SimpleProduct("Banana", 20));
                    }}
                ),
                Arguments.of(new LinkedList<Product>(){{
                        add(new SimpleProduct("Apple", 10));
                        add(new SimpleProduct("Banana", 20));
                        add(new SimpleProduct("Shower", 500));
                }}
                )
        );
    }

    @ParameterizedTest
    @MethodSource("provideProductsForAdd")
    void testAddProductInBasket(Product product, boolean expectSuccess) throws RuntimeException {

//        if (expectSuccess) {
            out.addProductBasket(product);
            assertEquals(product.getPrice(), out.getProducts().getFirst().getPrice());
//        }
//        else {
//            RuntimeException exception = assertThrows(IndexOutOfBoundsException.class, () -> out.addProductBasket(product));
//            assertEquals("Невозможно добавить продукт", exception.getMessage());
//        }
    }

    @ParameterizedTest
    @MethodSource("provideNullProduct")
    void testAddNullProduct(Product product, String expectedMessage) {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> out.addProductBasket(product));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("provideForSumProducts")
    void getSalaryProductBasket(List<Product> product, int sum) {
        out.setProducts(product);

        int result = out.getSalaryProductBasket();
        assertEquals(sum, result);

    }

    @ParameterizedTest
    @MethodSource("provideBasketForSumProducts")
    void testGetSumProductBasket(List<Product> products, String expectedMessage) {
        out.setProducts(products);

        String result = out.getSumProductBasket();
        assertEquals(expectedMessage, result);

    }

    @ParameterizedTest
    @MethodSource("provideCheckedForTrueProducts")
    void checkedProductBasket(List<Product> products, String productName, boolean expectSuccess) throws RuntimeException {
        out.setProducts(products);

        Boolean result = out.checkedProductBasket(productName);
        assertEquals(expectSuccess, result);
    }

    @ParameterizedTest
    @MethodSource("provideDeletedAllForProducts")
    void removeAllProductBasket(List<Product> product) {
        out.setProducts(product);
        out.removeAllProductBasket();

        assertEquals(0, out.getProducts().size());
    }

    @ParameterizedTest
    @ValueSource(ints = {-100, 0, -1})  // Неприемлемые значения
    public void testIllegalArgumentExceptionBasePrice(int price) {
        RuntimeException trows = assertThrows(IllegalArgumentException.class, () -> {
            new SimpleProduct("Milk", price);  // Проверяем, выбрасывается ли IllegalArgumentException
        });
        assertEquals("basePrice must be greater than zero", trows.getMessage());
    }
    @ParameterizedTest
    @ValueSource(bytes = {101, -1})  // Неприемлемые значения
    public void testIllegalArgumentExceptionSalaryInPercents(byte percent) {
        RuntimeException trows = assertThrows(IllegalArgumentException.class, () -> {
            new DiscountedProduct("Beer", 100, percent);  // Проверяем, выбрасывается ли IllegalArgumentException
        });
        assertEquals("salaryInPercents must be between 0 and 100", trows.getMessage());
    }
    @ParameterizedTest
    @ValueSource(strings = {"     ", ""})  // Неприемлемые значения
    public void testIllegalArgumentExceptionProduct(String product) {
        RuntimeException trows = assertThrows(IllegalArgumentException.class, () -> {
            new FixPriceProduct(product);  // Проверяем, выбрасывается ли IllegalArgumentException
        });
        assertEquals("Product name cannot be blank.", trows.getMessage());
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