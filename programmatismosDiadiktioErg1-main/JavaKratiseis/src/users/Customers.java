package users;
import cinema.Films;
import cinema.Provoles;

import java.util.List;

public class Customers extends Users {

    private int bookedSeats,numberOfReservations;

    public Customers(){
    }

    public Customers (String name, String username, String password){
        this.setName(name);
        this.setUsername(username);
        this.setPassword(password);
    }

    public void setBookedSeats(){this.bookedSeats = bookedSeats;}
    public void setnumberOfReservations(){this.numberOfReservations = numberOfReservations;}


    public void login(){
        System.out.println("--- Welcome, " + getUsername().toUpperCase() +" ---");
        this.setLoggedIn(true);
    }
    public void logout(){
        System.out.println("Customer " + getUsername().toUpperCase() + " logged out.");
        this.setLoggedIn(false);
    }

    public void showUserMenu(){
        System.out.println("--\n1.Now Playing Films");
        System.out.println("2.Make a Reservation");
        System.out.println("3.View your Reservation");
        System.out.println("4.Logout\n--");
    }

    public void nowPlayingFilms(List<Films> films){
        if (films.isEmpty()){
            System.out.println("There are no Available Films");
        }else{
            System.out.print("| ");
            for( Films f : films){
                System.out.print(f.getFilmTitle() + " | ");
            }
            System.out.println("");
        }
    }

    public void makeReservation(List<Provoles> provoles){
    	System.out.println("The current screenings are: ");	
    	for (Provoles provoli : provoles){
    		if(provoli.getProvoliIsAvailable()) {
        	System.out.println("Film: " + provoli.getProvoliFilm() + "| Cinema: " + provoli.getProvoliCinema());
    		}
    	}

    }

    public void viewReservation(){

    }
}
