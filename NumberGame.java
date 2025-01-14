package codsoft.task1;

import java.util.Scanner;

public class NumberGame 
{
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		boolean playAgain = true;
		int totalScore = 0;
		int totalRounds = 0;
		
		while(playAgain)
		{
			int myNum = (int)(Math.random()*100) + 1;
			int userNumber = 0;
			int attempts = 0;
			int maxattempts = 10;
			boolean roundwon = false;
			
			totalRounds++;
			
			System.out.println("Welcome to the number guessing game ");
			System.out.println("You can guess any number between 1-100 and you only have "+maxattempts +" attempts,Let's Try to Guess it..");
			
			while(userNumber != myNum && attempts < maxattempts)
			{
				System.out.println("Guess the number(1-100)");
				userNumber = sc.nextInt();
				attempts++;
				
				if(userNumber < myNum)
				{
					System.out.println("Too low!.. Try again.");
				}
				else if(userNumber > myNum)
				{
					System.out.println("Too high!.. Try again.");
				}
				else
				{
					roundwon = true;
					System.out.println("Congrats!.. You've guessed the number correctly.");
					System.out.println("You took "+attempts+" attempts");
					break;
				}
				
				if(attempts == maxattempts)
				{
					System.out.println("Sorry,You've reached the maximum number of attempts.");
					System.out.println("The correct number was "+myNum);
				}
			}
			
			if(roundwon)
			{
				int score = Math.max(0, maxattempts - attempts + 1);
				totalScore += score;
				
				System.out.println("You scored "+(score)+" points for this round.");
				
			}
			else
			{
				System.out.println("No points in this round.");
			}
			
			System.out.println("Your total score is "+(totalScore)+" points after "+totalRounds+" rounds.");
			
			System.out.println("Do you want to play another round ? (Yes/No) : ");
			String resonse = sc.next();
			
			if(resonse.equalsIgnoreCase("no"))
			{
				playAgain = false;
				System.out.println("Thanks for playing! Your final score is "+totalScore+" points");
			}
			
			
		}
		
		
		
		sc.close();
	}

}
