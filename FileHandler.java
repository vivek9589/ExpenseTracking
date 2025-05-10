import java.io.*;
import java.util.*;

public class FileHandler {
    static final String FILE_PREFIX = "expenses_";

    public static void saveExpenses(List<Expense> expenses, String username) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PREFIX + username + ".dat"))) {
            oos.writeObject(expenses);
        } catch (IOException e) {
            System.out.println("Error saving expenses: " + e.getMessage());
        }
    }

    public static List<Expense> loadExpenses(String username) {
        File file = new File(FILE_PREFIX + username + ".dat");
        if (!file.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Expense>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading expenses: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
