// --== CS400 Fall 2023 File Header Information ==--
// Name: Rishabh Jain
// Email: rvjain@wisc.edu
// Group: G18
// TA: Grant Waldow
// Lecturer: Florian

import java.util.ArrayList;

public interface ShortestPathInterface {
	/**
	 * This is the getter method for the segmented path along the shortestPath.
	 * This method returns a list of buildings along the shortest path between two
	 * locations.
	 * 
	 * @return a ArrayList of string that represents a list of buildings.
	 */
	ArrayList<String> path();

	/**
	 * This method returns a list of walking time for path segments which is the
	 * time it takes to walk from one building to the next.
	 * 
	 * @return a ArrayList of Integer that represents the list of walking time for
	 *         path segments
	 */
	ArrayList<Integer> walkingTime();

	/**
	 * This method return the total path cost as the estimated time it takes to walk
	 * from the start to the destination building.
	 * 
	 * @return an integer that represents the total path cost.
	 */
	int totalCost();
}
