package codsoft.task3;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
	private String username;
	private int pin;
	private double balance;
	private List<String> transactionHistory;
	
	public User(String username,int pin,double balance)
	{
		this.username = username;
		this.pin = pin;
		this.balance = balance;
		this.transactionHistory = new ArrayList<String>();
	}
	
	 public String getUsername() {
		return username;
	}
	 
	 public boolean checkPin(int pin) {
		return this.pin == pin;
	}
	
	 public double getBalance() {
		return balance;
	}
	 
	 public void setBalance(double balance) {
		this.balance = balance;
	}
	 
	 public List<String> getTransactionHistory() {
		return transactionHistory;
	}
	 
	 public void  addTransaction(String transaction)
	 {
		 transactionHistory.add(transaction);
	 }
	 
	 public void setPin(int newPin) {
		this.pin = newPin;
	}
}
