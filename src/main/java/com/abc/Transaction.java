package com.abc;

import java.util.Date;

public class Transaction {
    public final double amount;

    private Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }
    
    public boolean withdrawInTheLastTenDays() {
    	Date today = new Date();
    	int diff = today.getDay() - transactionDate.getDate();
    	return (diff < 10) && (amount < 0.0); 
    }
}