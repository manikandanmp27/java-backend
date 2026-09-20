package com.expenseanalyzer;
import java.util.List;
import java.util.ArrayList;
public class ExpenseManager {
    private List<Expense> expenses=new ArrayList<>();

    public void addExpense(Expense expense)
    {
        expenses.add(expense);
    }

    public List<Expense> getAllExpenses(){
        return new ArrayList<>(expenses);
    }
}
