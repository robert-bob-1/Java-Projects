package Model;

import java.io.Serializable;
import java.util.ArrayList;

//enum roles{ ADMIN, CLIENT }
public class User implements Serializable {
    public String username;
    public String password;
    public String role;
    public ArrayList<Order> ordersMade = new ArrayList<>();
    public User(){}

    public User(String username, String pass, String role){
        this.password = pass;
        this.username = username;
        this.role = role;
    }
    public static void main(String[] args){

    }
}
