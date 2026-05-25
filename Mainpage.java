/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Student
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Mainpage {

    // Global session management tracking state variables
    private static UserRecord registeredUser = null;
    private static boolean isUserLoggedIn = false;
    private static int totalMessagesSentCounter = 0; 
    
    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();

    // Data structure model representing a single account configuration
    static class UserRecord {
        String username;
        String cellphone;
        String password;

        public UserRecord(String username, String cellphone, String password) {
            this.username = username;
            this.cellphone = cellphone;
            this.password = password;
        }
    }

    // Data structure model tracking sent messages
    static class MessageDetails {
        String messageId;
        String messageHash;
        String recipient;
        String messageText;

        public MessageDetails(String messageId, String messageHash, String recipient, String messageText) {
            this.messageId = messageId;
            this.messageHash = messageHash;
            this.recipient = recipient;
            this.messageText = messageText;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Welcome to the Integrated QuickChat Application ===");
        
        // Step 1: Force User Registration first
        runRegistrationFlow();

        // Step 2: Force Login loop verification process
        while (!isUserLoggedIn) {
            if (!performLogin()) {
                System.out.println("Login sequence structural rejection. Let's try again.");
            }
        }

        // Step 3: Enter the Main Messenger Application
        boolean running = true;
        while (running) {
            System.out.println("\n=================================");
            System.out.println("Welcome to QuickChat Dashboard.");
            System.out.println("=================================");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages – \"Coming Soon.\"");
            System.out.println("3) Quit");
            System.out.print("Please choose an option (1-3): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> handleSendMessagesFlow();
                case "2" -> System.out.println("\nComing Soon.");
                case "3" -> {
                    System.out.println("Thank you for using QuickChat. Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid option. Please enter 1, 2, or 3.");
            }
        }
        scanner.close();
    }

    // ==========================================
    // AUTHENTICATION & CAPTURE MODULES (PART 1)
    // ==========================================
    
    private static void runRegistrationFlow() {
        System.out.println("\n--- QuickChat Account Creation Registration ---");
        
        System.out.println("Enter your username: ");
        String username = scanner.nextLine();
        while (!validateUsername(username)) {
            System.out.println("RE-Enter your username: ");
            username = scanner.nextLine();
        }
        
        System.out.println("Enter your secure account password: ");
        String password = scanner.nextLine();
        while (!validatePassword(password)) {
            System.out.println("Re-Enter your password: ");
            password = scanner.nextLine();
        }
        
        System.out.println("Enter your cellphone number: ");
        String cellphone = scanner.nextLine();
        while (!validateCellphone(cellphone)) {
            System.out.println("Re-Enter your cellphone number: ");
            cellphone = scanner.nextLine();
        }

        // Keep local memory instance track context pointer
        registeredUser = new UserRecord(username, cellphone, password);
        System.out.println("\n Account successfully provisioned!");
    }

    private static boolean performLogin() {
        System.out.println("\n--- QuickChat Security Login Portal ---");
        System.out.print("Enter your login username: ");
        String enteredUser = scanner.nextLine();
        System.out.print("Enter your password string: ");
        String enteredPass = scanner.nextLine();
        
        if (registeredUser != null && 
            registeredUser.username.equals(enteredUser) && 
            registeredUser.password.equals(enteredPass)) {
            
            System.out.println("\n\"Login successfully! Welcome back\"");
            isUserLoggedIn = true;
            return true;
        } else {
            System.out.println("\n\"Login failed. Invalid credentials.\"");
            return false;
        }
    }

    // ==========================================
    // VALIDATION PARSING SERVICES 
    // ==========================================
    
    public static boolean validateUsername(String name) {
        if (name.length() <= 5 && name.contains("_")) {
            System.out.println("Username successfully captured.");
            return true;
        } else {
            System.out.println("Username is not correctly formatted, please ensure that your username contains an underscore and is no more than 5 characters long.");
            return false;
        }
    }
    
    public static boolean validatePassword(String password) {
        if (password.length() < 8) {
            System.out.println("Password must be at least 8 characters long.");
            return false;
        } else if (!password.matches(".*[A-Z].*")) {
            System.out.println("Password must contain at least one uppercase letter.");
            return false;
        } else if (!password.matches(".*[0-9].*")) {
            System.out.println("Password must contain at least one number.");
            return false;
        } else if (!password.matches(".*[!@#$%&*()].*")) {
            System.out.println("Password must contain at least one special character.");
            return false;
        } else {
            System.out.println("Password successfully captured.");
            return true;
        }
    }

    public static boolean validateCellphone(String cellphone) {
        if (!cellphone.matches("^\\+27\\d{9}$")) {
            System.out.println("Cellphone number must be in format +27 followed by 9 digits.");
            return false;
        } else {
            System.out.println("Cellphone number successfully captured.");
            return true;
        }
    }

    // ==========================================
    // MESSAGING UTILITIES AND ENGINE (PART 2)
    // ==========================================
    
    private static void handleSendMessagesFlow() {
        System.out.print("\nHow many messages would you like to enter? ");
        int numberOfMessagesToProcess;
        try {
            numberOfMessagesToProcess = Integer.parseInt(scanner.nextLine().trim());
            if (numberOfMessagesToProcess <= 0) {
                System.out.println("Please enter a number greater than 0.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format. Returning to main menu.");
            return;
        }

        List<MessageDetails> sessionMessages = new ArrayList<>();
        int successfullySentInSessionCount = 0;

        for (int i = 0; i < numberOfMessagesToProcess; i++) {
            System.out.println("\n--- Processing Message " + (i + 1) + " of " + numberOfMessagesToProcess + " ---");

            // 1. Unique Message ID Generation
            String messageId = generateRandomTenDigitId();

            // 2. Recipient Validation (Enforces +27 format from Part 1 requirements)
            String recipient = "";
            while (true) {
                System.out.print("Enter Recipient Cell Number (+27XXXXXXXXX): ");
                recipient = scanner.nextLine().trim();
                if (validateCellphone(recipient)) {
                    break;
                }
            }

            // 3. Message Text Constraint Check
            String messageText = "";
            while (true) {
                System.out.print("Enter Message Content (Max 250 characters): ");
                messageText = scanner.nextLine().trim();
                if (messageText.length() > 250) {
                    System.out.println("\"Please enter a message of less than 250 characters.\"");
                } else {
                    break;
                }
            }

            // 4. Execution Directives Actions Choices
            System.out.println("\nChoose one of the following options for this message:");
            System.out.println("1. Send Message");
            System.out.println("2. Disregard Message");
            System.out.println("3. Store Message to send later");
            System.out.print("Selection: ");
            String actionChoice = scanner.nextLine().trim();

            if (actionChoice.equals("1")) {
                System.out.println("\n\"Message successfully sent\"");
                
                // Formulate customized checksum identity key hash output
                String messageHash = generateMessageHash(messageId, totalMessagesSentCounter, messageText);
                
                totalMessagesSentCounter++;
                successfullySentInSessionCount++;

                MessageDetails msgDetails = new MessageDetails(messageId, messageHash, recipient, messageText);
                sessionMessages.add(msgDetails);

                // Print tracking diagnostic output values
                System.out.println("\n>>> SENT MESSAGE SUMMARY RECIPIENT RECORD <<<");
                System.out.println("Message ID:   " + msgDetails.messageId);
                System.out.println("Message Hash: " + msgDetails.messageHash);
                System.out.println("Recipient:    " + msgDetails.recipient);
                System.out.println("Message:      " + msgDetails.messageText);
                System.out.println("---------------------------------------------");

            } else if (actionChoice.equals("2") || actionChoice.equals("0")) {
                System.out.println("\n\"Press 0 to delete the message\"");
                System.out.println("Message discarded.");
            } else if (actionChoice.equals("3")) {
                System.out.println("\n\"Message successfully stored\"");
                System.out.println("(Note: Store Message execution framework tool is categorized under Future Work)");
            } else {
                System.out.println("Invalid choice. Message dropped.");
            }
        }

        // Metrics display presentation summaries report
        System.out.println("\n=========================================");
        System.out.println("Session Summary Completed!");
        System.out.println("Messages successfully sent this batch: " + successfullySentInSessionCount);
        System.out.println("Total global application messages sent: " + totalMessagesSentCounter);
        System.out.println("=========================================");
    }

    private static String generateRandomTenDigitId() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    private static String generateMessageHash(String messageId, int numSent, String messageText) {
        String firstTwoDigits = messageId.substring(0, 2);
        String cleanedText = messageText.trim().replaceAll("\\s+", " ");
        String wordsCombined = "";

        if (!cleanedText.isEmpty()) {
            String[] words = cleanedText.split(" ");
            String firstWord = words[0];
            String lastWord = words[words.length - 1];
            
            wordsCombined = (firstWord + lastWord).toUpperCase();
            wordsCombined = wordsCombined.replaceAll("[^A-Z0-9]", ""); 
        } else {
            wordsCombined = "EMPTYMESSAGE";
        }

        return firstTwoDigits + ":" + numSent + ":" + wordsCombined;
    }
}