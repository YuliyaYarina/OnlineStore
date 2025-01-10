package org.example.onlinestore.searchable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public final class Article implements Searchable{

    public static final String RETURN_THE_ARTICLE_NAME = "ARTICLE";

    private String title;
    private String text;

    public Article(String title, String text) {
        this.title = title;
        this.text = text;
    }

    @Override
    public String toString() {
        return  title + "\n" +
                text + "\n" ;
    }

    @Override
    public String getSearchTerm() {
        return this.toString();
    }

    @Override
    public String getTypeContent() {
        return RETURN_THE_ARTICLE_NAME;
    }

    @Override
    public String getStringRepresentation() {
        return Searchable.super.getStringRepresentation();
    }
}
