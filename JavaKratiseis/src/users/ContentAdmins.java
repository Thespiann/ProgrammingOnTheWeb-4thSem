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
        while(true){
            System.out.print("Enter the ID of Film: ");
            try{
                film.setFilmId(Integer.parseInt(reader.readLine()));
                break;
            }catch(IOException e){
                System.out.println("Error at Film ID");
            }catch(NumberFormatException e){
                System.out.print("Enter Integer ID of film: ");
            }catch(InputMismatchException e){
                System.out.print("Enter valid ID of film: ");
            }
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


    /*
    public void deleteFilm(List<Films> films,List<Provoles> provoles,List<Users> users){
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
                System.out.print("Enter an Integer value: ");
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
            for (Provoles provoli : provoles){
                if (provoli.getProvoliFilm().equals(foundFilm.getFilmTitle()) && foundFilm.getFilmId() == filmId){
                    provoles.remove(provoli);
                    break;
                }
            }
            for (Users user : users){
                if (user instanceof Customers){
                    List<Reservations> reservationsUser = ((Customers) user).getReservationsUser();
                    for (Reservations reservationUser : reservationsUser){
                        if (reservationUser.getProvoli().getProvoliFilm().equals(foundFilm.getFilmTitle())){
                            reservationsUser.remove(reservationUser);
                        }
                    }
                }
            }
            for (Provoles provoli : provoles){
                if (provoli.getProvoliFilm().equals(foundFilm.getFilmTitle())){
                    provoles.remove(provoli);
                }
            }
        }else{
            System.out.println("Film with ID " + filmId + " was not found.");
        }
    }
     */

    public void assignFilmToCinema(List<Films> films, Cinemas[] cinemas, List<Provoles> provoles) {
        if (films.isEmpty()) {
            System.out.println("No Films are Inserted.");
            return;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Cinemas cinemaToAssign;
        Films filmToAssign;
        int userInput;
        System.out.println("The Cinemas Available are ");
        for (Cinemas cinema : cinemas) {
            System.out.println(cinema.getCinemaName());
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
            } catch (NumberFormatException e) {
                System.out.println("Enter integer Film ID value: ");
            }
        }
        for (Cinemas cinema : cinemas) {
            if (cinema.getCinemaId() == userInput) {
                cinemaToAssign = cinema;
                System.out.println("Cinema " + cinemaToAssign.getCinemaName() + " ready to assign.");
                System.out.println("The Films Available are ");
                for (Films film : films) {
                    System.out.println(film.getFilmId() + " | " + film.getFilmTitle());
                }
                while (true) {
                    System.out.print("Enter the ID of Film: ");
                    try {
                        userInput = Integer.parseInt(reader.readLine());
                        break;
                    } catch (IOException e) {
                        System.out.println("Error at Film ID");
                    } catch (InputMismatchException e) {
                        System.out.print("Enter valid integer Film ID value: ");
                    } catch (NumberFormatException e) {
                        System.out.print("Enter integer Film ID value: ");
                    }
                }
                for (Films film : films) {
                    if (film.getFilmId() == userInput) {
                        filmToAssign = film;
                        System.out.println("Film " + filmToAssign.getFilmTitle() + " ready to assign.");
                        Provoles provoli = new Provoles(cinemaToAssign, filmToAssign);
                        System.out.println("Assigning: " + filmToAssign.getFilmTitle() + " to " + cinemaToAssign.getCinemaName().toUpperCase());

                        System.out.print("Enter the ID of Provoli: ");
                        try {
                            provoli.setProvoliId(Integer.parseInt(reader.readLine()));
                        } catch (IOException e) {
                            System.out.println("Error at Provoli ID");
                        } catch (InputMismatchException e) {
                            System.out.print("Enter integer Provoli ID value: ");
                        } catch (NumberFormatException e) {
                            System.out.print("Enter valid Provoli ID value: ");
                        }

                        System.out.print("Enter Provoli Start Date: ");
                        try {
                            provoli.setProvoliStartDate(reader.readLine());
                        } catch (IOException e) {
                            System.out.println("Error at Provoli Start Date.");
                        } catch (InputMismatchException e) {
                            System.out.print("Enter valid Provoli Start Date value: ");
                        } catch (NumberFormatException e) {
                            System.out.print("Enter a valid Provoli Start Date value: ");
                        }

                        System.out.print("Enter Provoli End Date: ");
                        try {
                            provoli.setProvoliEndDate(reader.readLine());
                        } catch (IOException e) {
                            System.out.println("Error at Provoli End Date.");
                        } catch (InputMismatchException e) {
                            System.out.print("Enter valid Provoli End Date value: ");
                        } catch (NumberFormatException e) {
                            System.out.print("Enter a valid Provoli End Date value: ");
                        }
                        provoles.add(provoli);
                        System.out.println("Provoli with ID:" + provoli.getProvoliId()
                                + " | " + provoli.getProvoliCinema()
                                + " | " + provoli.getProvoliFilm()
                                + " | Starting:" + provoli.getProvoliStartDate()
                                + " | Ending:" + provoli.getProvoliEndDate());
                        break;
                    }
                }
                break;
            }
        }
    }
}
