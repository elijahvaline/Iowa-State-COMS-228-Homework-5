package edu.iastate.cs228.hw5;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class test {

	public static void main(String[] args) throws FileNotFoundException, IllegalArgumentException, FilmNotInInventoryException, AllCopiesRentedOutException {
		VideoStore v = new VideoStore("test1.txt");
//		String test = "hello test (15)";
//		Scanner sc = new Scanner(test);
//		System.out.println(sc.next());
		System.out.println(v.inventory.toString());
		System.out.println("\n \n \n");
		

		v.bulkImport("test1.txt");
		System.out.println(v.inventory.toString());
		try {
			v.bulkRent("test2.txt");	
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		System.out.println(v.inventory.toString());
	
		

	}

}
