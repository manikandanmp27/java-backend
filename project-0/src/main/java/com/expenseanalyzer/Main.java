package com.expenseanalyzer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ExpenseManager manager = new ExpenseManager();
        Expense exp1 = new Expense(1, "Lunch", Category.FOOD, LocalDate.now(), new BigDecimal("150"));
        Expense exp2 = new Expense(2, "Bus", Category.TRAVEL, LocalDate.now(), new BigDecimal("40"));
        manager.addExpense(exp1);
        manager.addExpense(exp2);

        List<Expense> expenses = manager.getAllExpenses();
        manager.printAllExpenses();
        System.out.println("Total:"+manager.calculateTotal());

    }
}