package app;

import domain.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private final Controller controller = new Controller();
    private final Scanner input = new Scanner(System.in);
    private IUser currentUser = null;

    public static void main(String[] args) {
        ConsoleUI console = new ConsoleUI();

        // Page Sequence
        while (true) {
            IUser currentUser = console.login();

            if (currentUser instanceof Student) {
                console.studentMenu();
            } else if (currentUser instanceof Lecturer) {
                console.lecturerMenu();
            } else if (currentUser instanceof Administrator) {
                console.adminMenu();
            } else {
                break;
            }
        }
    }

    // Login Page
    private IUser login() {
        // Create New Administrator Account if no Accounts Exists
        if (controller.getUserList("administrator").isEmpty()) {
            controller.addUser("administrator", "a1", "admin", "admin");
        }
        boolean invalid = false;

        while (currentUser == null) {
            // User Interface
            header();
            System.out.println("Enter your credentials to login");
            softline();
            System.out.println("enter \"q\" for ID and Password to quit system");
            if (invalid) {
                System.out.println("INCORRECT USERNAME OR PASSWORD, PLEASE TRY AGAIN");
                invalid = false;
            } else {
                System.out.println();
            }

            // User Input
            System.out.print("User ID: ");
            String userID = input.nextLine();
            System.out.print("Password: ");
            String password = input.nextLine();

            // User Input Processing
            if (userID.toUpperCase().equals("Q") && password.toUpperCase().equals("Q")) {
                return null;
            } else {
                currentUser = controller.validateLogin(userID, password);
                if (currentUser == null) {
                    invalid = true;
                }
            }
        }
        return currentUser;
    }

    // Student Main Menu
    private void studentMenu() {
        int choice;
        boolean invalid = false;

        do {
            // User Interface
            header();
            System.out.println("What would you like to do?");
            softline();
            System.out.println("1. Manage Profile");
            System.out.println("2. Submit Peer Feedback Evaluation");
            System.out.println("3. View Feedback");
            System.out.println("4. Logout");
            softline();
            if (invalid) {
                System.out.println("INVALID INPUT, PLEASE PICK BETWEEN 1 TO 4");
                invalid = false;
            } else {
                System.out.println();
            }
            System.out.print("Choice (1-4): ");

            // User Input
            try {
                choice = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                choice = 0;
            }

            // User Input Processing
            if (choice > 0 && choice < 5) {
                switch (choice) {
                    case 1 -> managePersonalProfile(currentUser);
                    case 2 -> submitEvaluation();
                    case 3 -> viewEvaluation();
                }
            } else {
                invalid = true;
            }
        } while (choice != 4);

        // Log Out
        currentUser = null;
    }

    // Lecturer Main Menu
    private void lecturerMenu() {
        int choice;
        boolean invalid = false;

        do {
            // User Interface
            header();
            System.out.println("What would you like to do?");
            softline();
            System.out.println("1. Manage Profile");
            System.out.println("2. Create Evaluation Activity");
            System.out.println("3. Generate Feedback Report");
            System.out.println("4. Logout");
            softline();
            if (invalid) {
                System.out.println("INVALID INPUT, PLEASE PICK BETWEEN 1 TO 4");
                invalid = false;
            } else {
                System.out.println();
            }
            System.out.print("Choice (1-4): ");

            // User Input
            try {
                choice = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                choice = 0;
            }

            // User Input Processing
            if (choice > 0 && choice < 5) {
                switch (choice) {
                    case 1 -> managePersonalProfile(currentUser);
                    case 2 -> createEvaluationActivity();
                    case 3 -> viewEvaluation();
                }
            } else {
                invalid = true;
            }
        } while (choice != 4);

        // Log Out
        currentUser = null;
    }

    // Administrator Menu
    private void adminMenu() {
        int choice;
        boolean invalid = false;

        do {
            // User Interface
            header();
            System.out.println("What would you like to do?");
            softline();
            System.out.println("1. Manage All Profile");
            System.out.println("2. Create Account");
            System.out.println("3. Generate Feedback Report");
            System.out.println("4. Logout");
            softline();
            if (invalid) {
                System.out.println("INVALID INPUT, PLEASE PICK BETWEEN 1 TO 4");
                invalid = false;
            } else {
                System.out.println();
            }
            System.out.print("Choice (1-4): ");

            // User Input
            try {
                choice = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                choice = 0;
            }

            // User Input Processing
            if (choice > 0 && choice < 5) {
                switch (choice) {
                    case 1 -> manageAllProfile();
                    case 2 -> createAccount();
                    case 3 -> viewEvaluation();
                }
            } else {
                invalid = true;
            }
        } while (choice != 4);

        // Log Out
        currentUser = null;
    }

    // Managing Profile Menu
    private void managePersonalProfile(IUser user) {
        int choice;
        boolean invalid = false;

        do {
            // User Interface
            header();
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
                invalid = false;
            } else {
                System.out.println();
            }
            System.out.print("Choice (1-3): ");

            // User Input
            try {
                choice = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                choice = 0;
            }

            // User Input Processing
            if (choice > 0 && choice < 4) {
                switch (choice) {
                    case 1 -> changeUsername(user);
                    case 2 -> changePassword(user);
                }
            } else {
                invalid = true;
            }
        } while (choice != 3);
    }

    // Manage Profile Menu for Admin
    private void manageProfileByAdmin(IUser user) {
        int choice;
        boolean invalid = false;

        do {
            // User Interface
            header();
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
                invalid = false;
            } else {
                System.out.println();
            }
            System.out.print("Choice (1-3): ");

            // User Input
            try {
                choice = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                choice = 0;
            }

            // User Input Processing
            if (choice > 0 && choice < 4) {
                switch (choice) {
                    case 1 -> changeUsername(user);
                    case 2 -> changePassword(user);
                    case 3 -> deleteProfile(user);
                }
            } else {
                invalid = true;
            }
        } while (choice != 3);
    }

    // Profile Selection by Admin
    private void manageAllProfile() {
        int choice;
        boolean invalid = false;

        do {
            // User Interface
            header();
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
                invalid = false;
            } else {
                System.out.println();
            }
            System.out.print("Choice (1-3): ");

            // User Input
            try {
                choice = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                choice = 0;
            }

            // User Input Formatting
            if (choice > 0 && choice < 4) {
                switch (choice) {
                    case 1 -> managePersonalProfile(currentUser);
                    case 2 -> manageOthersProfile();
                }
            }
        } while (choice != 3);
    }

    // Search Profile of Users
    private void manageOthersProfile() {
        List<IUser> users = controller.getUserList("all");
        boolean invalid = false;

        while (true) {
            // User Interface
            header();
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

            // User Input
            System.out.print("User ID: ");
            String userID = input.nextLine();

            // User input Processing
            if (userID.toUpperCase().equals("Q")) {
                return;
            } else {
                for (IUser u : users) {
                    if (u.getUserID().equals(userID)) {
                        manageProfileByAdmin(u);
                        return;
                    }
                }
            }
            invalid = true;
        }
    }

    // Change Username Menu
    private void changeUsername(IUser user) {
        String newName;

        // User Interface
        header();
        System.out.println("Changing Username for " + user.getName());
        hardline();
        System.out.println("Please enter a new name");
        System.out.println("[enter \"q\" to go back]");
        softline();

        // User Input
        System.out.print("New Name: ");
        newName = userInputFormating();

        // User Input Processing
        if (newName.toUpperCase().equals("Q")) {
            return;
        } else {
            controller.updateUser(user.getRole(), user.getUserID(), user.getPassword(), newName);
        }

        // Completion Menu
        header();
        System.out.println("Username changed to " + newName + " successfully");
        hardline();
        System.out.print("(Press ENTER to continue)");
        input.nextLine();
    }

    // Change Password Menu
    private void changePassword(IUser user) {
        String newPassword;

        // User Interface
        header();
        System.out.println("Changing Password for " + user.getName());
        hardline();
        System.out.println("Please enter a new password");
        System.out.println("[enter \"q\" to go back]");
        softline();

        // User Input
        System.out.print("New Password: ");
        newPassword = userInputFormating();

        // User Input Formatting
        if (newPassword.toUpperCase().equals("Q")) {
            return;
        } else {
            controller.updateUser(user.getRole(), user.getUserID(), newPassword, user.getName());
        }

        // Completion Menu
        header();
        System.out.println("Password changed successfully");
        hardline();
        System.out.print("(Press ENTER to continue)");
        input.nextLine();
    }

    // Delete Profile Menu
    private void deleteProfile(IUser user) {
        boolean invalid = false;

        while (true) {
            // User Interface
            header();
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

            // User Input
            System.out.print("Confirm Delete (User ID): ");
            String userID = input.nextLine();

            // User Input Processing
            if (userID.toUpperCase().equals("Q")) {
                return;
            } else if (userID.equals(user.getUserID())) {
                controller.deleteUser(user.getRole(), user.getUserID());

                // Completion Menu
                header();
                System.out.println("Profile Deleted Successfully");
                hardline();
                System.out.print("(Press ENTER to continue)");
                input.nextLine();
                return;
            } else {
                invalid = true;
            }
        }
    }

    // Submit Evaluation Menu
    private void submitEvaluation() {
        boolean invalid = false;
        List<EvaluationActivity> activities = controller.getActivityFilterByParticipant(currentUser.getUserID());

        do {
            // User Interface
            header();
            System.out.println("Submitting Peer Feedback");
            hardline();
            System.out.println("Please select the peer feedback evaluation activity");
            System.out.println("[enter \"q\" to go back]");
            softline();
            System.out.printf("%-7s %s\n", "ID", "Activity Name");
            softline();
            for (EvaluationActivity a : activities) {
                System.out.printf("%-7s %s\n", a.getActivityID(), a.getName());
            }
            softline();
            if (invalid) {
                System.out.println("INVALID INPUT, PLEASE ENTER AN EXISTING EVALUATION ACTIVITY");
            } else {
                System.out.println();
            }

            // User Input
            System.out.print("Activity ID: ");
            String activityID = input.nextLine();

            // User Input Formatting
            if (activityID.toUpperCase().equals("Q")) {
                return;
            } else {
                invalid = true;
                for (EvaluationActivity a : activities) {
                    if (a.getActivityID().equals(activityID)) {
                        createFeedback(a);
                        invalid = false;
                        break;
                    }
                }
            }
        } while (true);
    }

    // Create Feedback Menu
    private void createFeedback(EvaluationActivity activity) {
        boolean invalid = false;
        List<IUser> peers = controller.getParticipantExlude(activity.getActivityID(), currentUser.getUserID());
        List<Feedback> feedbacks = activity.getFeedbackList();

        do {
            // User Interface
            for (int i = 0; i < peers.size(); i++) {
                for (Feedback f : feedbacks) {
                    if (f.getCreator().equals(currentUser) && f.getReceiver().equals(peers.get(i))) {
                        peers.remove(peers.get(i));
                    }
                }
            }

            header();
            if (peers.isEmpty()) {
                System.out.println("You have no more peers to give feedbacks");
                hardline();
                System.out.print("(Press ENTER to continue)");
                input.nextLine();
                return;
            } else {
                System.out.println("Submitting Peer Feedback");
                hardline();
                System.out.println("Please select the student to give feedback");
                System.out.println("[enter \"q\" to go back]");
                softline();
                System.out.printf("%-5s %s\n", "ID", "Student Name");
                softline();
                for (IUser p : peers) {
                    System.out.printf("%-5s %s\n", p.getUserID(), p.getName());
                }
                softline();
                if (invalid) {
                    System.out.println("INVALID INPUT, PLEASE ENTER AN EXISTING STUDENT");
                } else {
                    System.out.println();
                }
            }

            System.out.print("Student ID: ");
            String studentID = input.nextLine();

            if (studentID.toUpperCase().equals("Q")) {
                return;
            }

            for (IUser p : peers) {
                if (p.getUserID().equals(studentID)) {
                    System.out.println();
                    System.out.println("Enter your feedback below");
                    softline();
                    while (true) {
                        String feedbackContent = userInputFormating();

                        if (feedbackContent.equals("q")) {
                            break;
                        } else if (!feedbackContent.equals("")) {
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

    // View Feedback Menu
    private void viewEvaluation() {
        boolean invalid = false;
        List<EvaluationActivity> activities;

        if (currentUser instanceof Student) {
            activities = controller.getActivityFilterByParticipant(currentUser.getUserID());
        } else if (currentUser instanceof Lecturer) {
            activities = controller.getActivityFilterByCreator(currentUser.getUserID());
        } else {
            activities = controller.getActivityList();
        }

        do {
            // User Interface
            header();
            System.out.println("View Peer Feedback Evaluation");
            hardline();
            System.out.println("Which activity feedback would you like to view");
            System.out.println("[enter \"q\" to go back]");
            softline();
            System.out.printf("%-7s %s\n", "ID", "Activity Name");
            softline();
            for (EvaluationActivity a : activities) {
                System.out.printf("%-7s %s\n", a.getActivityID(), a.getName());
            }
            softline();
            if (invalid) {
                System.out.println("INVALID INPUT, PLEASE ENTER AN EXISTING EVALUATION ACTIVITY");
                invalid = false;
            } else {
                System.out.println();
            }

            // User Input
            System.out.print("Activity ID: ");
            String activityID = input.nextLine();

            // User Input Selection
            if (activityID.toUpperCase().equals("Q")) {
                return;
            }

            //
            for (EvaluationActivity a : activities) {
                if (a.getActivityID().equals(activityID)) {
                    if (!(currentUser instanceof Student)) {
                        invalid = false;
                        generateFeebackReport(a);
                        break;
                    }

                    List<Feedback> feedbacks = controller.getFeedbackFilterByCreator(activityID,
                            currentUser.getUserID());
                    invalid = false;

                    do {
                        // User Interface
                        header();
                        System.out.println("View Peer Feedback Evaluation");
                        hardline();
                        System.out.println("Which feedback would you like to view");
                        System.out.println("[enter \"q\" to go back]");
                        softline();

                        System.out.printf("%-15s %-9s %-15s %-15s %-15s\n", "Activity", "ID", "Peer Name",
                                "Date Created",
                                "Date Updated");

                        softline();

                        for (Feedback f : feedbacks) {
                            System.out.printf("%-15s %-9s %-15s %-15s %-15s\n", a.getName(), f.getFeedbackID(),
                                    f.getReceiver().getName(),
                                    f.getDateCreated(),
                                    f.getDateUpdated());
                        }

                        if (invalid) {
                            System.out.println("INVALID INPUT, PLEASE ENTER AN EXISTING FEEDBACK");
                        } else {
                            System.out.println();
                        }

                        // User Input
                        System.out.print("Feedback ID: ");
                        String feedbackID = input.nextLine();

                        // User Input Processing
                        if (feedbackID.toUpperCase().equals("Q")) {
                            break;
                        }

                        for (Feedback f : feedbacks) {
                            if (f.getFeedbackID().equals(feedbackID)) {
                                updateFeedback(a, f);
                                System.out.println();
                                invalid = false;
                                break;
                            }
                            invalid = true;
                        }
                    } while (true);

                    break;
                }
                invalid = true;
            }
        } while (true);
    }

    // Create Evaluation Activity Menu
    private void createEvaluationActivity() {
        boolean invalid = false;

        // User Interface
        header();
        System.out.println("Creating New Peer Feedback Evaluation Activity");
        hardline();
        System.out.println("Please enter the activity information");
        softline();

        // User Input
        System.out.print("Activity Name: ");
        String activityName = userInputFormating();
        System.out.println();

        // User Input Processing
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
            // User Interface
            header();
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

            // User Input Processing
            if (userID.toUpperCase().equals("Q")) {
                break;
            } else if (participants.contains(userID)) {
                invalid = true;
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

    // Generate Report Menu
    private void generateFeebackReport(EvaluationActivity activity) {
        List<Feedback> feedbacks = activity.getFeedbackList();

        // User Interface
        header();
        System.out.println("Feedback Report (" + activity.getName() + ")");
        hardline();
        System.out.printf("%-9s %-15s %-15s %-15s %-15s %s\n", "ID", "Creator", "Receiver",
                "Date Created", "Date Updated", "Feedback");

        softline();

        if (feedbacks.isEmpty()) {
            System.out.println("Nothing to see here...");
        } else {
            for (Feedback f : feedbacks) {
                System.out.printf("%-9s %-15s %-15s %-15s %-15s %s\n", f.getFeedbackID(),
                        f.getCreator().getName(),
                        f.getReceiver().getName(),
                        f.getDateCreated(),
                        f.getDateUpdated(),
                        f.getContent());
            }
        }
        softline();

        System.out.print("(Press ENTER to continue)");
        input.nextLine();
    }

    // Create Account Menu
    private void createAccount() {
        List<String> roles = new ArrayList<>();
        roles.add("student");
        roles.add("lecturer");
        roles.add("administrator");

        String role;
        String name;
        String password;
        boolean invalid = false;

        while (true) {
            // User Interface
            header();
            System.out.println("Creating New Account");
            hardline();
            System.out.println("Please enter the information of the new profile");
            softline();
            if (invalid) {
                System.out.println("PLEASE ENTER A VALID USER ROLE");
            } else {
                System.out.println();
            }

            // User Roles
            System.out.print("User Role (student, lecturer, administrator): ");
            role = input.nextLine().toLowerCase();

            if (roles.contains(role)) {

                // User Details
                System.out.print("Name: ");
                name = userInputFormating();
                System.out.print("Password:");
                password = userInputFormating();

                List<IUser> users = controller.getUserList(role);
                int numID = 1;
                String userID = null;

                if (!users.isEmpty()) {
                    numID = Integer.parseInt(users.get(users.size() - 1).getUserID().substring(1)) + 1;
                }

                switch (role) {
                    case "student" -> userID = "s" + numID;
                    case "lecturer" -> userID = "l" + numID;
                    case "administrator" -> userID = "a" + numID;
                }

                controller.addUser(role, userID, password, name);

                // Completion Menu
                header();
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

    // Update Feedback Menu
    private void updateFeedback(EvaluationActivity activity, Feedback feedback) {
        boolean invalid = false;

        do {
            // User Interface
            header();
            System.out.println("View Peer Feedback Evaluation");
            hardline();
            System.out.println("[enter \"e\" to edit feedback content]");
            System.out.println("[enter \"q\" to go back]");
            softline();

            System.out.println(feedback.getContent());
            softline();

            if (invalid) {
                System.out.println("INVALID INPUT, PLEASE ENTER AN EXISTING FEEDBACK");
                invalid = false;
            } else {
                System.out.println();
            }

            // User Input
            System.out.print("Choice (e/q): ");
            String feedbackID = input.nextLine();

            // User Input Processing
            switch (feedbackID.toUpperCase()) {
                case "Q" -> {
                    return;
                }
                case "E" -> {
                    System.out.println();
                    System.out.println("Enter your updated feedback below");
                    softline();
                    while (true) {
                        String feedbackContent = userInputFormating();

                        if (feedbackContent.toUpperCase().equals("Q")) {
                            break;
                        } else if (!feedbackContent.equals("")) {
                            controller.updateFeedback(activity.getActivityID(),
                                    feedback.getFeedbackID(),
                                    currentUser.getUserID(), feedback.getReceiver().getUserID(),
                                    feedback.getDateCreated(),
                                    LocalDate.now().toString(),
                                    feedbackContent);
                            invalid = false;
                            break;
                        } else {
                            invalid = true;
                        }
                    }
                }
                default -> invalid = true;
            }
        } while (true);
    }

    // Console Manipulation and Decoration Methods
    // Clear Console Screen
    private static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException ex) {
        }
    }

    // Line Types
    private void softline() {
        System.out.println(
                "--------------------------------------------------------------------------------------------------------------------------------");
    }

    private void hardline() {
        System.out.println(
                "================================================================================================================================");
    }

    // Header
    private void header() {
        clearScreen();
        hardline();
        System.out.println("Peer Feedback Evaluation System");
        hardline();
    }

    // User Input Formatting
    private String userInputFormating() {
        String userInput = input.nextLine();

        // Remove all ; from Input
        userInput = userInput.replaceAll(";", "");

        return userInput;
    }
}