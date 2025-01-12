package org.example.onlinestore.searchable;

import org.example.onlinestore.exception.BestResultNotFound;
import org.example.onlinestore.searchable.product.DiscountedProduct;
import org.example.onlinestore.searchable.product.FixPriceProduct;
import org.example.onlinestore.searchable.product.SimpleProduct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
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
                Arguments.of(new Article("Title_", "text_")),
                Arguments.of((Object) null)
                );
    }

    public static Stream<Arguments> searchProvider() {
        return Stream.of(
                Arguments.of("Mil", new ArrayList<Searchable>(){{
                    new SimpleProduct("Milk", 75);
            }}),
                Arguments.of("Title", new ArrayList<Searchable>(){{
                new Article("Title_", "text_");
            }}),
                Arguments.of("Tomato", new ArrayList<Searchable>()
        ));
    }

    @ParameterizedTest
    @MethodSource("searchProvider")
     void searchT(String query, List<Searchable> expected) {
        List<Searchable> result = out.search(query);

        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).toString(), result.get(i).toString());
        }
    }

    @ParameterizedTest
    @MethodSource("provideAdd")
    void add(Searchable searchable) {
        if (searchable == null) {
            RuntimeException exception = assertThrows(NullPointerException.class, () -> out.add(searchable));
            assertEquals("Product is null", exception.getMessage());
        }else {
            assertDoesNotThrow(() -> out.add(searchable));
            out.add(searchable);
            assertEquals(searchable.toString(), out.toString());
        }
    }

    public static Stream<Arguments> providerSearchTerm() {
        return Stream.of(
                Arguments.of("ers", new FixPriceProduct("Beers")),
                Arguments.of("Mil", new SimpleProduct("Milk", 75)),
                Arguments.of("Title", new Article("Title_", "text_"))
        );
    }

    @ParameterizedTest
    @MethodSource("providerSearchTerm")
    void testGetSearchTerm(String searchable, Searchable object) throws BestResultNotFound {
            assertEquals(object, out.getSearchTerm(searchable));
    }
    public static Stream<Arguments> providerSearchError() {
        return Stream.of(
                Arguments.of("Gasers", new BestResultNotFound("Для поискового запроса " + "Gasers" + " не нашлось подходящей статьи")),
                Arguments.of(null, new NullPointerException("Search is null"))
        );
    }
    @ParameterizedTest
    @MethodSource("providerSearchError")
    void testGetSearchTermError(String searchable, RuntimeException exception) throws BestResultNotFound {

        if (searchable == null) {
            RuntimeException exc = assertThrows(NullPointerException.class, () -> out.getSearchTerm(searchable));
            assertEquals(exc.getMessage(), exception.getMessage());
        }else {
            RuntimeException exc = assertThrows(BestResultNotFound.class, () -> out.getSearchTerm(searchable));
            assertEquals(exc.getMessage(), exception.getMessage());
        }
    }



}