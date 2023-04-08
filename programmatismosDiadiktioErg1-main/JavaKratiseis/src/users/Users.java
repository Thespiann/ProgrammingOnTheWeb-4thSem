package users;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public abstract class Users implements Serializable {

    private String name,username,password;
    private boolean loggedIn = false;
    public Users(){

    }

    public void setName(String name) { this.name = name;}
    public void setUsername(String username) { this.username = username;}
    public void setPassword(String password){this.password = password;}
    public void setLoggedIn(boolean loggedIn) { this.loggedIn = true;}

    public String getName(){
        return this.name;
    }
    public String getUsername(){
        return this.username;
    }
    public String getPassword(){
        return this.password;
    }
    public boolean isLoggedIn() { return this.loggedIn;}

    public abstract void showUserMenu();

    public abstract void login();

    public abstract void logout();

}
