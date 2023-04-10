package cinema;

import java.io.Serializable;

public class Films implements Serializable {
    private int filmId;
    private String filmTitle,filmCategory,filmDescription;

    public Films(){

    }

    public Films(int filmId, String filmTitle, String filmCategory, String filmDescription){
        this.filmId =  filmId;
        this.filmTitle = filmTitle;
        this.filmCategory = filmCategory;
        this.filmDescription = filmDescription;
    }

    public void setFilmId(int filmId) { this.filmId = filmId;}
    public void setFilmTitle(String filmTitle) { this.filmTitle = filmTitle;}
    public void setFilmCategory(String filmCategory) { this.filmCategory = filmCategory;}
    public void setFilmDescription(String filmDescription) { this.filmDescription = filmDescription;}

    public int getFilmId(){return filmId;}
    public String getFilmTitle(){return filmTitle;}
    public String getFilmCategory(){return filmCategory;}
    public String getFilmDescription(){ return filmDescription;}


}
