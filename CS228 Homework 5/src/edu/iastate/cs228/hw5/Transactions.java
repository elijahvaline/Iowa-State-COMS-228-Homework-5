package edu.iastate.cs228.hw5;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *  
 * @author Elijah Valine
 *
 */

/**
 * 
 * The Transactions class simulates video transactions at a video store.
 *
 */
public class Transactions {

	/**
	 * The main method generates a simulation of rental and return activities.
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) throws FileNotFoundException {
		
		int state = 1;
		int num;
		boolean running = true;

		String multi;
		Scanner sc = new Scanner(System.in);
		VideoStore v = new VideoStore("test1.txt");

		System.out.println("Transactions at a Video Store");
		System.out.println("keys: 1 (rent)     2 (bulk rent)");
		System.out.println("      3 (return)   4 (bulk return)");
		System.out.println("      5 (summary)  6 (exit)" + "\n \n");

		//This just uses a switch statement, reads the user input for the correct transaction and switches to 
		//the correct case.  Then it calls the corresponding method and handles the exceptions, printing
		//the exception message if necessary.
		while (running == true) {
			multi = "";
			System.out.print("Transaction: ");
			state = sc.nextInt();

			switch (state) {

			case 1:
				System.out.print("Film to rent: ");
				multi = sc.nextLine();
				multi = sc.nextLine();
				num = VideoStore.parseNumCopies(multi);
				multi = VideoStore.parseFilmName(multi);

				try {
					v.videoRent(multi, num);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;

			case 2:
				System.out.print("Video file (rent): ");
				multi = sc.nextLine();
				multi = sc.nextLine();

				try {
					v.bulkRent(multi);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;

			case 3:
				System.out.print("Film to return: ");
				multi = sc.nextLine();
				multi = sc.nextLine();
				num = VideoStore.parseNumCopies(multi);
				multi = VideoStore.parseFilmName(multi);
				try {
					v.videoReturn(multi, num);
				} catch (Exception e) {

					System.out.println(e.getMessage());
				}
				break;

			case 4:
				System.out.print("Video file (return): ");
				multi = sc.nextLine();
				multi = sc.nextLine();

				try {
					v.bulkReturn(multi);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;

			case 5:
				System.out.println(v.transactionsSummary());
				break;

			case 6:
				running = false;
				break;
			}
			System.out.println();
		}

	}
}
