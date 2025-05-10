import java.io.*;
import java.util.*;
import java.time.LocalDate;

public class ExpenseTrackerApp {
    static Scanner scanner = new Scanner(System.in);
    static Map<String, String> userDB = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("Welcome to the Expense Tracker!");
        loadUsers();

        User currentUser = authenticateUser();
        ExpenseManager manager = new ExpenseManager();
        manager.expenses = FileHandler.loadExpenses(currentUser.username);

        int choice;
        do {
            System.out.println("\n1. Add Expense\n2. List Expenses\n3. Category Summary\n4. Save & Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addExpense(manager);
                    break;
                case 2:
                    manager.listExpenses();
                    break;
                case 3:
                    manager.categoryWiseSummary();
                    break;
                case 4:
                    FileHandler.saveExpenses(manager.expenses, currentUser.username);
                    saveUsers();
                    System.out.println("Data saved. Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 4);
    }

    static void addExpense(ExpenseManager manager) {
        System.out.print("Enter date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());
        System.out.print("Enter category: ");
        String category = scanner.nextLine();
        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        manager.addExpense(new Expense(date, category, amount));
        System.out.println("Expense added successfully.");
    }

    static User authenticateUser() {
        while (true) {
            System.out.print("\n1. Register\n2. Login\nEnter choice: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Username: ");
            String username = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();

            if (option == 1) {
                if (userDB.containsKey(username)) {
                    System.out.println("User already exists.");
                } else {
                    userDB.put(username, password);
                    return new User(username, password);
                }
            } else if (option == 2) {
                if (userDB.containsKey(username) && userDB.get(username).equals(password)) {
                    return new User(username, password);
                } else {
                    System.out.println("Invalid credentials.");
                }
            } else {
                System.out.println("Invalid option.");
            }
        }
    }

    static void loadUsers() {
        File file = new File("users.txt");
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    userDB.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading users.");
        }
    }

    static void saveUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt"))) {
            for (Map.Entry<String, String> entry : userDB.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving users.");
        }
    }
}
