import java.util.*;

public class ExpenseManager {
    List<Expense> expenses = new ArrayList<>();

    public void addExpense(Expense e) {
        expenses.add(e);
    }

    public void listExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
        } else {
            for (Expense e : expenses) {
                System.out.println(e);
            }
        }
    }

    public void categoryWiseSummary() {
        Map<String, Double> summary = new HashMap<>();
        for (Expense e : expenses) {
            summary.put(e.category, summary.getOrDefault(e.category, 0.0) + e.amount);
        }
        System.out.println("\nCategory-wise Summary:");
        for (Map.Entry<String, Double> entry : summary.entrySet()) {
            System.out.println(entry.getKey() + ": Rs." + entry.getValue());
        }
    }
}
