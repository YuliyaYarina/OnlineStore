package org.example.onlinestore.searchable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.example.onlinestore.exception.BestResultNotFound;

import java.util.*;

@Slf4j
@Data
@Setter
@Getter
public class SearchEngine {

    private Set<Searchable> searchables;
    Comparator<Searchable> compare = new Comparator<Searchable>() {
        @Override
        public int compare(Searchable o1, Searchable o2) {
            return o2.getSearchTerm().compareTo(o1.getSearchTerm());
        }
    };

    public SearchEngine() {
        this.searchables =  new TreeSet<>(compare);
    }

    public Set<Searchable> search(String string) { // проходит по множеству, проверяет на наличие, и добавляет в новое множество
        Set<Searchable> arrayNew = new TreeSet<>(new Comparator<Searchable>() {
            @Override
            public int compare(Searchable o1, Searchable o2) {
                return Integer.compare(o1.getSearchTerm().length(), o2.getSearchTerm().length());
            }
        });
        for (Searchable entry : searchables) {
            if (entry.getSearchTerm().contains(string)) {
                arrayNew.add(entry);
            }
        }
        return arrayNew;
    }

    public void add(Searchable searchable) {
        if (searchable == null) throw new NullPointerException("Product is null");
        searchables.add(searchable);
    }

    public String toStringAllSearchable() { // выводит все значения
        StringBuilder b = new StringBuilder();
        Iterator<Searchable> iterator = searchables.iterator();
        while (iterator.hasNext()) {
          if (searchables.contains(iterator.next())) {
                b.append(iterator);
          }
        }
        return b.toString();
    }

    /** возвращает объект, который getSearchTerm() содержит максимальное количество повторов строки
     search **/
    public Set<Searchable> getSearchTerm(String search) throws BestResultNotFound {
        if (search == null) throw new NullPointerException("Search is null");

        Set<Searchable> searched = search(search); // получаем объекты Searchable в которых есть хоть 1 совпадение
        if (searched.isEmpty())throw new BestResultNotFound("Для поискового запроса " + search + " не нашлось подходящей статьи");

        return returnGetSetSearchable(search, searched);
    }


    private Set<Searchable> returnGetSetSearchable(String search, Set<Searchable> searched) { //  метод высчитывает конкретное кол-во повторов и возвращает от большего числа к меньшему

        Map<Searchable, Integer> linkedHashMap = new LinkedHashMap<>();
        Set<Searchable> returnSet = new LinkedHashSet<>();

        int replay = 0;
        Iterator<Searchable> iterator = searched.iterator();
        Searchable objectSet = iterator.next();
        int finish = search.length();
        int starts = 0;

        while (!objectSet.getSearchTerm().isEmpty()) {
            while (finish <= objectSet.getSearchTerm().length()) { // проходим по объекту и проверяем есть ли еще сходства и считаем их
                if (objectSet.getSearchTerm().substring(starts, finish).contains(search)) {
                    replay++;
                }
                starts++;
                finish++;
            }

            linkedHashMap.put(objectSet, replay);
            replay = 0;

            List<Map.Entry<Searchable, Integer>> sortedEntries = new ArrayList<>(linkedHashMap.entrySet());
            sortedEntries.sort((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue())); // сортировка от числа больших совпадений к меньшим

            returnSet = linkedHashMap.keySet();
            if (iterator.hasNext()){
                objectSet = iterator.next();
            }else
                break;
        }

        return returnSet;
    }

}
