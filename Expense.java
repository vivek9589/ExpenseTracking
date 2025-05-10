import java.io.Serializable;
import java.time.LocalDate;

public class Expense implements Serializable {
    LocalDate date;
    String category;
    double amount;

    public Expense(LocalDate date, String category, double amount) {
        this.date = date;
        this.category = category;
        this.amount = amount;
    }

    public String toString() {
        return date + " | " + category + " | Rs." + amount;
    }
}
