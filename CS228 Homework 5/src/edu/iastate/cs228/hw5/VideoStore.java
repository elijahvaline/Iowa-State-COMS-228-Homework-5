package edu.iastate.cs228.hw5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import edu.iastate.cs228.hw5.SplayTree.Node;

/**
 * 
 * @author Elijah Valine
 *
 */

public class VideoStore {
	protected SplayTree<Video> inventory; // all the videos at the store
	private Scanner s;
	private Scanner sc;

	// ------------
	// Constructors
	// ------------

	/**
	 * Default constructor sets inventory to an empty tree.
	 */
	public VideoStore() {
		// no need to implement.
	}

	/**
	 * Constructor accepts a video file to create its inventory. Refer to Section
	 * 3.2 of the project description for details regarding the format of a video
	 * file.
	 * 
	 * Calls setUpInventory().
	 * 
	 * @param videoFile no format checking on the file
	 * @throws FileNotFoundException
	 */
	public VideoStore(String videoFile) throws FileNotFoundException {

		inventory = new SplayTree<Video>();
		setUpInventory(videoFile);
	}

	/**
	 * Accepts a video file to initialize the splay tree inventory. To be efficient,
	 * add videos to the inventory by calling the addBST() method, which does not
	 * splay.
	 * 
	 * Refer to Section 3.2 for the format of video file.
	 * 
	 * @param videoFile correctly formated if exists
	 * @throws FileNotFoundException
	 */
	public void setUpInventory(String videoFile) throws FileNotFoundException {

		int copies = 1;

		File f = new File(videoFile);
		String nextLine;
		String title = "";
		s = new Scanner(f);

		while (s.hasNextLine()) {
			nextLine = s.nextLine();
			//Checks if line is empty
			if (nextLine.trim().length() == 0) {

			} else {
				//Parses title and copies and adds to inventory
				title = VideoStore.parseFilmName(nextLine);
				copies = VideoStore.parseNumCopies(nextLine);
				inventory.addBST(new Video(title, copies));
			}
		}
	}

	// ------------------
	// Inventory Addition
	// ------------------

	/**
	 * Find a Video object by film title.
	 * 
	 * @param film
	 * @return The video with the corresponding name.
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	public Video findVideo(String film) {

		int j;

		String curr, prev;
		Node cur = inventory.root;
		curr = ((Video) cur.data).getFilm();

		//Uses the binary search algorithm to search through each node. I realize now i made this way harder
		//than it had to be.
		for (int i = 0; i < inventory.size - 1; i++) {
			j = film.compareTo(curr);
			if (j < 0) {
				if (cur.left == null) {
					return null;
				}
				cur = cur.left;
				curr = ((Video) cur.data).getFilm();
			} else if (j > 0) {
				if (cur.right == null) {
					return null;
				}
				cur = cur.right;
				curr = ((Video) cur.data).getFilm();
			} else {
				return (Video) cur.data;
			}
		}
		return (Video) cur.data;
	}

	/**
	 * Updates the splay tree inventory by adding a number of video copies of the
	 * film. (Splaying is justified as new videos are more likely to be rented.)
	 * 
	 * Calls the add() method of SplayTree to add the video object.
	 * 
	 * a) If true is returned, the film was not on the inventory before, and has
	 * been added. b) If false is returned, the film is already on the inventory.
	 * 
	 * The root of the splay tree must store the corresponding Video object for the
	 * film. Update the number of copies for the film.
	 * 
	 * @param film title of the film
	 * @param n    number of video copies
	 */
	public void addVideo(String film, int n) {

		boolean x = inventory.add(new Video(film, n));

		if (x == false) {
			inventory.root.data.addNumCopies(n);
		}
	}

	/**
	 * Add one video copy of the film.
	 * 
	 * @param film title of the film
	 */
	public void addVideo(String film) {
		
		boolean x = inventory.add(new Video(film));

		if (x == false) {
			inventory.root.data.addNumCopies(1);
		}
	}

