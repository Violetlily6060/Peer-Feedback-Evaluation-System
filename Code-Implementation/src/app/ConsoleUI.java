package app;

import domain.*;

import java.util.Scanner;

public class ConsoleUI {
    private static Controller controller = new Controller();
    private static Scanner input = new Scanner(System.in);
    private static boolean logged = false;
    private static IUser currentUser = null;

    public static void main(String[] args) {
        while (true) {
            login();

            if (currentUser instanceof Student) {
                studentMenu();
            } else if (currentUser instanceof Lecturer) {
                lecturerMenu();
            } else {
                adminMenu();
            }
        }
    }

    private static void login() {
        boolean invalid = false;

        while (!logged) {
            clearScreen();
            hardline();
            System.out.println("Peer Feedback Evaluain Menu");
            hardline();
            System.out.println("Please enter your credentials to login");
            softline();
            if (invalid) {
                System.out.println("INCORRECT USERNAME OR PASSWORD, PLEASE TRY AGAIN");
            } else {
                System.out.println();
            }
            System.out.print("User ID: ");
            String userID = input.nextLine();
            System.out.print("Password: ");
            String password = input.nextLine();

            currentUser = controller.validateLogin(userID, password);
            if (currentUser == null) {
                invalid = true;
            } else {
                invalid = false;
                logged = true;
            }
        }
    }

    private static void studentMenu() {
        int choice = 0;
        boolean invalid = false;

        clearScreen();
        do {
            hardline();
            System.out.println("Peer Feedback Evaluation Student Main Menu");
            hardline();
            System.out.println("What would you like to do?");
            softline();
            System.out.println("1. Manage Profile");
            System.out.println("2. Submit Peer Feedback Evaluation");
            System.out.println("3. View Feedback");
            System.out.println("4. Logout");
            softline();
            if (invalid)
                System.out.println("INVALID INPUT, PLEASE PICK BETWEEN 1 TO 4");
            else
                System.out.println();

            System.out.print("Choice (1-4): ");
            try {
                choice = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                choice = 0;
            }

            if (choice > 0 && choice < 5) {
                invalid = false;
                switch (choice) {
                    case 1:
                        managePersonalProfile(currentUser);
                        break;
                    case 2:
                        submitEvaluation();
                        break;
                    case 3:
                        viewEvaluation();
                        break;
                }
            } else {
                invalid = true;
            }
        } while (choice != 4);

        logged = false;
        currentUser = null;
    }

    private static void lecturerMenu() {
        int choice = 0;
        boolean invalid = false;

        clearScreen();
        do {
            hardline();
            System.out.println("Peer Feedback Evaluation Lecturer Main Menu");
            hardline();
            System.out.println("What would you like to do?");
            softline();
            System.out.println("1. Manage Profile");
            System.out.println("2. Create Evaluation Activity");
            System.out.println("3. Generate Feedback Report");
            System.out.println("4. Logout");
            softline();
            if (invalid)
                System.out.println("INVALID INPUT, PLEASE PICK BETWEEN 1 TO 4");
            else
                System.out.println();

            System.out.print("Choice (1-4): ");
            try {
                choice = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                choice = 0;
            }

            if (choice > 0 && choice < 5) {
                invalid = false;
                switch (choice) {
                    case 1:
                        managePersonalProfile(currentUser);
                        break;
                    case 2:
                        createEvaluationActivity();
                        break;
                    case 3:
                        generateFeebackReport();
                        break;
                }
            } else {
                invalid = true;
            }
        } while (choice != 4);

        logged = false;
        currentUser = null;
    }

    private static void adminMenu() {
        int choice = 0;
        boolean invalid = false;

        clearScreen();
        do {
            hardline();
            System.out.println("Peer Feedback Evaluation Administrator Main Menu");
            hardline();
            System.out.println("What would you like to do?");
            softline();
            System.out.println("1. Manage All Profile");
            System.out.println("2. Create Account");
            System.out.println("3. Generate Feedback Report");
            System.out.println("4. Logout");
            softline();
            if (invalid)
                System.out.println("INVALID INPUT, PLEASE PICK BETWEEN 1 TO 4");
            else
                System.out.println();

            System.out.print("Choice (1-4): ");
            try {
                choice = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                choice = 0;
            }

            if (choice > 0 && choice < 5) {
                invalid = false;
                switch (choice) {
                    case 1:
                        manageAllProfile();
                        break;
                    case 2:
                        createAccount();
                        break;
                    case 3:
                        generateFeebackReport();
                        break;
                }
            } else {
                invalid = true;
            }
        } while (choice != 4);

        logged = false;
        currentUser = null;
    }

    private static void managePersonalProfile(IUser user) {
        int choice = 0;
        boolean invalid = false;

        do {
            clearScreen();
            hardline();
            System.out.println("Manage Profile for " + user.getName());
            hardline();
            System.out.println("What would you like to do?");
            softline();
            System.out.println("1. Change Username");
            System.out.println("2. Change Password");
            System.out.println("3. Go To Main Menu");
            softline();
            if (invalid) {
                System.out.println("INVALID INPUT, PLEASE PICK BETWEEN 1 TO 3");
            } else {
                System.out.println();
            }

            System.out.print("Choice (1-3): ");
            try {
                choice = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                choice = 0;
            }

            if (choice > 0 && choice < 4) {
                invalid = false;
                switch (choice) {
                    case 1:
                        changeUsername(user);
                        break;
                    case 2:
                        changePassword(user);
                        break;
                }
            } else {
                invalid = true;
            }
        } while (choice != 3);
    }

    private static void manageAllProfile() {

    }

    private static void changeUsername(IUser user) {
        String newName = null;

        clearScreen();
        hardline();
        System.out.println("Changing Username for " + user.getName());
        hardline();
        System.out.println("Please enter a new name");
        softline();
        System.out.print("New Name: ");
        newName = input.nextLine();

        controller.updateUser(user.getRole(), user.getUserID(), user.getPassword(), newName);

        clearScreen();
        hardline();
        System.out.println("Username changed to " + newName + " successfully");
        hardline();
        System.out.print("Type anything to continue: ");
        input.nextLine();
    }

    private static void changePassword(IUser user) {
        String newPassword = null;

        clearScreen();
        hardline();
        System.out.println("Changing Password for " + user.getName());
        hardline();
        System.out.println("Please enter a new password");
        softline();
        System.out.print("New password: ");
        newPassword = input.nextLine();

        controller.updateUser(user.getRole(), user.getUserID(), newPassword, user.getName());

        clearScreen();
        hardline();
        System.out.println("Password changed successfully");
        hardline();
        System.out.print("Type anything to continue: ");
        input.nextLine();
    }

    private static void submitEvaluation() {

    }

    private static void viewEvaluation() {

    }

    private static void createEvaluationActivity() {

    }

    private static void generateFeebackReport() {

    }

    private static void createAccount() {

    }

    private static void updateFeedback() {

    }

    // console manipulation and decoration methods
    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void softline() {
        System.out.println("--------------------------------------------------");
    }

    private static void hardline() {
        System.out.println("==================================================");
    }
}