package mainpackage;

import cinema.Cinemas;
import cinema.Provoles;
import cinema.Films;
import users.Admins;
import users.ContentAdmins;
import users.Customers;
import users.Users;

import java.io.*;
import java.nio.Buffer;
import java.util.InputMismatchException;
import java.util.List;
import java.util.ArrayList;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;


//CONTENTADMIN - NAME: contentAdmin - PASSWORD: ImContentAdmin123
//ADMIN - NAME: admin - PASSWORD: ImAdmin123

public class MainClass implements Serializable {
    private static String name, username, password;
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
        do {
            Users user = null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("1.Login\t2.Register");
            int loginRegisterOption;
            System.out.print("Do you want to Login (1) or Register (2): ");
            while (true) {
                try {
                    loginRegisterOption = Integer.parseInt(reader.readLine());
                    if (loginRegisterOption == 1 || loginRegisterOption == 2) {
                        break;
                    } else {
                        System.out.print("Enter a valid Option (1) or (2): ");
                    }
                } catch (IOException e) {
                    System.out.println("Error in input.");
                } catch (InputMismatchException e) {
                    System.out.print("Enter a valid input (1) or (2): ");
                } catch (NumberFormatException e) {
                    System.out.print("Enter a valid Integer input (1) or (2): ");
                }
            }
            switch (loginRegisterOption) {
                case 1:
                    loginUser(reader);
                    user = handleLogin(username,password,users);
                    break;
                case 2:
                    registerUser(reader);
                    user = handleRegister(name, username, password);
                    users.add(user);
                    break;
            }
            user.login();
            user.showUserMenu();
            int userOption;
            if (user instanceof Customers) {
                while (true) {
                    try {
                        System.out.print("Please pick an Option: ");
                        userOption = Integer.parseInt(reader.readLine());
                        if (userOption < 1 || userOption > 4) {
                            System.out.println("Enter a valid Value Between (1-4)");
                            continue;
                        }
                    } catch (IOException e) {
                        //error while getting input
                        continue;
                    } catch (NumberFormatException e) {
                        System.out.println("Enter an integer value Between (1-4)");
                        continue;
                    }
                    switch (userOption) {
                        case 1:
                            System.out.println("You picked Option 1. Now Playing Films");
                            ((Customers) user).nowPlayingFilms(provoles);
                            break;
                        case 2:
                            System.out.println("You picked Option 2. Make a Reservation");
                            ((Customers) user).makeReservation(provoles, user);
                            break;
                        case 3:
                            System.out.println("You picked Option 3. View your Reservation");
                            ((Customers) user).viewReservations(user);
                            break;
                        case 4:
                            user.logout();
                            break;
                    }
                    if (userOption == 4) {
                        break;
                    }
                    user.showUserMenu();
                }
            } else if (user instanceof ContentAdmins) {
                while (true) {
                    try {
                        System.out.print("Pick Action: ");
                        userOption = Integer.parseInt(reader.readLine());
                        if (userOption < 1 || userOption > 4) {
                            System.out.println("Enter a valid Value Between (1-4)");
                            continue;
                        }
                    } catch (IOException e) {
                        //error while getting input
                        continue;
                    } catch (NumberFormatException e) {
                        System.out.println("Enter an integer value Between (1-4)");
                        continue;
                    }
                    switch (userOption) {
                        case 1:
                            ((ContentAdmins) user).insertFilm(films);
                            break;
                        case 2:
                            System.out.println("WORKING ON THAT");
                            //((ContentAdmins) user).deleteFilm(films,provoles,users);
                            break;
                        case 3:
                            ((ContentAdmins) user).assignFilmToCinema(films, cinemas, provoles);
                            break;
                        case 4:
                            user.logout();
                            break;
                    }
                    if (userOption == 4) {
                        break;
                    }
                    user.showUserMenu();
                }
            } else if (user instanceof Admins) {
                while (true) {
                    try {
                        System.out.print("Pick your Action: ");
                        userOption = Integer.parseInt(reader.readLine());
                        if (userOption < 1 || userOption > 7) {
                            System.out.println("Enter a valid Value Between (1-7)");
                            continue;
                        }
                    } catch (IOException e) {
                        //error while getting input
                        continue;
                    } catch (NumberFormatException e) {
                        System.out.println("Enter an integer value Between (1-7)");
                        continue;
                    }
                    switch (userOption) {
                        case 1:
                            users.add(((Admins) user).createUser());
                            break;
                        case 2:
                            ((Admins) user).updateUser(users);
                            break;
                        case 3:
                            ((Admins) user).deleteUser(users, user);
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
                    if (userOption == 7) {
                        break;
                    }
                    user.showUserMenu();
                }
            } else {
                System.out.println(user.getName() + " isn't a User");
            }
            System.out.print("Do you want to exit Cinema Application? (yes/no): ");
            while (true) {
                try {
                    userAnswerExitApp = reader.readLine();
                    if (userAnswerExitApp.equalsIgnoreCase("yes")) {
                        saveUsers(users);
                        saveFilms(films);
                        saveProvoles(provoles);
                        System.exit(0);
                    } else if (userAnswerExitApp.equalsIgnoreCase("no")) {
                        System.out.println("Redirecting to Main page...");
                        break;
                    } else {
                        System.out.print("Wrong input, please type (yes) or (no): ");
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (RuntimeException e) {
                    System.exit(1);
                }
            }
        } while (true);
    }

    private static void loginUser(BufferedReader reader) {
        String input;
        System.out.println("*****\nWelcome, please Login to continue.");
        System.out.print("Enter your Username: ");
        while (true) {
            try {
                input = reader.readLine();
                if (input.isEmpty()) {
                    throw new IllegalArgumentException();
                } else if (input.length() < 5 || input.length() > 20) {
                    throw new IllegalArgumentException();
                } else if (!input.matches("^[a-zA-Z]+[0-9]*$")) {
                    throw new IllegalArgumentException();
                } else {
                    username = input;
                    System.out.print("Enter your Password: ");
                    while (true) {
                        try {
                            input = reader.readLine();
                            if (input.isEmpty()) {
                                throw new IllegalArgumentException();
                            } else if (input.length() < 8 || input.length() > 20) {
                                throw new IllegalArgumentException();
                            } else if (!input.matches("^(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")) {
                                throw new IllegalArgumentException();
                            } else {
                                password = input;
                                break;
                            }
                        } catch (IOException e) {
                            System.out.print("Error in Password: ");
                        } catch (NumberFormatException e) {
                            System.out.print("Wrong input. ");
                        } catch (IllegalArgumentException e) {
                            System.out.print("Password must have at least 8 characters, including one uppercase letter and one digit: ");
                        }
                    }
                    break;
                }
            } catch (IOException e) {
                System.out.print("Error in Username: ");
            } catch (NumberFormatException e) {
                System.out.print("Wrong input. ");
            } catch (IllegalArgumentException e) {
                System.out.print("Please enter Username that is 5-20 Characters including Numbers(in the end of username): ");
            }
        }

    }

    private static Users handleLogin(String inputUsername, String inputPassword, List<Users> inputUsers) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        for (Users user : inputUsers) {
            if (user.getUsername().equals(inputUsername) && user.getPassword().equals(inputPassword)) {
                return user;
            }else if (user.getUsername().equals(inputUsername) && !user.getPassword().equals(inputPassword)){
                System.out.print("Wrong password. Would you like to Try again (1), or Register (2): ");
                int option;

                while (true) {
                    try {
                        option = Integer.parseInt(reader.readLine());
                        if (option == 1 || option == 2) {
                            break;
                        } else {
                            System.out.print("Enter a valid Option (1) or (2): ");
                        }
                    } catch (IOException e) {
                        System.out.println("Error in input.");
                    } catch (InputMismatchException e) {
                        System.out.print("Enter a valid input (1) or (2): ");
                    } catch (NumberFormatException e) {
                        System.out.print("Enter a valid Integer input (1) or (2): ");
                    }
                }
                user = null;
                switch (option) {
                    case 1:
                        loginUser(reader);
                        user = handleLogin(username, password,users);
                        return user;
                    case 2:
                        registerUser(reader);
                        user = handleRegister(name, username, password);
                        users.add(user);
                        return user;
                }
            }
        }


        //only if user not found
        Users user = null;
        System.out.print("User not Found, please Enter the name of new User: ");
        while (true) {
            try {
                name = reader.readLine();
                if (name.isEmpty()) {
                    throw new IllegalArgumentException();
                } else if (name.length() < 3 || name.length() > 20) {
                    throw new IllegalArgumentException();
                } else if (!name.matches("[a-zA-Z]+")) {
                    throw new IllegalArgumentException();
                } else {
                    break;
                }
            } catch (IOException e) {
                System.out.print("Error in Name: ");
            } catch (NumberFormatException e) {
                System.out.print("Wrong input. ");
            } catch (IllegalArgumentException e) {
                System.out.print("Please enter name that is 3-20 Characters: ");
            }
        }
        System.out.print("Enter Password of New User: ");
        while (true) {
            try {
                password = reader.readLine();
                if (password.isEmpty()) {
                    throw new IllegalArgumentException();
                } else if (password.length() < 8 || password.length() > 20) {
                    throw new IllegalArgumentException();
                } else if (!password.matches("^(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")) {
                    throw new IllegalArgumentException();
                } else {
                    break;
                }
            } catch (IOException e) {
                System.out.print("Error in Password: ");
            } catch (NumberFormatException e) {
                System.out.print("Wrong input. ");
            } catch (IllegalArgumentException e) {
                System.out.print("Password must have at least 8 characters, including one uppercase letter and one digit: ");
            }
        }
        if (password.equals("ImContentAdmin123")) {
            user = new ContentAdmins(name, username, password);
        } else if (password.equals("ImAdmin123")) {
            user = new Admins(name, username, password);
        } else {
            user = new Customers(name, username, password);
        }
        users.add(user);
        return user;
    }


    private static void registerUser(BufferedReader reader) {
        String input;
        System.out.println("*****\nWelcome, please Register to continue.");
        System.out.print("Enter your name: ");
        while (true) {
            try {
                input = reader.readLine();
                if (input.isEmpty()) {
                    throw new IllegalArgumentException();
                } else if (input.length() < 3 || input.length() > 20) {
                    throw new IllegalArgumentException();
                } else if (!input.matches("[a-zA-Z]+")) {
                    throw new IllegalArgumentException();
                } else {
                    name = input;
                    break;
                }
            } catch (IOException e) {
                System.out.print("Error in Name: ");
            } catch (NumberFormatException e) {
                System.out.print("Wrong input. ");
            } catch (IllegalArgumentException e) {
                System.out.print("Please enter name that is 3-20 Characters: ");
            }
        }
        System.out.print("Enter your Username: ");
        while (true) {
            boolean usernameExists = false;
            try {
                input = reader.readLine();
                if (input.isEmpty()) {
                    throw new IllegalArgumentException();
                } else if (input.length() < 5 || input.length() > 20) {
                    throw new IllegalArgumentException();
                } else if (!input.matches("^[a-zA-Z]+[0-9]*$")) {
                    throw new IllegalArgumentException();
                }
                for (Users user : users) {
                    if (input.equals(user.getUsername())) {
                        usernameExists = true;
                        System.out.println("Username Already Registered.");
                    }
                }
                if (!usernameExists) {
                    username = input;
                    break;
                } else {
                    System.out.print("Enter your Username: ");
                }
            } catch (IOException e) {
                System.out.print("Error in Username: ");
            } catch (NumberFormatException e) {
                System.out.print("Wrong input. ");
            } catch (IllegalArgumentException e) {
                System.out.print("Please enter Username that is 5-20 Characters including Numbers(in the end of username): ");
            }
        }
        System.out.print("Enter your Password: ");
        while (true) {
            try {
                input = reader.readLine();
                if (input.isEmpty()) {
                    throw new IllegalArgumentException();
                } else if (input.length() < 8 || input.length() > 20) {
                    throw new IllegalArgumentException();
                } else if (!input.matches("^(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")) {
                    throw new IllegalArgumentException();
                } else {
                    password = input;
                    break;
                }
            } catch (IOException e) {
                System.out.print("Error in Password: ");
            } catch (NumberFormatException e) {
                System.out.print("Wrong input. ");
            } catch (IllegalArgumentException e) {
                System.out.print("Password must have at least 8 characters, including one uppercase letter and one digit: ");
            }
        }
    }
    private static Users handleRegister(String name, String username, String password) {
        if (password.equals("ImContentAdmin123")) {
            return new ContentAdmins(name, username, password);
        } else if (password.equals("ImAdmin123")) {
            return new Admins(name, username, password);
        } else {
            return new Customers(name, username, password);
        }
    }

    private static void cinemaTheaters() {
        cinemas[0] = new Cinemas("CINEMA 1", 150, 1, false);
        cinemas[1] = new Cinemas("CINEMA 2", 150, 2, false);
        cinemas[2] = new Cinemas("CINEMA 3", 200, 3, false);
        cinemas[3] = new Cinemas("CINEMA 4", 200, 4, false);
        cinemas[4] = new Cinemas("CINEMA 5", 250, 5, false);
        cinemas[5] = new Cinemas("CINEMA 6", 250, 6, false);
        cinemas[6] = new Cinemas("CINEMA 7", 250, 7, false);
        cinemas[7] = new Cinemas("CINEMA 8", 200, 8, true);
        cinemas[8] = new Cinemas("CINEMA 9", 250, 9, true);
        cinemas[9] = new Cinemas("CINEMA 10", 300, 10, true);

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
            } catch (IOException | ClassNotFoundException e){
                e.printStackTrace();
            }
        }
        return users;
    }
    private static void saveUsers(List<Users> users) {
        try {
            FileOutputStream fileOut = new FileOutputStream("users.txt");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(users);
            out.close();
            fileOut.close();
            //System.out.println("-Users have been saved to users.txt-");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Films> loadFilms(){
        File file = new File("films.txt");
        if (file.exists() && file.length() > 0) {
            try {
                FileInputStream fileIn = new FileInputStream("films.txt");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                films = (List<Films>) in.readObject();
                in.close();
                fileIn.close();
            } catch (IOException | ClassNotFoundException e){
                e.printStackTrace();
            }
        }
        return films;
    }
    private static void saveFilms(List<Films> films){
        try {
            FileOutputStream fileOut = new FileOutputStream("films.txt");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(films);
            out.close();
            fileOut.close();
            //System.out.println("-Films have been saved to films.txt-");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Provoles> loadProvoles(){
        File file = new File("provoles.txt");
        if (file.exists() && file.length() > 0) {
            try {
                FileInputStream fileIn = new FileInputStream("provoles.txt");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                provoles = (List<Provoles>) in.readObject();
                in.close();
                fileIn.close();
            } catch (IOException | ClassNotFoundException e){
                e.printStackTrace();
            }
        }
        return provoles;
    }

    private static void saveProvoles(List<Provoles> provoles){
        try {
            FileOutputStream fileOut = new FileOutputStream("provoles.txt");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(provoles);
            out.close();
            fileOut.close();
            //System.out.println("-Provoles have been saved to provoles.txt-");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
