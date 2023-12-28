import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Scanner;

public class FrontendDeveloperTests {

    /**
     * This test method ensure that the program begins and exits correctly
     */
    @Test
    public void testStartExitCommandLoop() {

        TextUITester tester = new TextUITester("4\n");

        String introString = "Welcome to our interactive campus navigation application, " +
                "designed to provide comprehensive insights and guidance for exploring " +
                "our campus. As a user, you will engage with a dynamic interface, " +
                "tailored to streamline your experience in accessing and understanding " +
                "the campus layout. This application is meticulously crafted by our " +
                "development team, ensuring an intuitive and " +
                "user-friendly journey.\n";
        Frontend frontend = new Frontend(new Scanner(System.in), new Backend());

        // Call the method
        frontend.startCommandLoop();

        String output = tester.checkOutput();
        Assertions.assertTrue(output.startsWith(introString) && output.contains("Exiting the " +
                "UW Path Finder program. Goodbye!"));
    }

    /**
     * This test method ensure that the program's loadFile works properly with valid inputs then
     * exits the program
     */
    @Test
    public void testValidLoadFile() {

        TextUITester tester = new TextUITester("1\n1\n4\n");
        Frontend frontend = new Frontend(new Scanner(System.in), new Backend());

        // Call the method
        frontend.startCommandLoop();


        String output = tester.checkOutput();
        Assertions.assertTrue(output.contains("Please enter an integer for file selection: ")
                && output.contains("File Loaded Successfully\n"));


    }

    /**
     * This test method ensure that the program ensures the DisplayData function works properly
     * with valid inputs and then exits the program
     */
    @Test
    public void testDisplayData() {
        TextUITester tester = new TextUITester("1\n1\n2\nx\n4\n");
        Frontend frontend = new Frontend(new Scanner(System.in), new Backend());

        // Call the method
        frontend.startCommandLoop();
        String output = tester.checkOutput();

        //System.out.println(output);
        Assertions.assertTrue(output.contains("Printing out dataset statistics.")
                && output.contains("Please enter an integer for command:")
                && output.contains("\n\nPlease enter any key to return to the menu."));

    }

    /**
     * This test method ensure that the program ensures the DisplayClosestConnection function works
     * properly with valid inputs and then exits the program
     */
    @Test
    public void testDisplayClosestConnection() {
        TextUITester tester = new TextUITester("1\n1\n3\nStart\nEnd\nx\n4\n");
        Frontend frontend = new Frontend(new Scanner(System.in), new Backend());

        // Call the method
        frontend.startCommandLoop();
        String output = tester.checkOutput();

        //System.out.println(output);
        Assertions.assertTrue(output.contains("Please enter your starting point:")
                && output.contains("Please enter your destination:")
                && output.contains("\n\nPlease enter any key to return to the menu."));
    }

    /**
     * This test method ensure that the program ensures the loadFile and displayClosestConnection
     * functions work properly when given invalid inputs to their requests
     */
    @Test
    public void testInvalidInputs() {
        //This series of tests will first test invalid inputs in the main menu then invalid
        // inputs in the file selections then invalid inputs in the destination function
        TextUITester tester = new TextUITester("""
                0
                500
                StringInput
                1
                0
                500
                StringInput
                1
                3

                Valid
                Valid
                x
                4
                """);
        Frontend frontend = new Frontend(new Scanner(System.in), new Backend());

        // Call the method
        frontend.startCommandLoop();
        String output = tester.checkOutput();
        //System.out.println(output);
        Assertions.assertTrue(output.contains("\nInvalid selection. Please try again.")
                && output.contains("\nInvalid input. Please enter a non-empty string.")
                && output.contains("\nThat's not an integer. Please try again."));

    }

    /**
     * Integration
     */
    @Test
    public void testDisplayClosestConnectionIntegration() {
        TextUITester tester = new TextUITester("""
            Lot 36 - Observatory Drive Ramp
            Steenbock Memorial Library
            x
            4
            """);
        Frontend frontend = new Frontend(new Scanner(System.in), new Backend());

        // Call the method
        frontend.startCommandLoop();
        String output = tester.checkOutput();

        Assertions.assertNotNull(output);
    }

    /**
     * This test method ensure that the program ensures the DisplayData function works properly
     * with valid inputs and then exits the program
     */
    @Test
    public void testDisplayDataIntegration() {
        TextUITester tester = new TextUITester("1\n1\n2\nx\n4\n");
        Frontend frontend = new Frontend(new Scanner(System.in), new Backend());

        // Call the method
        frontend.startCommandLoop();
        String output = tester.checkOutput();

        //System.out.println(output);
        Assertions.assertTrue(output.contains("""
            Graph information:
            Number of buildings (nodes): 160
            Number of edges: 800
            Total walking time (sum of weights): 110675.5 seconds""")
                && output.contains("Printing out dataset statistics.")
                && output.contains("Please enter an integer for command:")
                && output.contains("\n\nPlease enter any key to return to the menu."));
    }
}





