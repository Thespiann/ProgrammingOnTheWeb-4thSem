package cinema;
import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;

public final class Cinemas implements Serializable{
    private String cinemaName;
    private int cinemaNumberOfSeats;
    private int cinemaId;
    private boolean cinemaIs3d;

    public Cinemas(String cinemaName, int cinemaNumberOfSeats , int cinemaId , boolean cinemaIs3d){
        this.cinemaName = cinemaName;
        this.cinemaNumberOfSeats = cinemaNumberOfSeats;
        this.cinemaId = cinemaId;
        this.cinemaIs3d = cinemaIs3d;
    }
    public String getCinemaName(){
        return cinemaName;
    }

    public int getCinemaNumberOfSeats(){
        return cinemaNumberOfSeats;
    }
    public int getCinemaId(){
        return cinemaId;
    }

    public boolean getCinemaIs3d(){
        return cinemaIs3d;
    }




}
