package edu.iastate.cs228.hw5;


import java.io.FileNotFoundException;
import java.util.Scanner; 

/**
 *  
 * @author
 *
 */

/**
 * 
 * The Transactions class simulates video transactions at a video store. 
 *
 */
public class Transactions 
{
	
	/**
	 * The main method generates a simulation of rental and return activities.  
	 *  
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException
	{	
		// TODO 
		// 
		// 1. Construct a VideoStore object.
		// 2. Simulate transactions as in the example given in Section 4 of the 
		//    the project description. 
		int numTrans = 1;
		int state = 1;
		boolean running = true;
		String f ;
		Scanner sc = new Scanner(System.in);
		
		VideoStore v = new VideoStore("test1.txt");
		
		
		
		System.out.println("Transactions at a Video Store");
		System.out.println("keys: 1 (rent)     2 (bulk rent)");
		System.out.println("      3 (return)   4 (bulk return)");
		System.out.println("      5 (summary)  6 (exit)" + "\n \n");
		
		
		
		while(running == true) {
			System.out.print("Transaction: ");
			state = sc.nextInt();
			
			switch (state){
			case 1:
				break;
			case 2: 
				break;
			case 3: 
				break;
			case 4:
				break;
			case 5:
				break;
			case 6:
				running = false;
				break;
				
				
			}
			
			
			
		}
		
		
		
		
		
		
	}
}