	/**
	 * Update the splay trees inventory by adding videos. Perform binary search
	 * additions by calling addBST() without splaying.
	 * 
	 * The videoFile format is given in Section 3.2 of the project description.
	 * 
	 * @param videoFile correctly formated if exists
	 * @throws FileNotFoundException
	 */
	public void bulkImport(String videoFile) throws FileNotFoundException {
		
		File f = new File(videoFile);
		String nextLine;
		int copies = 1;
		String title = "";
		String next;
		s = new Scanner(f);
		ArrayList<Video> vids = new ArrayList<Video>();

		while (s.hasNextLine()) {
			//Goes line by line manually parsing the line and adding it to an arraylist. I made this before
			//I made the other dedicated parsing methods.
			nextLine = s.nextLine();
			sc = new Scanner(nextLine);
			while (sc.hasNext()) {
				next = sc.next();
				if (next.length() == 1) {
					title += next + " ";
				} else if (isNum(next.charAt(1)) == false) {

					title += next + " ";
				} else if (isNum(next.charAt(1)) == true) {
					copies = Integer.parseInt(next.substring(1, next.length() - 1) + "");
				}
			}
			title = title.substring(0, title.length() - 1);
			vids.add(new Video(title, copies));

			copies = 1;
			title = "";
		}
		//Using the arraylist adds the videos to the inventory.
		for (int i = 0; i < vids.size(); i++) {
			Video curr = vids.get(i);
			boolean x = inventory.addBST(curr);

			if (x == false) {
				Video cur;
				cur = findVideo(curr.getFilm());
				cur.addNumCopies(vids.get(i).getNumCopies());
			}
		}
	}

	// ----------------------------
	// Video Query, Rental & Return
	// ----------------------------

