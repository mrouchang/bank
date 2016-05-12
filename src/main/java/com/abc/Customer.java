package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private List<Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    public String getName() {
        return name;
    }

    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }
    
    // we only test to see if two accounts are valid and if the amount transfered makes sense.
    // we dont check for account types. we assume transfer could happen amongst all account types.
    public synchronized void transfer(Account from, Account to, double amt) throws Exception {
    	if (amt < 0.0) {
    		throw new Exception("Transfer amount has to be greater than 0.0.");
    	}
    	Account fromAcct = accounts.stream().filter(acct -> acct.equals(from)).collect(Collectors.toList()).get(0);
    	Account toAcct = accounts.stream().filter(acct -> acct.equals(to)).collect(Collectors.toList()).get(0);
    	if (fromAcct == null || toAcct == null) {
    		throw new Exception("Invalid account");
    	}

    	fromAcct.withdraw(amt);
    	toAcct.deposit(amt);
    }

    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total += a.sumTransactions();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

    private String statementForAccount(Account a) {
        String s = "";

       //Translate to pretty account type
        switch(a.getAccountType()){
            case Account.CHECKING:
                s += "Checking Account\n";
                break;
            case Account.SAVINGS:
                s += "Savings Account\n";
                break;
            case Account.MAXI_SAVINGS:
                s += "Maxi Savings Account\n";
                break;
        }

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.transactions) {
            s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
            total += t.amount;
        }
        s += "Total " + toDollars(total);
        return s;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
