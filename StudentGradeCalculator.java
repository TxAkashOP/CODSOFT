package codsoft.task2;

import java.util.Scanner;

public class StudentGradeCalculator {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int numOfSubjects = 0;

		while (true) {
			System.out.println("Please provide total number of subjects : ");

			if (sc.hasNextInt()) {
				numOfSubjects = sc.nextInt();

				if (numOfSubjects <= 0) {
					System.out.println("Invalid entry! Please provide positive number");
					return;
				} else {
					break;
				}
			} else {
				System.out.println("Invalid input! Please enter a numeric value.");
				sc.next();
			}

		}

		double[] marks = new double[numOfSubjects];
		double totalMarks = 0;

		boolean hasFailed = false;

		for (int i = 0; i < numOfSubjects; i++) {
			marks[i] = getMarks(sc, i + 1);
			totalMarks += marks[i];
		}

		for (int i = 0; i < numOfSubjects; i++) {
			if (marks[i] < 35) {
				hasFailed = true;
				System.out.println("\nYou've failed in subject " + (i + 1) + " (Marks : " + marks[i] + ").");
			}
		}

		if (hasFailed) {
			System.out.println("\nTotal Marks : " + totalMarks + "/" + (numOfSubjects * 100));
			System.out.println("Result : Fail");
			return;
		} else {
			double percentage = (totalMarks / (numOfSubjects * 100)) * 100;

			String formattedPercentage = String.format("%.2f", percentage);
			displayResult(totalMarks, numOfSubjects, percentage, formattedPercentage);
		}

	}

	public static double getMarks(Scanner sc, int subjectNum) {
		double marks;

		while (true) {
			System.out.println("Enter marks for subject " + subjectNum + " : ");
			if (sc.hasNextDouble()) {
				marks = sc.nextDouble();

				if (marks >= 0 && marks <= 100) {
					break;
				} else {
					System.out.println("Invalid marks! Please provide marks in range 0-100.");
				}
			} else {
				System.out.println("Invalid input! Please enter a numeric value.");
			}
		}

		  return marks;
	}

	public static void displayResult(double totalMarks, int numOfSubjects, double percentage,
			String formattedPercentage) {
		System.out.println("\nTotal Marks : " + totalMarks + "/" + (numOfSubjects * 100));
		System.out.println("Percentage : " + formattedPercentage + "%");

		String grade = getGrade(percentage);
		System.out.println("Grade : " + grade);
	}

	public static String getGrade(double percentage) {
		if (percentage >= 70) {
			return "A++";
		} else if (percentage >= 60 && percentage < 70) {
			return "A";
		} else if (percentage >= 50 && percentage < 60) {
			return "B";
		} else if (percentage >= 40 && percentage < 50) {
			return "C";
		} else if (percentage > 35 && percentage < 40) {
			return "D";
		} else {
			return "Fail";
		}

	}

}
