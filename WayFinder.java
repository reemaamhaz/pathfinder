package project5;

import java.util.*;
import java.io.*;
import java.lang.*;

/**
 * WayFinder class is used to get from the beginning of an array to the end.
 * It validates the input and creates an ArrayList to be modified by the solver function
 * to indicate the direction in the array that it is going. It prints the possible paths
 * and keeps count of the possible solutions.  
 * 
 * @author Reema Amhaz
 *
 */

public class WayFinder
{
    /**
	 * Validate there are arguments and filter out the invalid arguments from the user input. 
	 * Creates an ArrayList and adds the valid arguments to list.
     * Creates a boolean array to keep track of which arguments were visited and which ones weren't to prevent duplicates. 
     * 
     * Makes calls to the solver function with the valid arguments and count function to process the number of solutions. 
	 * @throws NumberFormatException if the input is not a number/invalid
     * @param args - command line arguments 
     * 
	 */
    public static void main(String[] args)
    {
        if (args.length == 0) // this exits the system when there is no command line arg provided
        {
            System.err.println("ERROR: incorrect usage. At least one argument is required.\n");
            System.exit(1);
        }

        try
        {
            ArrayList<String> result = new ArrayList<String>();  // creates a new arraylist
            int end = args.length;

            for (int i = 0; i < end; i++)
            {
                if (Integer.parseInt(args[i]) > 99 || Integer.parseInt(args[i]) < 0) // checks if each index element are in bounds
                {
                    System.err.println("ERROR: the puzzle values have to be positive integers in range [0, 99]."); // returns error msg if not
                    System.exit(1); // exits program
                }

                if (Integer.parseInt(args[end - 1]) != 0) // if the last arg isn't 0 
                {
                    System.err.println("ERROR: the last value must be 0."); // print error
                    System.exit(1); // exits program
                }
                result.add(String.format("%2d%s", Integer.parseInt(args[i]), " "));
            }  
                boolean[] visited = new boolean[result.size()]; //creates boolean for all indicies if visited
                int paths = solver(result, 0, visited); //calls solver function
                counter(paths);
        } 
        catch (NumberFormatException e) 
        {
            System.err.println("ERROR: the values must all be digits"); // print error
        }
    }

    /**
	 * Traverses the array by parsing the integers from the first index to the last recursively.
     * At the end of the array, it prints the path it took indicating the left or right direction.
     * It generates a new array by backtracking and attempting a different direction. 
	 * Space holders are included in the print statements to indicate a value was not visited or isn't a double digit.  
     * 
	 * @return int of number of paths 
     * @param result - Arraylist of Strings that contain integers 
     * @param i - current index
     * @param visited - boolean array of visited indicies 
     *
	 */
    private static int solver(ArrayList<String> result, int i, boolean[] visited)
    {
        int size = result.size() - 1; //size
        int current = Integer.parseInt(result.get(i).trim()); //gets current value at index 
        int left = i - current; //left move
        int right = i + current; //right move
        int sum = 0;

        //this is the base case
        if (i == size) // if the index is the last index in the array 
        {
            System.out.println(result);
            return 1; // adds to sum of patterns
        }

        if (size > 0 && current == 0)  //prevents infinite recursion
        {
            return 0;
        }

        if (right <= size && visited[right] == false) // if the right move is not out of bounds and it hasn't been visited
        {
            visited[i] = true; // has been visited
            result.set(i, String.format("%2d%s", current, "R")); // sets the value at current index to the value + direction R
            sum += solver(result, right, visited); // returns the array, index is the right index
            result.set(i, String.format("%2d%s", current, " ")); //deletes direction
        }

        if (left > 0 && visited[left] == false) // if the left move is not out of bounds and it hasn't been visited
        {
            visited[i] = true; // has been visited
            result.set(i, String.format("%2d%s", current, "L")); // sets the value at current index to the value + direction L
            sum += solver(result, left, visited); // returns the array, index is the left index
            result.set(i, String.format("%2d%s", current, " ")); //deletes direction
        }
        visited[i] = false; //resets boolean
        return sum;
    }

    /**
	 * Prints a statement indicating the number of paths based on what is returned by the solver function. 
     * 
     * @param x - number of paths
     * 
	 */
    private static void counter(int x)
    {    
        if(x == 0)  
        {
            System.out.println("No way through this puzzle.\n");
        }      
        if(x == 1)
        {
            System.out.println("There is 1 way through the puzzle.\n");
        }
        if (x > 1) 
        {
            System.out.println("There are " + x + " ways through the puzzle.\n");
        }
    }
}