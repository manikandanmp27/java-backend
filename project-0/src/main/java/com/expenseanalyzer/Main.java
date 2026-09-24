package com.expenseanalyzer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.time.LocalDate;
import java.util.Scanner;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        ExpenseManager manager = new ExpenseManager();

        // Expense exp1 = new Expense(1, "Lunch", Category.FOOD, LocalDate.now(), new
        // BigDecimal("150"));
        // Expense exp2 = new Expense(2, "Bus", Category.TRAVEL, LocalDate.now(), new
        // BigDecimal("40"));
        // manager.addExpense(exp1);
        // manager.addExpense(exp2);

        // List<Expense> expenses = manager.getAllExpenses();
        // manager.printAllExpenses();
        // System.out.println("Total:"+manager.calculateTotal());
        // Map<Category,List<Expense>> grouped=manager.groupByCategory();
        // System.out.println(grouped);
        // Optional<Expense> highest=manager.findHighestExpense();
        // if(highest.isPresent())
        // {
        // System.out.println(highest.get());
        // }
        // List<Expense> sorted=manager.sortByAmount();
        // System.out.println(sorted);

        // List<Expense> filterCategory=manager.filterByCategory(Category.FOOD);
        // System.out.println(filterCategory);
        // LocalDate date=LocalDate.of(26,9,24);
        // System.out.println(manager.filterByDate(date));

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Add Expense");
            System.out.println("2. View All Expenses");
            System.out.println("3. Calculate Total");
            System.out.println("4. Group by Category");
            System.out.println("5. Find Highest Expense");
            System.out.println("6. Sort by Amount");
            System.out.println("7. Filter by Category");
            System.out.println("8. Filter by Date");
            System.out.println("9. Exit");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    try {
                        System.out.print("Enter ID: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Enter description: ");
                        String description = scanner.nextLine();

                        System.out.print("Enter category: ");
                        Category category = Category.valueOf(scanner.nextLine().toUpperCase());

                        System.out.print("Enter amount: ");
                        BigDecimal amount = new BigDecimal(scanner.nextLine());

                        System.out.print("Enter date (YYYY-MM-DD): ");
                        LocalDate date = LocalDate.parse(scanner.nextLine());

                        Expense expense = new Expense(id, description, category, date, amount);
                        manager.addExpense(expense);
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid ID. Please enter a number.");
                        scanner.nextLine();
                    } catch (IllegalArgumentException e) {
                        System.out.println("Inavlid category,amount or date.");
                    }

                    break;
                case 2:
                    manager.printAllExpenses();
                    break;

                case 3:
                    System.out.println("Total: " + manager.calculateTotal());
                    break;

                case 4:
                    System.out.println(manager.groupByCategory());
                    break;

                case 5:
                    System.out.println(manager.findHighestExpense());
                    break;

                case 6:
                    System.out.println(manager.sortByAmount());
                    break;

                case 7:
                    System.out.print("Enter category: ");
                    Category filterCategory = Category.valueOf(scanner.nextLine().toUpperCase());

                    System.out.println(manager.filterByCategory(filterCategory));
                    break;

                case 8:
                    System.out.print("Enter date (YYYY-MM-DD): ");
                    LocalDate filterDate = LocalDate.parse(scanner.nextLine());
                    System.out.println(manager.filterByDate(filterDate));
                    break;

                default:
                    System.out.println("Invalid choice");
            }
            if (choice == 9) {
                break;
            }
        }

        scanner.close();
    }
}