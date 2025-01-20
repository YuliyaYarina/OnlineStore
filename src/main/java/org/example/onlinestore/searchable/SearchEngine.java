package org.example.onlinestore.searchable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.example.onlinestore.exception.BestResultNotFound;

import java.util.Map;
import java.util.TreeMap;

@Slf4j
@Data
@Setter
@Getter
public class SearchEngine {

    private Map<String, Searchable> searchables;

    public SearchEngine() {
        this.searchables = new TreeMap<>();
    }

    public Map<String, Searchable> search(String string) {
        Map<String, Searchable> arrayNew = new TreeMap<>();

        for (Map.Entry<String, Searchable> entry : this.searchables.entrySet()) {
            if (entry.getKey().contains(string)) {
                arrayNew.put(entry.getKey(), entry.getValue());
            }
        }
        return arrayNew;
    }

    public void add(Searchable searchable) {
        if (searchable == null) throw new NullPointerException("Product is null");
        searchables.put(searchable.getSearchTerm(), searchable);
 }


    public String toStringAllSearchable() {
        StringBuilder b = new StringBuilder();
        for (Map.Entry<String, Searchable> entry : this.searchables.entrySet()) {
            if (searchables.containsKey(entry.getKey())) {
                b.append(this.searchables.get(entry.getKey()).toString());
            }
        }
        return b.toString();
    }
    /** возвращает объект, который getSearchTerm() содержит максимальное количество повторов строки
     search **/
    public Searchable getSearchTerm(String search) throws BestResultNotFound {
        if (search == null) throw new NullPointerException("Search is null");

        Map<Integer, Searchable> searchableTreeMap = new TreeMap<>();
        Map<String, Searchable> searched = search(search); // получаем объекты Searchable в которых есть хоть 1 совпадение

        if (searched.isEmpty())throw new BestResultNotFound("Для поискового запроса " + search + " не нашлось подходящей статьи");

        int replay = 0;
        for (Map.Entry<String, Searchable> entry : searched.entrySet()) {

            int finish = search.length();
            int starts = 0;

            while (finish <= entry.getKey().length()) { // проходим по объекту и проверяем есть ли еще сходства и считаем их
                if(entry.getKey().substring(starts, finish).contains(search)) {
                    replay++;
                }
                starts ++;
                finish++;
            }
            searchableTreeMap.put(replay, entry.getValue());
            replay = 0;
        }
        int lastKay = searchableTreeMap.size();
        return searchableTreeMap.get(lastKay);
    }

}
