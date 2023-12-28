// --== CS400 Fall 2023 File Header Information ==--
// Name: Rishabh Jain
// Email: rvjain@wisc.edu
// Group: G18
// TA: Grant Waldow
// Lecturer: Florian

import java.io.FileNotFoundException;

/**
 * This interface is the Backend Role for project2: UW Path Finder. It has
 * methods that read DOT file with name provided by frontend, find the shortest
 * path between two location and return string that gives information about the
 * dataset. It takes an instance of the GraphADT.
 * 
 * @author Rishabh Jain
 *
 */
public interface BackendInterface {

	/**
	 * This method read data from a DOT file when provided with the name of this
	 * file. This method will throw an FileNotFoundException if there is any trouble
	 * loading the file based on the name provided. The method should store the data
	 * into the GraphADT structure.
	 * 
	 * @param fileName: the name of file this method is loading
	 * @throws FileNotFoundException: throw exception when the file name cannot be
	 *                                found
	 */
	void readData(String fileName) throws FileNotFoundException;

	/**
	 * This method gets the shortest path from a start to a destination building in
	 * the dataset. The method takes in the start and end location as string.
	 * 
	 * @param start:       name of the starting building
	 * @param destination: name of the destination building
	 * @throws IllegalArgumentException: this method throws IllegalArgumentException
	 *                                   if the start or end location provided
	 *                                   cannot be found in the dataset
	 */
	ShortestPathInterface shortestPath(String start, String destination) throws IllegalArgumentException;

	/**
	 * This method return a String representation of the dataset. The string will
	 * include a list of information: the number of buildings (nodes), the number of
	 * edges, and total walking time (sum of weights) for all edges in the graph.
	 * 
	 * @return a String that contains information about this dataset
	 */
	String toString();
}
