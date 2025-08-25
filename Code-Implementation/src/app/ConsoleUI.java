package app;

import domain.*;

public class ConsoleUI {
    public static void main(String[] args) throws Exception {
        Controller controller = new Controller();
        controller.addUser("student", "yes", "yes", "yes"); 
    }
}
