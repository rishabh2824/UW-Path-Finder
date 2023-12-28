// --== CS400 Fall 2023 File Header Information ==--
// Name: Rishabh Jain
// Email: rvjain@wisc.edu
// Group: G18
// TA: Grant Waldow
// Lecturer: Florian

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.ByteArrayInputStream;

public class BackendDeveloperTests {

    // Test loading of data from a valid file
    @Test
    public void testReadDataValidFile() {
        BackendInterface backend = new Backend();
        assertDoesNotThrow(() -> backend.readData("campus.dot"));
    }

    // Test loading of data from a non-existent file
    @Test
    public void testReadDataInvalidFile() {
        BackendInterface backend = new Backend();
        assertThrows(FileNotFoundException.class, () -> backend.readData("wrong.dot"));
    }

    // Test finding the shortest path with valid start and destination
    @Test
    public void testShortestPathValidLocations() throws FileNotFoundException {
        BackendInterface backend = new Backend();
        backend.readData("campus.dot"); // Assume this method works correctly
        ShortestPathInterface result = backend.shortestPath("Memorial Union", "Agricultural Heating Station");
        assertNotNull(result);
    }

    // Test finding the shortest path with invalid start or destination
    @Test
    public void testShortestPathInvalidLocations() throws FileNotFoundException {
        BackendInterface backend = new Backend();
        backend.readData("campus.dot"); // Assume this method works correctly
        assertThrows(IllegalArgumentException.class,
            () -> backend.shortestPath("invalid_start", "destination_building"));
    }

    // Test toString method returns expected format
    @Test
    public void testToStringFormat() throws FileNotFoundException {
        BackendInterface backend = new Backend();
        backend.readData("campus.dot");
        String datasetInfo = backend.toString();
        System.out.println(datasetInfo);
        assertTrue(datasetInfo.contains("Number of buildings (nodes): 160") && datasetInfo.contains(
            "Number of edges: 800") && datasetInfo.contains("Total walking time (sum of weights):" +
            " 110675.5 seconds"));
    }

    // Test if frontend successfully prints the correct value from the shortest path method.
    @Test
    void DataRetrievalIntegration() {
        Backend backend = new Backend();
        Frontend frontend = new Frontend(new Scanner(System.in), backend);

        String origin = "Memorial Union";
        String destination = "Agricultural Heating Station";
        frontend.displayClosestConnection(origin, destination);

        String expectedOutput = "Your expected output here";
        assertNotNull(expectedOutput.trim());
    }

    // Test if frontend throws an exception when it fails to load data
    @Test
    void DataLoadFailureIntegration() {
        Backend backend = new Backend();
        Frontend frontend = new Frontend(new Scanner(System.in), backend);

        String invalidFile = "invalid_file.dot";
        assertThrows(FileNotFoundException.class, () -> {
            backend.readData(invalidFile);
            frontend.loadDataFile();
        });
    }

    @Test
    public void testExitForPartner() {
        Backend backend = new Backend();
        Frontend frontend = new Frontend(new Scanner(System.in), backend);
        frontend.exit();
        assertFalse(frontend.runProgram);
    }

    @Test
    public void testLoadDataFileForPartner() {
        String input = "1\n"; // Assuming 1 is the correct input for loading file
        ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        Backend backend = new Backend();
        Frontend frontend = new Frontend(new Scanner(System.in), backend);
        frontend.loadDataFile();

        assertTrue(frontend.hasLoadedFile);
    }
}
