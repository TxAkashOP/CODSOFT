package codsoft.task3;

import java.util.Scanner;

public class AtmDriver {
	public static void main(String[] args) {
		ATMinterface atm = new ATMinterface();

		Scanner sc = new Scanner(System.in);

		// ATM welcome message
		System.out.println("Welcome to the ATM System!");

		while (true) {
			System.out.println("\n1. Register");
			System.out.println("2. Login");
			System.out.println("3. Exit");

			System.out.print("Choose an option: ");
			int choice = sc.nextInt();

			switch (choice) {
			case 1:
				atm.registerUsers(); // Register a new user
				break;
			case 2:
				User user = atm.login(); // Login an existing user
				if (user != null) { 
					atm.displayMenu(user); // Display ATM menu if login is successful
				}
				break;
			case 3:
				System.out.println("You’ve been successfully logged out. Thank you for your time. Goodbye!");
				sc.close();
				return; // Exit the program
			default:
				System.out.println("Invalid choice. Please try again.");
			}
		}
	}

}
