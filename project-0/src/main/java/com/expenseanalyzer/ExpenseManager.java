package com.expenseanalyzer;

import java.util.List;
import java.math.BigDecimal;
import java.util.ArrayList;

public class ExpenseManager {
    private List<Expense> expenses = new ArrayList<>();

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public List<Expense> getAllExpenses() {
        return new ArrayList<>(expenses);
    }

    public void printAllExpenses() {

        System.out.printf("%-5s %-15s %-10s %-10s %-10s%n", "ID", "DESCRIPTION", "CATEGORY", "AMOUNT", "DATE");
        for (Expense expense : getAllExpenses()) {
            System.out.printf("%-5d %-15s %-10s %-10s %-10s%n",
                    expense.getId(),
                    expense.getDescription(),
                    expense.getCategory(),
                    expense.getAmount(),
                    expense.getDate());
        }
    
    }

    public BigDecimal calculateTotal() {
        return expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
