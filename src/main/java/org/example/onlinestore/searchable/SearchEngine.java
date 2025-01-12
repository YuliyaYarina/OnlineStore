package org.example.onlinestore.searchable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.onlinestore.exception.BestResultNotFound;

@Data
@Setter
@Getter
public class SearchEngine {

    private Searchable[] searchables;

    private static final int MAX_SIZE_SEARCH = 5;
    private static final int DIMENSION_OF_ARRAY = 10;

    public SearchEngine() {
        this.searchables = new Searchable[DIMENSION_OF_ARRAY];
    }

    public Searchable[] search(String string) {
        Searchable[] arrayNew = new Searchable[DIMENSION_OF_ARRAY];

        for (int i = 0; i < searchables.length; i++) {
            if (searchables[i] != null && searchables[i].toString().contains(string)) {
                for (int j = 0; j < MAX_SIZE_SEARCH; j++) {
                    if (arrayNew[j] == null) {
                        arrayNew[j] = searchables[i];
                        break;
                    }
                }
            }
        }
        return arrayNew;
    }

    public void add(Searchable searchable) {
        if (searchable == null) throw new NullPointerException("Product is null");

        for (int i = 0; i < searchables.length; i++) {
            if (searchables[i] == null) {
                searchables[i] = searchable;
                break;
            }
        }
        if (searchables[9] != null) throw new RuntimeException("Maximum search limit reached");
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        for (int i = searchables.length - 1; i >= 0; i--) {
            if (searchables[i] != null) {
                b.append(this.searchables[i]);
                break;
            }
        }
        return b.toString();
    }
    /** возвращает объект, который getSearchTerm() содержит максимальное количество повторов строки
     search **/
    public Searchable getSearchTerm(String search) throws BestResultNotFound {

        if (search == null) throw new NullPointerException("Search is null");
        int count = 0;
        int index= 0;
        int indexStringOf = 0;
        for (Searchable searchable : searchables) {
            if (searchable == null) break;
            indexStringOf = searchable.toString().indexOf(search,index);
            while (indexStringOf != -1) {
                count++;
                index += search.length();
                indexStringOf = searchable.getStringRepresentation().indexOf(search,index);
                return searchable;
            }
        }
        throw new BestResultNotFound("Для поискового запроса " + search + " не нашлось подходящей статьи");
    }

}
