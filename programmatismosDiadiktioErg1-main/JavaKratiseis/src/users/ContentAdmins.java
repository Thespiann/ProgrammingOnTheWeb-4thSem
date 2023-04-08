package users;
import cinema.Cinemas;
import cinema.Films;
import cinema.Provoles;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.List;

public class ContentAdmins extends Users {
    public ContentAdmins(){

    }

    public ContentAdmins(String name,String username, String password){
        this.setName(name);
        this.setUsername(username);
        this.setPassword(password);
    }
    public void login(){
        System.out.println("--- Welcome Content Admin, " + getUsername().toUpperCase() +" ---");
    }
    public void logout(){
        System.out.println("Content Admin " + getUsername().toUpperCase() + " logged out.");
    }

    public void showUserMenu(){
        System.out.println("--\n1.Insert New Film");
        System.out.println("2.Delete Film");
        System.out.println("3.Assign Film to Cinema");
        System.out.println("4.Logout\n--");
    }

    public void insertFilm(List<Films> films){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Films film = new Films();
        System.out.print("Enter the ID of Film: ");
        try{
            film.setFilmId(Integer.parseInt(reader.readLine()));
        }catch(IOException e){
            System.out.println("Error at Film ID");
        }catch(NumberFormatException e){
            System.out.println("Wrong Input at Film ID");
        }
        System.out.print("Enter the Title of the Film: ");
        try{
            film.setFilmTitle(reader.readLine());
        }catch(IOException e){
            System.out.println("Wrong Input at Film Title");
        }
        System.out.print("Enter the Category of the Film: ");
        try{
            film.setFilmCategory(reader.readLine());
        }catch(IOException e){
            System.out.println("Wrong Input at Film Category");
        }
        System.out.print("Enter the Description of the Film: ");
        try{
            film.setFilmDescription(reader.readLine());
        }catch(IOException e){
            System.out.println("Wrong Input at Film Description");
        }
        System.out.println("Inserting New Film "+ film.getFilmTitle());
        films.add(film);
    }

    public void deleteFilm(List<Films> films){
        int filmId;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter the Film ID you want to Delete: ");
        while (true){
            try{
                filmId = Integer.parseInt(reader.readLine());
                break;
            }catch(IOException e){
                System.out.println("Error in User Input");
            }catch(NumberFormatException e){
                System.out.println("Enter an Integer value: ");
            }
        }
        boolean found = false;
        Films foundFilm = null;
        for (Films film : films){
            if (film.getFilmId() == filmId){
                found = true;
                foundFilm = film;
            }
        }
        if (found){
            System.out.println("Deleting Film " + foundFilm.getFilmTitle());
            films.remove(foundFilm);
        }else{
            System.out.println("Film with ID " + filmId + " was not found.");
        }

    }

    public void assignFilmToCinema(List<Films> films, Cinemas[] cinemas, List<Provoles> provoles) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Cinemas cinemaToAssign = null;
        Films filmToAssign = null;
        int userInput;
        System.out.println("The Cinemas Available are ");
        for (Cinemas cinema : cinemas) {
            System.out.println(cinema.getCinemaName() + " | " + cinema.getCinemaId());
        }
        while (true) {
            System.out.print("Enter the ID of the cinema: ");
            try {
                userInput = Integer.parseInt(reader.readLine());
                break;
            } catch (IOException e) {
                System.out.println("Error at Cinema ID");
            } catch (InputMismatchException e) {
                System.out.println("Enter integer Cinema ID value: ");
            }catch (NumberFormatException e){
                System.out.println("Enter integer Film ID value: ");
            }
        }
        for (Cinemas cinema : cinemas) {
            if (cinema.getCinemaId() == userInput) {
                cinemaToAssign = cinema;
                System.out.println("Cinema " + cinemaToAssign.getCinemaName() + " ready to assign.");
                System.out.println("The Films Available are ");
                for (Films film : films) {
                    System.out.println(film.getFilmTitle() + " | " + film.getFilmId());
                }
                while (true) {
                    System.out.print("Enter the ID of Film: ");
                    try {
                        userInput = Integer.parseInt(reader.readLine());
                        break;
                    } catch (IOException e) {
                        System.out.println("Error at Film ID");
                    } catch (InputMismatchException e) {
                        System.out.println("Enter integer Film ID value: ");
                    }catch (NumberFormatException e){
                        System.out.println("Enter integer Film ID value: ");
                    }
                }
                for (Films film : films) {
                    if (film.getFilmId() == userInput) {
                        filmToAssign = film;
                        System.out.println("Film " + filmToAssign.getFilmTitle() + " ready to assign.");
                        System.out.println("Now Assigning film: " + filmToAssign.getFilmTitle() + " to cinema " + cinemaToAssign.getCinemaName().toUpperCase());
                        Provoles provoli = new Provoles(cinemaToAssign, filmToAssign);
                        provoles.add(provoli);                  
                        break;
                    }
                }
                break;
            }
        }
    }

}
