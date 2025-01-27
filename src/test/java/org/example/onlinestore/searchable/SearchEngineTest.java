package org.example.onlinestore.searchable;

import org.example.onlinestore.exception.BestResultNotFound;
import org.example.onlinestore.searchable.product.DiscountedProduct;
import org.example.onlinestore.searchable.product.FixPriceProduct;
import org.example.onlinestore.searchable.product.SimpleProduct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
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
                        new HashSet<Searchable>(){{
                            add(new SimpleProduct("Milka", 756));
                }}),
                Arguments.of(new DiscountedProduct("Beers", 175, (byte) 10),  new HashSet<>(){{
                    add( new FixPriceProduct("Beers"));
                }}),
                Arguments.of(new FixPriceProduct("Beers"),  new HashSet<>(){{
                  add(new FixPriceProduct("Beers"));
                }}),
                Arguments.of(new Article("Title_", "text_"),  new HashSet<>(){{
                    add(new Article("Title_", "text_"));
                    add(new Article("Title_27", "text_26"));
                }}),
                Arguments.of(null, new HashSet<>(){})
                );
    }

    public static Stream<Arguments> searchProvider() {
        return Stream.of(
                Arguments.of("Mil", new HashSet<>() {{
                    add(new SimpleProduct("Milk", 75));
            }}),
                Arguments.of("Title",  new HashSet<>() {{
                    add(new Article("Title_", "text_"));
                    add(new Article("Title_2", "text_2"));
            }}),
                Arguments.of("Tomato",  new HashSet<>()
                ));
    }

    @ParameterizedTest
    @MethodSource("searchProvider")
     void searchT(String query, Set<Searchable> expected) {
        Set<Searchable> result  = out.search(query);

            assertEquals(result.toString(), result.toString());
    }

    @ParameterizedTest
    @MethodSource("provideAdd")
    void add(Searchable searchable, Set<Searchable> expected) {
        if (searchable == null) {
            RuntimeException exception = assertThrows(NullPointerException.class, () -> out.add(searchable));
            assertEquals("Product is null", exception.getMessage());
        }else {
            out.add(searchable);
            assertEquals(expected, out.search(searchable.getSearchTerm()));
        }
    }

    public static Stream<Arguments> providerSearchTerm() {
        return Stream.of(
                Arguments.of("e", new LinkedHashSet<>(){
            {
                add(new DiscountedProduct("Beer", 175, (byte) 10));
                add(new FixPriceProduct("Beers"));
                add(new Article("Title_", "text_"));
                add(new Article("Title_27", "text_26"));
            }}),
                Arguments.of("Mil", new LinkedHashSet<>(){{
                    add(new SimpleProduct("Milk", 75));
                }}),
                Arguments.of("Title",new LinkedHashSet<>(){{

                    add(new Article("Title_", "text_"));
                    add(new Article("Title_27", "text_26"));
                }})
        );
    }

    @ParameterizedTest
    @MethodSource("providerSearchTerm")
    void testGetSearchTerm(String searchable,Set<Searchable> object) throws BestResultNotFound {
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