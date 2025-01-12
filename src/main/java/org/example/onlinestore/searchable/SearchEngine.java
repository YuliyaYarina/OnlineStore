package org.example.onlinestore.searchable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.onlinestore.exception.BestResultNotFound;

import java.util.ArrayList;
import java.util.List;

@Data
@Setter
@Getter
public class SearchEngine {

    private List<Searchable> searchables;

    private static final int DIMENSION_OF_ARRAY = 10;

    public SearchEngine() {
        this.searchables = new ArrayList<>(DIMENSION_OF_ARRAY);
    }

    public List<Searchable> search(String string) {
        List<Searchable> arrayNew = new ArrayList<>(DIMENSION_OF_ARRAY);

        for (int i = 0; i < searchables.size(); i++) {
            if (searchables.get(i) != null && searchables.get(i).toString().contains(string)) {
                arrayNew.add(searchables.get(i));
            }
        }
        return arrayNew;
    }

    public void add(Searchable searchable) {
        if (searchable == null) throw new NullPointerException("Product is null");

      searchables.add(searchable);
//        if (searchables.get(9) != null) throw new RuntimeException("Maximum search limit reached");
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        for (int i = searchables.size()- 1; i >= 0; i--) {
            if (searchables.get(i) != null) {
                b.append(this.searchables.get(i).toString());
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
