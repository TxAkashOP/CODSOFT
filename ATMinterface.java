package codsoft.task3;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ATMinterface {

	private List<User> users;
	private User currentUsers;

	static Scanner sc = new Scanner(System.in);

	public ATMinterface() {
		users = new ArrayList<User>();
		loadUsers(); // Load users from file when the ATM starts
	}

	private void loadUsers() {
		try {
			FileInputStream fis = new FileInputStream("users.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);

			users = (List<User>) ois.readObject();
			ois.close();
			fis.close();
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Looks like it's your first time here! Initializing a fresh session.");
		}
	}

	public void saveUsers() {
		try {
			FileOutputStream fos = new FileOutputStream("users.ser");
			ObjectOutputStream out = new ObjectOutputStream(fos);

			out.writeObject(users);
			out.close();
			fos.close();

		} catch (IOException i) {
			System.out.println("Oops! Something went wrong while saving your data. Please try again.");
			i.printStackTrace();
		}
	}

	public void registerUsers() {
		System.out.println("Enter Username : ");
		String username = sc.nextLine();

		if (findUser(username) != null) {
			System.out.println("Username already exists. Please choose a different username.");
			return;
		}

		int pin = 0;
		while (true) {
			System.out.print("Enter PIN (4 digits): ");
			if (sc.hasNextInt()) {
				pin = sc.nextInt();
				// Validate if the PIN is a 4-digit number
				if (pin >= 1000 && pin <= 9999) {
					break;
				} else {
					System.out.println("Invalid PIN. Please enter a 4-digit PIN.");
				}
			} else {
				System.out.println("Invalid input. Please enter a valid 4-digit number for the PIN.");
				sc.next();
			}
		}

		double balance = 0.0;
		while (true) {
			System.out.println("How much would you like to deposit to get started?");
			if (sc.hasNextDouble()) {
				balance = sc.nextDouble();

				if (balance >= 0) {
					break;
				} else {
					System.out.println("Initial deposit cannot be negative.");
				}
			} else {
				System.out.println("Invalid amount. Please enter a valid amount");
				sc.next();
			}
		}

		User newUser = new User(username, pin, balance);
		users.add(newUser);
		saveUsers();
		System.out.println("Registration successful! Welcome, " + username);
	}

	private User findUser(String username) {
		for (User user : users) {
			if (user.getUsername().equals(username)) {
				return user;
			}
		}
		return null;
	}

	public User login() {
		System.out.println("Enter Username: ");
		String username = sc.nextLine();
		sc.nextLine();
		User user = findUser(username);

		if (user != null) {

			System.out.println("Enter PIN: ");
			int pin = sc.nextInt();

			sc.nextLine();

			if (user.checkPin(pin)) {
				System.out.println("Login Successful! Welcome " + username);
				return user;
			} else {
				System.out.println("Invalid PIN. Please try again.");
				return null;
			}
		} else {
			System.out.println("User not found. Please register first.");
		}

		return null;
	}

	public void checkBalance(User user) {
		System.out.println("Enter PIN : ");
		int enteredPin = sc.nextInt();

		if (user.checkPin(enteredPin)) {
			System.out.println("Your balance is ₹" + user.getBalance());
		} else {
			System.out.println("Invalid PIN.");
		}
	}

	private void withdrawMoney(User user) {
		System.out.println("Enter PIN : ");
		int enteredpin = sc.nextInt();

		if (user.checkPin(enteredpin)) {
			System.out.print("Please enter the amount you would like to withdraw: ");
			double amount = sc.nextDouble();

			if (amount < user.getBalance()) {
				user.setBalance(user.getBalance() - amount);

				String transaction = "Withdrawn: $" + amount;
				user.addTransaction(transaction);

				System.out.println(
						"Your withdrawal of ₹" + amount + " was successful. Your new balance is ₹" + user.getBalance());
			} else {
				System.out.println("Insufficient balance. You cannot withdraw this amount.");
			}
		} else {
			System.out.println("Error: Incorrect PIN. The transaction has been canceled.");
		}
	}

	private void depositMoney(User user) {
		System.out.println("Enter PIN : ");
		int enteredPin = sc.nextInt();
		sc.nextLine();

		if (user.checkPin(enteredPin)) {
			System.out.print("Please enter the amount you would like to deposit: ");
			double amount = sc.nextDouble();
			sc.nextLine();

			if (amount > 0) {
				user.setBalance(user.getBalance() + amount);

				String transaction = "Deposited: ₹" + amount;
				user.addTransaction(transaction);

				System.out.println(
						"Your deposit of ₹" + amount + " was successful. Your new balance is ₹" + user.getBalance());

			} else {
				System.out.println("Deposit amount must be positive.");
			}
		} else {
			System.out.println("Error: Incorrect PIN. The transaction has been canceled.");
		}
	}

	private void viewTransactionHistory(User user) {
		List<String> transactionHistory = user.getTransactionHistory();

		if (transactionHistory.isEmpty()) {
			System.out.println("No transactions found.");
		} else {
			System.out.println("Transaction History : ");
			for (String transaction : transactionHistory) {
				System.out.println(transaction);
			}
		}
	}

	public void displayMenu(User user) {
		int choice;
		do {
			System.out.println("\nATM Menu:");
			System.out.println("1. Check Balance");
			System.out.println("2. Deposit Money");
			System.out.println("3. Withdraw Money");
			System.out.println("4. View Transaction History");
			System.out.println("5. Change Pin");
			System.out.println("6. Exit");

			System.out.print("Choose an option: ");
			choice = sc.nextInt();

			switch (choice) {
			case 1:
				checkBalance(user);
				break;
			case 2:
				depositMoney(user);
				break;
			case 3:
				withdrawMoney(user);
				break;
			case 4:
				viewTransactionHistory(user);
				break;
			case 5:
				changePin(user);
				break;
			case 6:
				System.out.println("Exiting the system. Goodbye!");
//				sc.close();
				return;
			default:
				System.out.println("Invalid option. Please try again.");
			}
		} while (choice != 6);
	}

	private void changePin(User user) {
		// Ask the user to enter their current PIN for authentication
		System.out.print("Enter your current PIN: ");
		int enteredPin = sc.nextInt();
		sc.nextLine(); // Consume newline character after integer input

		// Check if the entered PIN is correct
		if (user.checkPin(enteredPin)) {
			// Ask for the new PIN
			int newPin = 0;
			while (true) {
				System.out.print("Enter your new PIN (4 digits): ");
				if (sc.hasNextInt()) {
					newPin = sc.nextInt();
					sc.nextLine();

					if (newPin >= 1000 && newPin <= 9999) {
						break;
					} else {
						System.out.println("Invalid PIN. Please enter a 4-digit PIN.");
					}
				} else {
					System.out.println("Invalid input. Please enter a valid 4-digit number.");
					sc.next();
				}
			}

			System.out.print("Re-enter your new PIN: ");
			int confirmPin = sc.nextInt();
			sc.nextLine();

			if (newPin == confirmPin) {

				user.setPin(newPin);
				System.out.println("Your PIN has been successfully changed.");
			} else {
				System.out.println(
						"The PINs you entered don't match. Please check and enter the same PIN in both fields.");
			}
		} else {
			System.out
					.println("The current PIN is incorrect. Please enter the correct PIN to proceed with the change.");
		}
	}

}
