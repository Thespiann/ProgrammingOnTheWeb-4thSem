package cinema;
import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;

public class Provoles implements Serializable{

    private int provoliId,provoliNumberOfReservations,numberOfSeatsLeft;
    private String provoliFilm,provoliCinema,provoliStartDate,provoliEndDate;
    private boolean provoliIsAvailable;

    public Provoles(){}

    public Provoles(Cinemas cinema, Films film){
        provoliIsAvailable = true;
        provoliFilm= film.getFilmTitle();
        provoliCinema= cinema.getCinemaName();
        numberOfSeatsLeft = cinema.getCinemaNumberOfSeats();
    }

    public void setProvoliStartDate(String provoliStartDate){
        this.provoliStartDate = provoliStartDate;
    }
    public void setProvoliEndDate(String provoliEndDate){
        this.provoliEndDate = provoliEndDate;
    }
    public void setProvoliIsAvailable(boolean provoliIsAvailable){
        this.provoliIsAvailable = provoliIsAvailable;
    }
    public void setProvoliId(int provoliId){
        this.provoliId = provoliId;
    }
    public void setprovoliNumberOfReservations(int provoliNumberOfReservations){
        this.provoliNumberOfReservations = provoliNumberOfReservations;
    }

    public String getProvoliFilm(){
        return provoliFilm;
    }
    public String getProvoliCinema(){
        return provoliCinema;
    }
    public boolean getProvoliIsAvailable() {
    	return provoliIsAvailable;
    }

}