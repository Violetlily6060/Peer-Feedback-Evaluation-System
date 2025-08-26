package app;

import domain.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class ConsoleUI {
    private static Controller controller = new Controller();
    private static Scanner input = new Scanner(System.in);
    private static boolean logged = false;
    private static IUser currentUser = null;

    public static void main(String[] args) {
        if (controller.getUserList("administrator").isEmpty()) {
            controller.addUser("administrator", "a1", "admin", "admin");
        }

        while (true) {
            login();

            if (currentUser instanceof Student) {
                studentMenu();
            } else if (currentUser instanceof Lecturer) {
                lecturerMenu();
            } else if (currentUser instanceof Administrator) {
                adminMenu();
            } else {
                break;
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
            System.out.println("enter \"q\" for ID and Password to quit system");
            if (invalid) {
                System.out.println("INCORRECT USERNAME OR PASSWORD, PLEASE TRY AGAIN");
            } else {
                System.out.println();
            }
            System.out.print("User ID: ");
            String userID = input.nextLine();
            System.out.print("Password: ");
            String password = input.nextLine();

            if (userID.equals("q") && password.equals("q")) {
                break;
            }

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

        do {
            clearScreen();
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

        do {
            clearScreen();
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

        do {
            clearScreen();
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
            System.out.println("3. Go Back");
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

    private static void manageProfileByAdmin(IUser user) {
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
            System.out.println("3. Delete Profile");
            System.out.println("3. Go Back");
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
                    case 3:
                        deleteProfile(user);
                }
            } else {
                invalid = true;
            }
        } while (choice != 3);
    }

    private static void manageAllProfile() {
        int choice = 0;
        boolean invalid = false;

        do {
            clearScreen();
            hardline();
            System.out.println("Manage All Profile");
            hardline();
            System.out.println("What would you like to do?");
            softline();
            System.out.println("1. Manage Personal Profile");
            System.out.println("2. Manage Other's Profile");
            System.out.println("3. Go Back");
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
                        managePersonalProfile(currentUser);
                        break;

                    case 2:
                        manageOthersProfile();
                        break;
                }
            }
        } while (choice != 3);
    }

    private static void manageOthersProfile() {
        List<IUser> users = controller.getUserList("all");
        boolean invalid = false;

        while (true) {
            clearScreen();
            hardline();
            System.out.println("Searching Profile to Manage");
            hardline();
            System.out.println("Enter the profile user ID");
            softline();
            System.out.println("[Enter \"q\" to go back]");
            if (invalid) {
                System.out.println("INVALID INPUT, PLEASE ENTER AN EXISTING USER ID");
            } else {
                System.out.println();
            }

            System.out.print("User ID: ");
            String userID = input.nextLine();

            if (userID.equals("q")) {
                return;
            }

            for (IUser u : users) {
                if (u.getUserID().equals(userID)) {
                    manageProfileByAdmin(u);
                    return;
                }
            }

            invalid = true;
        }
    }

    private static void changeUsername(IUser user) {
        String newName = null;

        clearScreen();
        hardline();
        System.out.println("Changing Username for " + user.getName());
        hardline();
        System.out.println("Please enter a new name");
        System.out.println("[enter \"q\" to go back]");
        softline();
        System.out.print("New Name: ");
        newName = input.nextLine();

        if (newName.equals("q")) {
            return;
        }

        controller.updateUser(user.getRole(), user.getUserID(), user.getPassword(), newName);

        clearScreen();
        hardline();
        System.out.println("Username changed to " + newName + " successfully");
        hardline();
        System.out.print("(Press ENTER to continue)");
        input.nextLine();
    }

    private static void changePassword(IUser user) {
        String newPassword = null;

        clearScreen();
        hardline();
        System.out.println("Changing Password for " + user.getName());
        hardline();
        System.out.println("Please enter a new password");
        System.out.println("[enter \"q\" to go back]");
        softline();
        System.out.print("New Password: ");
        newPassword = input.nextLine();

        if (newPassword.equals("q")) {
            return;
        }

        controller.updateUser(user.getRole(), user.getUserID(), newPassword, user.getName());

        clearScreen();
        hardline();
        System.out.println("Password changed successfully");
        hardline();
        System.out.print("(Press ENTER to continue)");
        input.nextLine();
    }

    private static void deleteProfile(IUser user) {
        boolean invalid = false;

        while (true) {
            clearScreen();
            hardline();
            System.out.println("Deleting " + user.getName() + "\'s Profile");
            hardline();
            System.out.println("Enter the profile user ID to confirm deletion");
            softline();
            System.out.println("[enter \"q\" to go back]");
            if (invalid) {
                System.out.println("INVALID INPUT, PLEASE ENTER THE CORRECT USER ID");
            } else {
                System.out.println();
            }

            System.out.print("Confirm Delete (User ID): ");
            String userID = input.nextLine();

            if (userID.equals("q")) {
                return;
            } else if (userID.equals(user.getUserID())) {
                controller.deleteUser(user.getRole(), user.getUserID());
                clearScreen();
                hardline();
                System.out.println("Profile Deleted Successfully");
                hardline();
                System.out.print("(Press ENTER to continue)");
                input.nextLine();
                return;
            }

            invalid = true;
        }
    }

    private static void submitEvaluation() {
        boolean invalid = false;
        List<EvaluationActivity> activities = controller.getActivityFilteredByCreator(currentUser.getUserID());

        do {
            clearScreen();
            for (EvaluationActivity a : activities) {
                System.out.println(a.getParticipantList());
            }
            hardline();
            System.out.println("Submitting Peer Feedback");
            hardline();
            System.out.println("Please select the peer feedback evaluation activity");
            System.out.println("[enter \"q\" to go back]");
            softline();
            for (EvaluationActivity a : activities) {
                System.out.printf("%-7s %s\n", a.getActivityID(), a.getName());
            }
            if (invalid) {
                System.out.println("INVALID INPUT, PLEASE ENTER AN EXISTING EVALUATION ACTIVITY");
            } else {
                System.out.println();
            }
            System.out.print("Activity ID: ");
            String activityID = input.nextLine();

            if (activityID.equals("q")) {
                return;
            }

            for (EvaluationActivity a : activities) {
                if (a.getActivityID().equals(activityID)) {
                    createFeedback(a);
                    invalid = false;
                    break;
                }
                invalid = true;
            }
        } while (true);
    }

    private static void createFeedback(EvaluationActivity activity) {
        boolean invalid = false;
        List<IUser> peers = controller.getParticipantExlude(activity.getActivityID(), currentUser.getUserID());

        if (peers.isEmpty()) {
            clearScreen();
            hardline();
            System.out.println("You have no more peers to give feedbacks");
            hardline();
            System.out.print("(Press ENTER to continue)");
            input.nextLine();
            return;
        }

        do {
            clearScreen();
            hardline();
            System.out.println("Submitting Peer Feedback");
            hardline();
            System.out.println("Please select the student to give feedback");
            System.out.println("[enter \"q\" to go back]");
            softline();
            for (IUser p : peers) {
                System.out.printf("%-5s %s\n", p.getUserID(), p.getName());
            }
            if (invalid) {
                System.out.println("INVALID INPUT, PLEASE ENTER AN EXISTING STUDENT");
            } else {
                System.out.println();
            }

            System.out.print("Student ID: ");
            String studentID = input.nextLine();

            if (studentID.equals("q")) {
                return;
            }

            for (IUser p : peers) {
                if (p.getUserID().equals(studentID)) {
                    System.out.println();
                    System.out.println("Enter your feedback below");
                    softline();
                    while (true) {
                        String feedbackContent = input.nextLine();

                        if (!feedbackContent.equals("")) {
                            controller.addFeedback(activity.getActivityID(),
                                    activity.getActivityID() + "-" + (activity.getFeedbackList().size() + 1),
                                    currentUser.getUserID(), studentID, LocalDate.now().toString(),
                                    LocalDate.now().toString(),
                                    feedbackContent);
                            invalid = false;
                            break;
                        }
                    }
                    break;
                }
                invalid = true;
            }
        } while (true);
    }

    private static void viewEvaluation() {

    }

    private static void createEvaluationActivity() {
        boolean invalid = false;

        clearScreen();
        hardline();
        System.out.println("Creating New Peer Feedback Evaluation Activity");
        hardline();
        System.out.println("Please enter the activity information");
        softline();

        System.out.print("Activity Name: ");
        String activityName = input.nextLine();
        System.out.println();

        List<EvaluationActivity> activities = controller.getActivityList();
        int numID = 1;
        if (!activities.isEmpty()) {
            numID = Integer.parseInt(activities.get(activities.size() - 1).getActivityID().substring(3)) + 1;
        }
        controller.addActivity("pfe" + numID, activityName, currentUser.getUserID());
        activities = controller.getActivityList();

        EvaluationActivity currentActivity = null;
        for (EvaluationActivity ea : activities) {
            if (ea.getActivityID().equals("pfe" + numID)) {
                currentActivity = ea;
            }
        }
        List<IUser> users = controller.getUserList("student");
        List<String> participants = new ArrayList<>();

        while (true) {
            clearScreen();
            hardline();
            System.out.println("Adding Participants to Activity");
            hardline();
            System.out.println("enter \"q\" to stop adding participants");
            softline();
            if (invalid) {
                System.out.println("STUDENT NOT FOUND / ALREADY A PARTICIPANT");
            } else {
                System.out.println();
            }
            System.out.print("Student ID: ");
            String userID = input.nextLine();

            if (participants.contains(userID)) {
                invalid = true;
                break;
            }

            if (userID.equals("q")) {
                break;
            }

            for (IUser u : users) {
                if (u.getUserID().equals(userID)) {
                    invalid = false;
                    currentActivity.addParticipant(u);
                    participants.add(userID);
                    break;
                } else {
                    invalid = true;
                }
            }
        }
    }

    private static void generateFeebackReport() {

    }

    private static void createAccount() {
        List<String> roles = new ArrayList<>();
        roles.add("student");
        roles.add("lecturer");
        roles.add("administrator");

        String role;
        String name;
        String password;
        boolean invalid = false;

        while (true) {
            clearScreen();
            hardline();
            System.out.println("Creating New Account");
            hardline();
            System.out.println("Please enter the information of the new profile");
            softline();
            if (invalid) {
                System.out.println("PLEASE ENTER A VALID USER ROLE");
            } else {
                System.out.println();
            }

            System.out.print("User Role (student, lecturer, administrator): ");
            role = input.nextLine().toLowerCase();

            if (roles.contains(role)) {
                System.out.print("Name: ");
                name = input.nextLine();
                System.out.print("Password:");
                password = input.nextLine();

                List<IUser> users = controller.getUserList(role);
                int numID = 1;
                String userID = null;

                if (!users.isEmpty()) {
                    numID = Integer.parseInt(users.get(users.size() - 1).getUserID().substring(1)) + 1;
                }

                switch (role) {
                    case "student":
                        userID = "s" + numID;
                        break;

                    case "lecturer":
                        userID = "l" + numID;
                        break;

                    case "administrator":
                        userID = "a" + numID;
                        break;
                }

                controller.addUser(role, userID, password, name);

                clearScreen();
                hardline();
                System.out.println("Account created for " + name);
                hardline();
                System.out.println("(Press ENTER to continue)");
                input.nextLine();
                return;
            } else {
                invalid = true;
            }
        }
    }

    private static void updateFeedback() {

    }

    // console manipulation and decoration methods
    private static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException ex) {
        }
    }

    private static void softline() {
        System.out.println("-----------------------------------------------------------------------");
    }

    private static void hardline() {
        System.out.println("=======================================================================");
    }
}