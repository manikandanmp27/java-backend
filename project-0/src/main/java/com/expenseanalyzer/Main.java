package com.expenseanalyzer;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
public class Main {
    public static void main(String[] args) {
        ExpenseManager manager = new ExpenseManager();
        Expense exp1 = new Expense(1, "Lunch", Category.FOOD, LocalDate.now(), new BigDecimal("150"));
        manager.addExpense(exp1);

        List<Expense> expenses=manager.getAllExpenses();
        System.out.println(expenses.size());
    }
}