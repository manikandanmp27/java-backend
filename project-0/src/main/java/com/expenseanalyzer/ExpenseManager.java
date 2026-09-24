package com.expenseanalyzer;

import java.util.List;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.time.LocalDate;
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

    public Map<Category,List<Expense>> groupByCategory(){
        return expenses.stream()
                        .collect(Collectors.groupingBy(Expense::getCategory));
    }

    public Optional<Expense> findHighestExpense(){
        return expenses.stream()
                        .max(Comparator.comparing(Expense::getAmount));
    }
    public List<Expense> sortByAmount(){
        return expenses.stream()   
                        .sorted(Comparator.comparing(Expense::getAmount))
                        .toList();
    }

    public List<Expense> filterByCategory(Category category)
    {
        return expenses.stream()
                .filter(expense->expense.getCategory()==category)
                .toList();
    }
    public List<Expense> filterByDate(LocalDate date) {
    return expenses.stream()
            .filter(expense -> expense.getDate().equals(date))
            .toList();
}

}
