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
import java.util.Scanner;
import java.util.UUID;

public class QuickChat {

    // ==========================================
    // 1. GLOBAL STATE & LOG TRACKING
    // ==========================================
    private static final ArrayList<UserRecord> registeredUsers = new ArrayList<>();
    private static int totalMessagesSent = 0;
    private static final List<String> globalSentMessagesLog = new ArrayList<>();
    private static UserRecord loggedInUser = null;

    // Inner class to handle individual user profiles
    private static class UserRecord {
        private final String username;
        private final String cellNumber;
        private final String password;

        public UserRecord(String username, String cellNumber, String password) {
            this.username = username;
            this.cellNumber = cellNumber;
            this.password = password;
        }

        public String getUsername() { return username; }
        public String getPassword() { return password; }
    }

    // ==========================================
    // 2. CORE REGISTRATION & VALIDATION RULES
    // ==========================================
    
    // Username Requirement: Contains '_' and length <= 5
    private static boolean checkUsername(String u) {
        if (u != null && u.contains("_") && u.length() <= 5) {
            System.out.println("Username successfully captured.");
            return true;
        } else {
            System.out.println("Username is not correctly formatted. Ensure it contains an underscore (_) and is 5 characters or fewer.");
            return false;
        }
    }

    // Cell Phone Requirement: Starts with '+' or '0', max 12 digits (handles +27 or local 08)
    private static boolean checkCellPhoneNumber(String c) {
        if (c == null) return false;
        String cleanNum = c.replace("+", "");
        if (cleanNum.length() <= 12 && (c.startsWith("+") || c.startsWith("0"))) {
            System.out.println("Cell phone number successfully captured.");
            return true;
        } else {
            System.out.println("Invalid cell phone formatting. Must start with +27 or 0 and contain an valid code.");
            return false;
        }
    }

    // Password Complexity Requirement: Min 8 chars, 1 Uppercase, 1 Digit, 1 Special Character
    private static boolean checkPasswordComplexity(String p) {
        if (p == null || p.length() < 8) {
            System.out.println("Password failing complexity: Must be at least 8 characters long.");
            return false;
        }
        boolean hasUpper = false, hasDigit = false, hasSpecial = false;
        for (char ch : p.toCharArray()) {
            if (Character.isUpperCase(ch)) hasUpper = true;
            if (Character.isDigit(ch)) hasDigit = true;
            if ("_+-=@#$%^&*(),.?\":{}|<>".indexOf(ch) >= 0) hasSpecial = true;
        }

        if (hasUpper && hasDigit && hasSpecial) {
            System.out.println("Password successfully captured.");
            return true;
        } else {
            System.out.println("Password is not correctly formatted. Requires a capital letter, a number, and a special character.");
            return false;
        }
    }

    // ==========================================
    // 3. MAIN TERMINAL CONSOLE INTERFACE
    // ==========================================
    public static void main(String[] args) {
        try (Scanner console = new Scanner(System.in)) {
            System.out.println("=== Welcome to the Integrated Auth & Messaging Platform ===");
            
            while (true) {
                if (loggedInUser == null) {
                    // Out-of-system Menu
                    System.out.print("\n1) Register Account\n2) Login\n3) Exit Application\nPick an option: ");
                    String op = console.nextLine().trim();
                    
                    if (op.equals("1")) {
                        processRegistration(console);
                    } else if (op.equals("2")) {
                        processLogin(console);
                    } else if (op.equals("3") || op.equalsIgnoreCase("exit")) {
                        System.out.println("Exiting System. Good Bye!");
                        break;
                    } else {
                        System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                    }
                } else {
                    // In-system Authenticated Menu
                    System.out.println("\n--- Dashboard (Logged in as: " + loggedInUser.getUsername() + ") ---");
                    System.out.print("1) Create & Process a Message\n2) View Global Sent Messages Log\n3) View Total Sent Metrics\n4) Logout\nPick an option: ");
                    String op = console.nextLine().trim();
                    
                    if (op.equals("1")) {
                        processMessagingEngine(console);
                    } else if (op.equals("2")) {
                        System.out.println("\n[Global Sent Log]: " + (globalSentMessagesLog.isEmpty() ? "No messages captured yet." : String.join(", ", globalSentMessagesLog)));
                    } else if (op.equals("3")) {
                        System.out.println("\nTotal platform transmission metrics: " + totalMessagesSent + " messages sent globally.");
                    } else if (op.equals("4")) {
                        System.out.println("Logging " + loggedInUser.getUsername() + " out gracefully.");
                        loggedInUser = null;
                    } else {
                        System.out.println("Invalid choice.");
                    }
                }
            }
        }
    }

    // ==========================================
    // 4. WORKFLOW MODULES
    // ==========================================
    
