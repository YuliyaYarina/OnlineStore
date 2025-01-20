package org.example.onlinestore.searchable;

public interface Searchable {
    String getSearchTerm();
    String getTypeContent();

    default String getStringRepresentation() {
        return "имя " +
                getSearchTerm() +
                "-объекта \n — тип " +
                getTypeContent() +
                "-объекта";
    }
}
