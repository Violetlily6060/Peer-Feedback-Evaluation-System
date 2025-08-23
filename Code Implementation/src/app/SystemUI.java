package app;

import domain.*;

public class SystemUI {
    public static void main(String[] args) throws Exception {
        User user = new User("1", "password", "John Doe", "john@example.com", "1234567890");
        System.out.println("User ID: " + user.getUserID());
        System.out.println("Name: " + user.getName());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Phone No: " + user.getPhoneNo());
    }
}