	/**
	 * Search the splay tree inventory to determine if a video is available.
	 * 
	 * @param film
	 * @return true if available
	 */
	public boolean available(String film) {
		Video curr = findVideo(film);

		if (curr.getNumAvailableCopies() == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Update inventory.
	 * 
	 * Search if the film is in inventory by calling findElement(new Video(film,
	 * 1)).
	 * 
	 * If the film is not in inventory, prints the message "Film <film> is not in
	 * inventory", where <film> shall be replaced with the string that is the value
	 * of the parameter film. If the film is in inventory with no copy left, prints
	 * the message "Film <film> has been rented out".
	 * 
	 * If there is at least one available copy but n is greater than the number of
	 * such copies, rent all available copies. In this case, no
	 * AllCopiesRentedOutException is thrown.
	 * 
	 * @param film
	 * @param n
	 * @throws IllegalArgumentException    if n <= 0 or film == null or
	 *                                     film.isEmpty()
	 * @throws FilmNotInInventoryException if film is not in the inventory
	 * @throws AllCopiesRentedOutException if there is zero available copy for the
	 *                                     film.
	 */
	public void videoRent(String film, int n)
			throws IllegalArgumentException, FilmNotInInventoryException, AllCopiesRentedOutException {
		//Handles the exceptions and then rents video if it passes inspection.
		Video curr = inventory.findElement(new Video(film, 1));
		
		if (curr == null) {
			throw new FilmNotInInventoryException("Film " + film + " is not in inventory");
		}
		if (film == null || n <= 0 || film.isEmpty()) {
			throw new IllegalArgumentException("Film " + film + " has an invalid request");
		}
		if (curr.getNumAvailableCopies() == 0) {
			throw new AllCopiesRentedOutException("Film " + film + " has been rented out");
		}

		if (curr.getNumAvailableCopies() > 0) {
			if (n > curr.getNumAvailableCopies())
				curr.rentCopies(curr.getNumAvailableCopies());
			else
				curr.rentCopies(n);
		}
	}

	/**
	 * Update inventory.
	 * 
	 * 1. Calls videoRent() repeatedly for every video listed in the file. 2. For
	 * each requested video, do the following: a) If it is not in inventory or is
	 * rented out, an exception will be thrown from videoRent(). Based on the
	 * exception, prints out the following message: "Film <film> is not in
	 * inventory" or "Film <film> has been rented out." In the message, <film> shall
	 * be replaced with the name of the video. b) Otherwise, update the video record
	 * in the inventory.
	 * 
	 * For details on handling of multiple exceptions and message printing, please
	 * read Section 3.4 of the project description.
	 * 
	 * @param videoFile correctly formatted if exists
	 * @throws FileNotFoundException
	 * @throws IllegalArgumentException    if the number of copies of any film is <=
	 *                                     0
	 * @throws FilmNotInInventoryException if any film from the videoFile is not in
	 *                                     the inventory
	 * @throws AllCopiesRentedOutException if there is zero available copy for some
	 *                                     film in videoFile
	 */
	public void bulkRent(String videoFile) throws FileNotFoundException, IllegalArgumentException,
			FilmNotInInventoryException, AllCopiesRentedOutException {


		int priority = 10;
		int copies = 1;
		
		String nextLine;
		String exceptions = "";
		String title = "";
		Boolean first = true;
		File f = new File(videoFile);
		s = new Scanner(f);
		
		//This just parses each line and rents it while catching exceptions.Then it throws the new exceptions
		//if necessary.
		
		while (s.hasNextLine()) {

			nextLine = s.nextLine();
			if (nextLine.trim().length() == 0) {

			} else {

				title = VideoStore.parseFilmName(nextLine);
				copies = VideoStore.parseNumCopies(nextLine);

				try {
					videoRent(title, copies);
				} catch (IllegalArgumentException e) {
					if (first) {
						first = false;
						exceptions += e.getMessage();
					} else
						exceptions += "\n" + e.getMessage();
					priority = 1;
				} catch (FilmNotInInventoryException e) {
					if (first) {
						first = false;
						exceptions += e.getMessage();
					} else
						exceptions += "\n" + e.getMessage();
					if (2 < priority) {
						priority = 2;
					}
				} catch (AllCopiesRentedOutException e) {
					if (first) {
						first = false;
						exceptions += e.getMessage();
					} else
						exceptions += "\n" + e.getMessage();
					if (3 < priority) {
						priority = 3;
					}
				}
			}
		}
		
		if (exceptions.length() > 0) {
			switch (priority) {
			case 1:
				throw new IllegalArgumentException(exceptions);
			case 2:
				throw new FilmNotInInventoryException(exceptions);
			case 3:
				throw new AllCopiesRentedOutException(exceptions);
			}

		}

	}

	/**
	 * Update inventory.
	 * 
	 * If n exceeds the number of rented video copies, accepts up to that number of
	 * rented copies while ignoring the extra copies.
	 * 
	 * @param film
	 * @param n
	 * @throws IllegalArgumentException    if n <= 0 or film == null or
	 *                                     film.isEmpty()
	 * @throws FilmNotInInventoryException if film is not in the inventory
	 */
	public void videoReturn(String film, int n) throws IllegalArgumentException, FilmNotInInventoryException {
		
		//Same idea as videoRent. Handes exceptions, and if it passes then returns the video.
		if (n <= 0) {
			throw new IllegalArgumentException("Film " + film + " has an invalid request");
		}
		if (inventory.contains(new Video(film, 1)) == false) {

			throw new FilmNotInInventoryException("Film " + film + " is not in the inventory");
		}
		Video curr = inventory.findElement(new Video(film, 1));

		if (n > curr.getNumRentedCopies()) {
			curr.returnCopies(curr.getNumRentedCopies());

		} else {
			curr.returnCopies(n);
		}
	}

	/**
	 * Update inventory.
	 * 
	 * Handles excessive returned copies of a film in the same way as videoReturn()
	 * does. See Section 3.4 of the project description on how to handle multiple
	 * exceptions.
	 * 
	 * @param videoFile
	 * @throws FileNotFoundException
	 * @throws IllegalArgumentException    if the number of return copies of any
	 *                                     film is <= 0
	 * @throws FilmNotInInventoryException if a film from videoFile is not in
	 *                                     inventory
	 */
	public void bulkReturn(String videoFile)
			throws FileNotFoundException, IllegalArgumentException, FilmNotInInventoryException {

		int copies = 1;
		int priority = 10;
		
		String nextLine;
		String title = "";
		String exceptions = "";
		Boolean first = true;
		File f = new File(videoFile);
		s = new Scanner(f);

		
		//Same idea as the last couple methods. Parses the text file, and then returns video while catching
		//catching exceptions. Then it throw new exceptions if necessary.
		while (s.hasNextLine()) {

			nextLine = s.nextLine();
			title = VideoStore.parseFilmName(nextLine);
			copies = VideoStore.parseNumCopies(nextLine);

			try {
				videoReturn(title, copies);
			} catch (IllegalArgumentException e) {
				if (first) {
					first = false;
					exceptions += e.getMessage();
				} else
					exceptions += "\n" + e.getMessage();
				priority = 1;
			} catch (FilmNotInInventoryException e) {
				if (first) {
					first = false;
					exceptions += e.getMessage();
				} else
					exceptions += "\n" + e.getMessage();
				priority = 2;

			}
		}

		if (exceptions.length() > 0) {
			switch (priority) {
			case 1:
				throw new IllegalArgumentException(exceptions);
			case 2:
				throw new FilmNotInInventoryException(exceptions);
			}
		}
	}

	// ------------------------
	// Methods without Splaying
	// ------------------------

	/**
	 * Performs inorder traversal on the splay tree inventory to list all the videos
	 * by film title, whether rented or not. Below is a sample string if printed
	 * out:
	 * 
	 * 
	 * Films in inventory:
	 * 
	 * A Streetcar Named Desire (1) Brokeback Mountain (1) Forrest Gump (1) Psycho
	 * (1) Singin' in the Rain (2) Slumdog Millionaire (5) Taxi Driver (1) The
	 * Godfather (1)
	 * 
	 * 
	 * @return A string containing all of the movies in the inventory.
	 */
	public String inventoryList() {

		//a list of all the movies in the inventory. Just iterates through the whole tree.
		String returner = "";
		Iterator<Video> iter = inventory.iterator();
		
		while (iter.hasNext()) {
			returner += iter.next().toString() + "\n";

		}
		return returner;
	}

	/**
	 * Calls rentedVideosList() and unrentedVideosList() sequentially. For the
	 * string format, see Transaction 5 in the sample simulation in Section 4 of the
	 * project description.
	 * 
	 * @return A summary of both rented videos and unrented videos.
	 */
	public String transactionsSummary() {
		return rentedVideosList() + unrentedVideosList();
	}

	/**
	 * Performs inorder traversal on the splay tree inventory. Use a splay tree
	 * iterator.
	 * 
	 * Below is a sample return string when printed out:
	 * 
	 * Rented films:
	 * 
	 * Brokeback Mountain (1) Forrest Gump (1) Singin' in the Rain (2) The Godfather
	 * (1)
	 * 
	 * 
	 * @return A string containing all of the rented videos.
	 */
	private String rentedVideosList() {
		
		Iterator<Video> iter = inventory.iterator();
		
		String returner = "Rented Films:" + "\n \n \n";
		Video curr;
		
		//If a video has been rented, adds that information to a string and returns it.
		while (iter.hasNext()) {
			curr = iter.next();
			if (curr.getNumRentedCopies() == 0) {

			} else {
				returner += curr.getFilm() + " " + "(" + curr.getNumRentedCopies() + ")" + "\n";
			}
		}
		return returner + "\n \n";
	}

	/**
	 * Performs inorder traversal on the splay tree inventory. Use a splay tree
	 * iterator. Prints only the films that have unrented copies.
	 * 
	 * Below is a sample return string when printed out:
	 * 
	 * 
	 * Films remaining in inventory:
	 * 
	 * A Streetcar Named Desire (1) Forrest Gump (1) Psycho (1) Slumdog Millionaire
	 * (4) Taxi Driver (1)
	 * 
	 * 
	 * @return A string containing all of the unrented videos.
	 */
	private String unrentedVideosList() {
		
		
		Iterator<Video> iter = inventory.iterator();
		
		String returner = "Films remaining in inventory: " + "\n \n \n";
		Video curr;
		
		//Same idea as the last method. If a video is unrented, adds it to a string.
		while (iter.hasNext()) {
			curr = iter.next();
			if (curr.getNumAvailableCopies() == 0) {

			} else {
				returner += curr.getFilm() + " " + "(" + curr.getNumAvailableCopies() + ")" + "\n";
			}
		}

		return returner;

	}

	/**
	 * Parse the film name from an input line.
	 * 
	 * @param line
	 * @return The title
	 */
	@SuppressWarnings("resource")
	public static String parseFilmName(String line) {
		
		String next;
		String title = "";
		Scanner sc = new Scanner(line);
		
		//Scans through the line, if it is a number in parents removes it.
		while (sc.hasNext()) {
			next = sc.next();
			if (next.length() == 1) {
				title += next + " ";
			} else if (next.charAt(0) != '(') {

				title += next + " ";
			}
		}
		title = title.substring(0, title.length() - 1);
		return title;
	}

	/**
	 * Parse the number of copies from an input line.
	 * 
	 * @param line
	 * @return The number of copies.
	 */
	@SuppressWarnings("resource")
	public static int parseNumCopies(String line) {
		
		int copies = 1;
		
		Scanner sc = new Scanner(line);
		String next = "";

		//Oposite of the last method. Number in parents gets parsed to an int.
		while (sc.hasNext()) {
			next = sc.next();
			if (next.charAt(0) == '(') {
				copies = Integer.parseInt(next.substring(1, next.length() - 1) + "");
			}
		}
		return copies;
	}

	/**
	 * Static method used to determine if a character is an integer.
	 * @param c A character
	 * @return a boolean whether or not it is an integer.
	 */
	@SuppressWarnings("unused")
	private static boolean isNum(Character c) {

		//This just checks if a character is an integer.
		if (c == null) {
			return false;
		}
		try {
			
			int i = Integer.parseInt(c + "");
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}
