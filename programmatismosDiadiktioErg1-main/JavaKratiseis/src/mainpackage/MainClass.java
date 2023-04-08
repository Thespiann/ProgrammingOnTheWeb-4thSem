package mainpackage;

import cinema.Cinemas;
import cinema.Provoles;
import cinema.Films;
import users.Admins;
import users.ContentAdmins;
import users.Customers;
import users.Users;

import java.io.*;
import java.util.InputMismatchException;
import java.util.List;
import java.util.ArrayList;


public class MainClass implements Serializable {
    //CONTENTADMIN - NAME: contentAdmin - PASSWORD: ImContentAdmin123
    //ADMIN - NAME: admin - PASSWORD: ImAdmin123

    private static String name,username,password;

    private static List<Users> users = new ArrayList<>();
    private static List<Films> films = new ArrayList<>();
    private static final Cinemas[] cinemas = new Cinemas[10];
    private static List<Provoles> provoles = new ArrayList<>();

    public static void main(String[] args) {
        loadUsers();
        loadFilms();
        loadProvoles();
        cinemaTheaters();
        String userAnswerExitApp;
        do{
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            showLogin(reader);
            Users user = handleLogin(name,username,password);
            user.login();
            user.showUserMenu();
            int userOption;
            users.add(user);
            if (user instanceof Customers){
            	
                while(true){
                    try {
                        System.out.print("Please pick an Option: ");
                        userOption = Integer.parseInt(reader.readLine());
                        if(userOption<1 || userOption>4){
                            System.out.println("Enter a valid Value Between (1-4)");
                            continue;
                        }
                    } catch (IOException e) {
                        //error while getting input
                        continue;
                    } catch(NumberFormatException e) {
                        System.out.println("Enter an integer value Between (1-4)");
                        continue;
                    }
                    switch(userOption){
                        case 1:

                            ((Customers) user).nowPlayingFilms(films);
                            break;
                        case 2:

                            ((Customers) user).makeReservation(provoles);
                            break;
                        case 3:

                            ((Customers) user).viewReservation();
                            break;
                        case 4:
                            user.logout();
                            break;
                    }
                    if(userOption==4){
                        break;
                    }
                    user.showUserMenu();
                }
            }

            else if (user instanceof ContentAdmins){
                while(true){
                    try {
                        System.out.print("Pick Action: ");
                        userOption = Integer.parseInt(reader.readLine());
                        if(userOption<1 || userOption>4){
                            System.out.println("Enter a valid Value Between (1-4)");
                            continue;
                        }
                    } catch (IOException e) {
                        //error while getting input
                        continue;
                    } catch(NumberFormatException e) {
                        System.out.println("Enter an integer value Between (1-4)");
                        continue;
                    }
                    switch(userOption){
                        case 1:
                            ((ContentAdmins) user).insertFilm(films);
                            break;
                        case 2:
                            ((ContentAdmins) user).deleteFilm(films);
                            break;
                        case 3:
                            ((ContentAdmins) user).assignFilmToCinema(films,cinemas,provoles);
                            break;
                        case 4:
                            user.logout();
                            break;
                    }
                    if(userOption==4){
                        break;
                    }
                    user.showUserMenu();
                }
            }

            else if (user instanceof Admins){
                while(true){
                    try {
                        System.out.print("Pick your Action: ");
                        userOption = Integer.parseInt(reader.readLine());
                        if(userOption<1 || userOption>7){
                            System.out.println("Enter a valid Value Between (1-7)");
                            continue;
                        }
                    } catch (IOException e) {
                        //error while getting input
                        continue;
                    } catch(NumberFormatException e) {
                        System.out.println("Enter an integer value Between (1-7)");
                        continue;
                    }
                    switch(userOption){
                        case 1:
                            users.add(((Admins) user).createUser());
                            break;
                        case 2:
                            ((Admins) user).updateUser(users);
                            break;
                        case 3:
                            ((Admins) user).deleteUser(users,user);
                            break;
                        case 4:
                            ((Admins) user).searchUser(users);
                            break;
                        case 5:
                            ((Admins) user).viewAllUsers(users);
                            break;
                        case 6:
                            users.add(((Admins) user).registerAdmin());
                            break;
                        case 7:
                            user.logout();
                            break;
                    }
                    if(userOption==7){
                        break;
                    }
                    user.showUserMenu();
                }
            }

            else {
                System.out.println("User " + user.getName() + " is nothing");
            }
            System.out.print("Do you want to exit Cinema Application? (yes/no): ");
            while (true) {
                try {
                    userAnswerExitApp = reader.readLine();
                    if (userAnswerExitApp.equalsIgnoreCase("yes")) {
                        saveUsers();
                        saveFilms();
                        saveProvoles();
                        System.exit(0);
                    } else if (userAnswerExitApp.equalsIgnoreCase("no")) {
                        System.out.println("Redirecting to Login page...");
                        break;
                    } else {
                        System.out.print("Wrong input, please type (yes) or (no): ");
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }catch (RuntimeException e){
                    System.exit(1);
                }
            }
        }while(true);
    }

    private static void showLogin(BufferedReader reader) {
        String input;
        System.out.println("*****\nWelcome, please Login to continue.");
        System.out.print("Enter your name: ");
        while(true){
            try {
                input = reader.readLine();
                if (input.isEmpty()){
                    throw new IllegalArgumentException();
                }else if (input.length()<3 || input.length()>20){
                    throw new IllegalArgumentException();
                }else if (!input.matches("[a-zA-Z]+")){
                    throw new IllegalArgumentException();
                }
                else{
                    name = input;
                    break;
                }
            }catch (IOException e){
                System.out.print("Error in Name: ");
            }catch (NumberFormatException e){
                System.out.print("Wrong input. ");
            }catch (IllegalArgumentException e){
                System.out.print("Please enter name that is 3-20 Characters: ");
            }
        }
        System.out.print("Enter your Username: ");
        while(true){
            try {
                input = reader.readLine();
                if (input.isEmpty()){
                    throw new IllegalArgumentException();
                }else if (input.length()<5 || input.length()>20){
                    throw new IllegalArgumentException();
                }else if (!input.matches("^[a-zA-Z]+[0-9]*$")){
                    throw new IllegalArgumentException();
                }
                else{
                    username = input;
                    break;
                }
            }catch (IOException e){
                System.out.print("Error in Username: ");
            }catch (NumberFormatException e){
                System.out.print("Wrong input. ");
            }catch (IllegalArgumentException e){
                System.out.print("Please enter Username that is 5-20 Characters including Numbers(in the end of username): ");
            }
        }

        System.out.print("Enter your Password: ");
        while(true){
            try {
                input = reader.readLine();
                if (input.isEmpty()){
                    throw new IllegalArgumentException();
                }else if (input.length()<8 || input.length()>20){
                    throw new IllegalArgumentException();
                }else if (!input.matches("^(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")){
                    throw new IllegalArgumentException();
                }
                else{
                    password = input;
                    break;
                }
            }catch (IOException e){
                System.out.print("Error in Password: ");
            }catch (NumberFormatException e){
                System.out.print("Wrong input. ");
            }catch (IllegalArgumentException e){
                System.out.print("Password must have at least 8 characters, including one uppercase letter and one digit: ");
            }
        }

    }
    private static Users handleLogin(String name,String username,String password){
        if(password.equals("ImContentAdmin123")){
            return new ContentAdmins(name,username,password);
        }else if(password.equals("ImAdmin123")){
            return new Admins(name,username,password);
        }else {
            return new Customers(name,username,password);
        }
    }

    private static void cinemaTheaters(){
        cinemas[0] = new Cinemas("CINEMA 1",150,1,false);
        cinemas[1] = new Cinemas("CINEMA 2", 150,2,false);
        cinemas[2] = new Cinemas("CINEMA 3",200,3,false);
        cinemas[3] = new Cinemas("CINEMA 4", 200,4,false);
        cinemas[4] = new Cinemas("CINEMA 5",250,5,false);
        cinemas[5] = new Cinemas("CINEMA 6", 250,6,false);
        cinemas[6] = new Cinemas("CINEMA 7",250,7,false);
        cinemas[7] = new Cinemas("CINEMA 8", 200,8,true);
        cinemas[8] = new Cinemas("CINEMA 9", 250,9,true);
        cinemas[9] = new Cinemas("CINEMA 10", 300,10,true);

    }


    private static List<Users> loadUsers() {
        File file = new File("users.txt");
        if (file.exists() && file.length() > 0) {
            try {
                FileInputStream fileIn = new FileInputStream("users.txt");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                users = (List<Users>) in.readObject();
                in.close();
                fileIn.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return users;
    }



    private static void saveUsers(){
    	try {
    	    FileOutputStream fileOut = new FileOutputStream("users.txt");
    	    ObjectOutputStream out = new ObjectOutputStream(fileOut);
    	    out.writeObject(users);
    	    out.close();
    	    fileOut.close();
    	    System.out.println("Serialized data is saved in users.txt");
    	} catch (IOException e) {
    	    e.printStackTrace();
    	}
    }
    
    private static List<Users> loadFilms() {

        File file = new File("films.txt");
        if (file.exists() && file.length() > 0) {
            try {
                FileInputStream fileIn = new FileInputStream("films.txt");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                films = (List<Films>) in.readObject();
                in.close();
                fileIn.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    private static void saveFilms(){
    	try {
    	    FileOutputStream fileOut = new FileOutputStream("films.txt");
    	    ObjectOutputStream out = new ObjectOutputStream(fileOut);
    	    out.writeObject(films);
    	    out.close();
    	    fileOut.close();
    	    System.out.println("Serialized data is saved in films.txt");
    	} catch (IOException e) {
    	    e.printStackTrace();
    	}

    }
    private static List<Users> loadProvoles() {

        File file = new File("provoles.txt");
        if (file.exists() && file.length() > 0) {
            try {
                FileInputStream fileIn = new FileInputStream("provoles.txt");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                provoles = (List<Provoles>) in.readObject();
                in.close();
                fileIn.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return users;
    }



    private static void saveProvoles(){
    	try {
    	    FileOutputStream fileOut = new FileOutputStream("provoles.txt");
    	    ObjectOutputStream out = new ObjectOutputStream(fileOut);
    	    out.writeObject(provoles);
    	    out.close();
    	    fileOut.close();
    	    System.out.println("Serialized data is saved in provoles.txt");
    	} catch (IOException e) {
    	    e.printStackTrace();
    	}

    }
   
}