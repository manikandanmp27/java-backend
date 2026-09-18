package com.expenseanalyzer;

import java.time.LocalDate;
import java.math.BigDecimal;

public class Expense {
    private int id;
    private String description;
    private Category category;
    private LocalDate date;
    private BigDecimal amount;

    public Expense(int id, String description, Category category, LocalDate date, BigDecimal amount) {
        this.id = id;
        this.description = description;
        this.category = category;
        this.date = date;
        this.amount = amount;
    }

    public  int getId(){
        return id;
    }
    public String getDescription(){
        return description;
    }
    public BigDecimal getAmount(){
        return amount;
    }
    public LocalDate getDate(){
        return date;
    }
    public Category getCategory(){
        return category;
    }
    
    public void setDescription(String description){
        this.description=description;
    }
    public void setDate(LocalDate date)
    {
        this.date=date;
    }
    public void setCategory(Category category)
    {
        this.category=category;
    }
    public void setAmount(BigDecimal amount){
        this.amount=amount;
    }
}
