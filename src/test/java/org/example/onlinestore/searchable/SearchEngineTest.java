package org.example.onlinestore.searchable;

import org.example.onlinestore.exception.BestResultNotFound;
import org.example.onlinestore.searchable.product.DiscountedProduct;
import org.example.onlinestore.searchable.product.FixPriceProduct;
import org.example.onlinestore.searchable.product.SimpleProduct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.TreeMap;
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
                out.add(new Article("Title_27", "text_26"));
    }

    public static Stream<Arguments> provideAdd() {
        return Stream.of(
                Arguments.of(new SimpleProduct("Milka", 756),
                        new TreeMap<String, Searchable>(){{
                            put("Milka", new SimpleProduct("Milka", 756));
                }}),
                Arguments.of(new DiscountedProduct("Beer", 175, (byte) 10),  new TreeMap<String, Searchable>(){{
                    put("Beer", new DiscountedProduct("Beer", 175, (byte) 10));
                    put("Beers", new FixPriceProduct("Beers"));
                }}),
                Arguments.of(new FixPriceProduct("Beers"),  new TreeMap<String, Searchable>(){{
                  put("Beers", new FixPriceProduct("Beers"));
                }}),
                Arguments.of(new Article("Title_", "text_"),  new TreeMap<String, Searchable>(){{
                    put("Title_", new Article("Title_", "text_"));
                    put("Title_27", new Article("Title_27", "text_26"));
                }}),
                Arguments.of((Object) null, new TreeMap<String, Searchable>(){})
                );
    }

    public static Stream<Arguments> searchProvider() {
        return Stream.of(
                Arguments.of("Mil", new TreeMap<String, Searchable>() {{
                    put("Milk", new SimpleProduct("Milk", 75));
            }}),
                Arguments.of("Title",  new TreeMap<String, Searchable>() {{
                    put("Title_", new Article("Title_", "text_"));
                    put("Title_2", new Article("Title_2", "text_2"));
            }}),
                Arguments.of("Tomato",  new TreeMap<String, Searchable>()
                ));
    }

    @ParameterizedTest
    @MethodSource("searchProvider")
     void searchT(String query, Map<String, Searchable> expected) {
        Map<String, Searchable> result  = out.search(query);

            assertEquals(result.toString(), result.toString());
    }

    @ParameterizedTest
    @MethodSource("provideAdd")
    void add(Searchable searchable, Map<String, Searchable> expected) {
        if (searchable == null) {
            RuntimeException exception = assertThrows(NullPointerException.class, () -> out.add(searchable));
            assertEquals("Product is null", exception.getMessage());
        }else {
            assertDoesNotThrow(() -> out.add(searchable));
            out.add(searchable);
            assertEquals(expected, out.search(searchable.getSearchTerm()));
        }
    }

    public static Stream<Arguments> providerSearchTerm() {
        return Stream.of(
                Arguments.of("e", new FixPriceProduct("Beers")),
                Arguments.of("Mil", new SimpleProduct("Milk", 75)),
                Arguments.of("Title",new Article("Title_27", "text_26"))
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