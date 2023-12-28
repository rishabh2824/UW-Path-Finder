// Group: G18
// TA: Grant Waldow
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>

import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;

public class Frontend implements FrontendInterface {
    protected final Scanner scnr;
    protected final BackendInterface backend;
    protected final File[] dotFiles;
    protected boolean runProgram = true;
    protected boolean hasLoadedFile = false;

    public Frontend(Scanner scnr, BackendInterface backend) {
        //Sets private fields
        this.scnr = scnr;
        this.backend = backend;

        //Gets all dot files in the current directory where this file resides
        File directory = new File(System.getProperty("user.dir"));
        dotFiles = directory.listFiles((dir, name) -> name.endsWith(".dot"));
    }


    /**
     * This method runs the program as a whole serving as the hub where all other functionalities
     * of the program will be called from.
     */
    @Override
    public void startCommandLoop() {
        try {
            System.out.println("Welcome to our interactive campus navigation application, " +
                    "designed to provide comprehensive insights and guidance for exploring " +
                    "our campus. As a user, you will engage with a dynamic interface, " +
                    "tailored to streamline your experience in accessing and understanding " +
                    "the campus layout. This application is meticulously crafted by our " +
                    "development team, ensuring an intuitive and " +
                    "user-friendly journey.\n");


            while (runProgram) {
                //Creates the main menu for the users commands
                System.out.println("""
                    [1] Choose a file to load data from
                    [2] Show Statistics of Data
                    [3] Find Shortest Path
                    [4] Quit Program
                    """
                );


                System.out.println("Please enter an integer for command:");
                int commandInt = getUserInt(4);
                System.out.println();

                //Branching point for functions
                switch (commandInt) {
                    case 1 -> loadDataFile();
                    case 2 -> {
                        if (!hasLoadedFile) {
                            System.out.println("Please load a file!\n\n");
                            break;
                        }
                        displayData();

                        //Waits for the user to leave the current printed statement and consumes
                        //that input
                        System.out.println("\n\nPlease enter any key to return to the menu.");
                        if (scnr.hasNext()) {
                            scnr.next();
                        }
                        System.out.println();
                    }
                    case 3 -> {
                        if (!hasLoadedFile) {
                            System.out.println("Please load a file!\n\n");
                            break;
                        }
                        //Poll user for two strings for the params of displayClosestConnection
                        System.out.println("Please enter your starting point:");
                        String origin = getUserString();
                        System.out.println();
                        System.out.println("Please enter your destination:");
                        String destination = getUserString();
                        displayClosestConnection(origin, destination);

                        //Waits for the user to leave the current printed statement and consumes
                        //that input
                        System.out.println("\n\nPlease enter any key to return to the menu.");
                        if (scnr.hasNext()) {
                            scnr.next();
                        }
                        System.out.println();
                    }
                    case 4 -> exit();
                    default -> throw new IllegalStateException();
                }
            }
        } catch (Exception e) {
            System.out.println("Sorry an error has occurred; Resetting program.");
            e.printStackTrace();
            //recursive call to "restart" program if unexpected error occurs
            startCommandLoop();
            runProgram = false;
        }
    }






    /**
     * This method lets the user input the name of the file and sends it to the backend
     * to get information that will be loaded on the frontend
     */
    @Override
    public void loadDataFile() {
        int fileInput;

        //No dot files found in the user's directory
        if (dotFiles.length == 0) {
            System.out.println("Your directory does not contain any .dot files. Please " +
                    "add/recheck your files to use this feature.\n");
            return;
        }

        //Presents the user with their dot files and asks which one they want loaded
        System.out.println("Please enter an integer for file selection: ");
        for (int i = 0; i < dotFiles.length; i++) {
            System.out.println(i + 1 + ". " + dotFiles[i]);
        }
        fileInput = getUserInt(dotFiles.length) - 1;


        //Catches FileNotFoundExceptions from the backend
        try {
            //Sends the proper file to the backend
            System.out.println("\nLoading File...");
            backend.readData(dotFiles[fileInput].toString());
            hasLoadedFile = true;
            System.out.println("File Loaded Successfully\n");


        } catch (FileNotFoundException e) {
            System.out.println("File Load Unsuccessful. Please check your files.\n");
        }

    }




    /**
     * This method helps display the statistics of the dataset as a whole which
     * includes things such as number of buildings, the number of routes,
     * and the average time it takes to get from one building to another
     */
    @Override
    public void displayData() {
        System.out.println("Printing out dataset statistics.");
        System.out.println(backend.toString() + "\n");
    }




    /**
     * This method uses 2 friends as inputs and then displays the shortest
     * path which is its closest connection
     *
     * @param origin      building you are going out from
     * @param destination building you are trying to get to
     */
    @Override
    public void displayClosestConnection(String origin, String destination) {
            try {
                ShortestPathInterface path = backend.shortestPath(origin, destination);

                System.out.println("\nShortest Path Details:\n");

                for (int i = 0; i < path.path().size() - 1; i++) {
                    String fromBuilding = path.path().get(i);
                    String toBuilding = path.path().get(i + 1);
                    int time = path.walkingTime().get(i);
                    System.out.println("Walk from " + fromBuilding + " to " + toBuilding + " - Estimated " +
                            "time: " + time + " seconds\n");
                }
                System.out.println("Total walking time: " + path.totalCost() + " seconds");

            }catch(IllegalArgumentException e) {
                System.out.println(e.toString().replace("java.lang.IllegalArgumentException: ",
                        "\n"));
            }


    }



    /**
     * This method ends the loop and exists out of the app
     */
    @Override
    public void exit() {
        System.out.println("Exiting the UW Path Finder program. Goodbye!");
        runProgram = false;
    }

    /**
     * @param maxVal the maximum value
     * @return the correct user input
     */
    private int getUserInt(int maxVal) {
        int userInput;

        while (true) {

            //Ensures an integer is grabbed
            if (scnr.hasNextInt()) {
                userInput = scnr.nextInt();
                scnr.nextLine();

                //Checks to see if the integer is within a valid range
                if (userInput < 1 || userInput > maxVal) {
                    System.out.println("\nInvalid selection. Please try again.");
                    continue;
                }
                return userInput;

            } else {
                //Consumes and move past the incorrect input
                System.out.println("\nThat's not an integer. Please try again.");
                scnr.next();
            }

        }
    }

    /**
     * @return User input
     */
    private String getUserString() {
        String userInput;

        while (true) {
            if (scnr.hasNextLine()) {
                userInput = scnr.nextLine().trim(); // .trim() removes leading and trailing spaces
                if (!userInput.isEmpty()) {
                    return userInput;
                }
            }
            System.out.println("\nInvalid input. Please enter a non-empty string.");
        }
    }

    /**
     * @param args user input
     */
    public static void main(String[] args) {
        Frontend frontend = new Frontend(new Scanner(System.in),
                new Backend());
        frontend.startCommandLoop();
    }


}

