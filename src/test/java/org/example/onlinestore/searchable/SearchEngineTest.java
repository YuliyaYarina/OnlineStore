package org.example.onlinestore.searchable;

import org.example.onlinestore.searchable.product.DiscountedProduct;
import org.example.onlinestore.searchable.product.FixPriceProduct;
import org.example.onlinestore.searchable.product.SimpleProduct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class SearchEngineTest {
    private SearchEngine out;

    @BeforeEach
    public void setUp() {
        out = new SearchEngine();
                out.add(new SimpleProduct("Milk", 75));
                out.add(new DiscountedProduct("Beer", 175, (byte) 10));
                out.add(new FixPriceProduct("Beers"));
                out.add(new Article("Title_", "text_"));
                out.add(new Article("Title_2", "text_2"));
    }

    public static Stream<Arguments> provideAdd() {
        return Stream.of(
                Arguments.of(new SimpleProduct("Milk", 75)),
                Arguments.of(new DiscountedProduct("Beer", 175, (byte) 10)),
                Arguments.of(new FixPriceProduct("Beers")),
                Arguments.of(new Article("Title_", "text_"))
//                Arguments.of(null)
                );
    }

    public static Stream<Arguments> searchProvider() {
        return Stream.of(
                Arguments.of("Mil", new Searchable[]{new SimpleProduct("Milk", 75)}),
                Arguments.of("Title", new Searchable[]{new Article("Title_", "text_")}),
                Arguments.of("Tomato", new Searchable[]{})
        );
    }

    @ParameterizedTest
    @MethodSource("searchProvider")
     void searchT(String query, Searchable[] expected) {
        Searchable[] result = out.search(query);

        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i].toString(), result[i].toString());
        }
    }

    @ParameterizedTest
    @MethodSource("provideAdd")
    void add(Searchable searchable) {
        if (searchable == null) {
         assertThrows(NullPointerException.class, () -> out.add(searchable), "Product is null");
            RuntimeException exception = assertThrows(RuntimeException.class, () -> out.add(searchable));
            assertEquals("Product is null", exception.getMessage());
        }else {
            assertDoesNotThrow(() -> out.add(searchable));
            out.add(searchable);
            assertEquals(searchable.toString(), out.toString());
        }
    }

}