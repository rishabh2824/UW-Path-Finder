// --== CS400 Fall 2023 File Header Information ==--
// Name: Nicolas Hurtado
// Email: nhurtado@wisc.edu
// Group: G18
// TA: Grant Waldow
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>
import java.io.FileNotFoundException;

/**
 * This is my individual version of the frontend interface which includes
 * all the methods that are asked for in the document which
 */
public interface FrontendInterface {
    /**
     * This method starts the loop for the user interaction
     */
    void startCommandLoop() throws FileNotFoundException;

    /**
     * This method lets the user input the name of the file and sends it to the backend
     * to get information that will be loaded on the frontend
     */
    void loadDataFile();

    /**
     * This method helps display the statistics of the dataset as a whole which
     * includes things such as number of people, number of friendships and average number
     * of friends
     */
    void displayData();

    /**
     * This method uses 2 friends as inputs and then displays the shortest
     * path which is its closest connection
     * @param origin building you are going out from
     * @param destination building you are trying to get to
     */
    void displayClosestConnection(String origin, String destination);

    /**
     * This method ends the loop and exists out of the app
     */
    void exit();

}
