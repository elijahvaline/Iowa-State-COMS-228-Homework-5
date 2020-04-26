package edu.iastate.cs228.hw5;

import java.io.FileNotFoundException;

public class test {

	public static void main(String[] args) throws FileNotFoundException {
		VideoStore v = new VideoStore("test1.txt");
		System.out.println(v.inventory.toString());
		System.out.println(v.findVideo("Slumdog Millionaire").toString());
		

	}

}