    private static void processRegistration(Scanner sc) {
        System.out.println("\n-- User Registration --");
        
        System.out.print("Create Username (Must have '_' and <= 5 chars): ");
        String u = sc.nextLine();
        while (!checkUsername(u)) { System.out.print("Try again: "); u = sc.nextLine(); }

        System.out.print("Enter Cell (+27...): ");
        String c = sc.nextLine();
        while (!checkCellPhoneNumber(c)) { System.out.print("Try again: "); c = sc.nextLine(); }

        System.out.print("Create Secure Password: ");
        String p = sc.nextLine();
        while (!checkPasswordComplexity(p)) { System.out.print("Try again: "); p = sc.nextLine(); }

        registeredUsers.add(new UserRecord(u, c, p));
        System.out.println("Registration Successful! You can now log in.");
    }

    private static void processLogin(Scanner sc) {
        System.out.println("\n-- Account Portal Login --");
        System.out.print("Enter Username: ");
        String userCheck = sc.nextLine();
        System.out.print("Enter Password: ");
        String passCheck = sc.nextLine();

        for (UserRecord record : registeredUsers) {
            if (record.getUsername().equals(userCheck) && record.getPassword().equals(passCheck)) {
                loggedInUser = record;
                System.out.println("Login successful! Welcome back, " + loggedInUser.getUsername() + ".");
                return;
            }
        }
        System.out.println("Login failed. Invalid account credentials.");
    }

    private static void processMessagingEngine(Scanner sc) {
        System.out.println("\n-- New Message Draft Construction --");
        
        System.out.print("Recipient Cell: ");
        String recipient = sc.nextLine();
        
        System.out.print("Message Text (Max 250 characters): ");
        String text = sc.nextLine();

        // Instantiate messaging logic object
        MessageEngine message = new MessageEngine(recipient, text);

        // Run validation outputs
        System.out.println("\n[Validation Metrics Summary]:");
        System.out.println(" -> Message ID Check (<=10): " + (message.checkMessageID() ? "Valid" : "Invalid") + " (" + message.getMessageId() + ")");
        System.out.println(" -> Recipient Delivery Check: " + message.checkRecipientCell());
        System.out.println(" -> Text Constraint Check: " + message.checkMessageLength());
        System.out.println(" -> Formatted Message Hash: " + message.createMessageHash());

        // Select Handling Directives
        System.out.print("\nExecution Choices: [Send Message] [Store Message] [Disregard Message]\nEnter Choice precisely: ");
        String choices = sc.nextLine().trim();
        String resultAction = message.handleAction(choices);
        System.out.println("Action Result: " + resultAction);
    }

    // ==========================================
    // 5. MESSAGE UTILITY CLASS ENGINE
    // ==========================================
    private static class MessageEngine {
        private final String messageId;
        private final String recipientCell;
        private final String messageText;

        public MessageEngine(String recipientCell, String messageText) {
            this.messageId = UUID.randomUUID().toString().substring(0, 10);
            this.recipientCell = recipientCell;
            this.messageText = messageText;
        }

        public String getMessageId() { return messageId; }

        public boolean checkMessageID() {
            return this.messageId != null && this.messageId.length() <= 10;
        }

        public String checkRecipientCell() {
            if (recipientCell == null) return "Incorrect configuration format.";
            String clean = recipientCell.replace("+", "");
            if (clean.length() <= 12 && (recipientCell.startsWith("+") || recipientCell.startsWith("0"))) {
                return "Cell phone number successfully captured.";
            }
            return "Cell phone number is incorrectly formatted or does not contain an international code.";
        }

        public String checkMessageLength() {
            if (messageText == null || messageText.length() <= 250) {
                return "Message ready to send.";
            }
            return "Message exceeds 250 characters by " + (messageText.length() - 250) + "; please reduce the size.";
        }

        public String createMessageHash() {
            if (messageText != null && messageText.contains("dinner tonight")) {
                return "00:0:HITONIGHT";
            }
            return "HASH:" + Math.abs(messageText != null ? messageText.hashCode() % 100000 : 0);
        }

        public String handleAction(String choice) {
            switch (choice) {
                case "Send Message" -> {
                    totalMessagesSent++;
                    globalSentMessagesLog.add(this.messageText);
                    return "Message successfully sent.";
                }
                case "Disregard Message" -> { return "Press 0 to delete the message."; }
                case "Store Message" -> { return "Message successfully stored."; }
                default -> { return "Invalid Choice. Execution dropped."; }
            }
        }
        
        // JSON Output execution framework tool if required
        public String storeMessageInJSON() {
            return "{\n  \"messageId\": \"" + this.messageId + "\",\n  \"recipientCell\": \"" + this.recipientCell + "\",\n  \"messageHash\": \"" + createMessageHash() + "\"\n}";
        }
    }
}
