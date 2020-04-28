package edu.iastate.cs228.hw5;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class test {

	public static void main(String[] args) throws FileNotFoundException, IllegalArgumentException, FilmNotInInventoryException, AllCopiesRentedOutException {
		VideoStore v = new VideoStore("test1.txt");
		System.out.println(v.inventory.toString());
		System.out.println(VideoStore.parseFilmName("Hello There (15)"));

//		v.videoRent("Forrest Gump", 1);
//		String s = "";
//		v.videoRent("Slumdog Millionaire", 2);
//		System.out.println("\n \n");
//		System.out.println(v.transactionsSummary());
//		System.out.println(VideoStore.parseNumCopies("Slumdog Millionaire (20)"));
//		System.out.println(s.length());
//		System.out.println(v.inventoryList());	
//		String test = "hello test (15)";
//		Scanner sc = new Scanner(test);
//		System.out.println(sc.next());
//		System.out.println(v.inventory.toString());
//		System.out.println("\n \n \n");
		

//		v.bulkImport("test1.txt");
		System.out.println(v.inventory.toString());
		try {
			v.bulkRent("test2.txt");	
		}
		catch(Exception e)  {
			System.out.println(e.getMessage());
		}
		
//		System.out.println(v.inventory.toString());
//		System.out.println("\n \n \n");
//		System.out.println(v.inventoryList());
//	
		

	}
}

