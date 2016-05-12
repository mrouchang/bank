package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.sql.Savepoint;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }

    /*
     * Statement for Oscar
	 * Savings Account
     * deposit $5,000.00
     * withdrawal $500.00
     * Total $4,500.00

     * Checking Account
     * deposit $1,000.00
     * deposit $500.00
     * Total $1,500.00
     * Total In All Accounts $6,000.00
     */
    @Test
    public void testTransfer() throws Exception {
    	Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);
        savingsAccount.deposit(5000.0);
        checkingAccount.deposit(1000.0);
    	Customer oscar = 
    			 new Customer("Oscar").openAccount(savingsAccount);
        oscar.openAccount(checkingAccount);
        assertEquals(2, oscar.getNumberOfAccounts());
        
        oscar.transfer(savingsAccount, checkingAccount, 500.0);
        
        System.out.println(oscar.getStatement());       
    }
    
    
    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Ignore
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
}
