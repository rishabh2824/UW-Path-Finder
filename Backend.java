// --== CS400 Fall 2023 File Header Information ==--
// Name: Rishabh Jain
// Email: rvjain@wisc.edu
// Group: G18
// TA: Grant Waldow
// Lecturer: Florian

import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.text.DecimalFormat;

public class Backend implements BackendInterface {
  private final DijkstraGraph<String, Double> graph;
  private final List<String> allNodeNames; // Stores the names of all nodes

  public Backend() {
    this.graph = new DijkstraGraph<>(new PlaceholderMap<>());
    this.allNodeNames = new ArrayList<>();
  }

  @Override
  public void readData(String fileName) throws FileNotFoundException {
    File file = new File(fileName);

    // Use try-with-resources to ensure the scanner is closed properly
    try (Scanner scanner = new Scanner(file)) {
      // Regex expression pattern to match lines in the DOT file
      Pattern pattern = Pattern.compile("\"([^\"]+)\"\\s*--\\s*\"([^\"]+)\"\\s*\\[seconds=(\\d+" +
          "(?:\\.\\d+)?)];");

      while (scanner.hasNextLine()) {
        String line = scanner.nextLine().trim();
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
          // Extract the node names and weight
          String fromNode = matcher.group(1);
          String toNode = matcher.group(2);
          double weight = Double.parseDouble(matcher.group(3));

          // Add nodes and edge to the graph
          graph.insertNode(fromNode);
          graph.insertNode(toNode);
          graph.insertEdge(fromNode, toNode, weight);

          // Add nodes to node list
          if(!allNodeNames.contains(fromNode))
            allNodeNames.add(fromNode);
          if(!allNodeNames.contains(toNode))
            allNodeNames.add(toNode);
        }}}}

  public ShortestPathInterface shortestPath(String start, String destination) throws IllegalArgumentException {
    // Check if nodes exist
    if (!graph.containsNode(start) || !graph.containsNode(destination)) {
      throw new IllegalArgumentException("Start or destination does not exist in the graph.");
    }

    // Find the shortest path data
    List<String> pathList = graph.shortestPathData(start, destination);
    double cost = graph.shortestPathCost(start, destination);

    // Convert path and walking times into the format expected by ShortestPath
    ArrayList<String> path = new ArrayList<>(pathList);
    ArrayList<Integer> walkingTimes = new ArrayList<>();

    // Assuming you have a method to get the weight of an edge between two nodes
    for (int i = 0; i < path.size() - 1; i++) {
      double segmentCost = graph.getEdge(path.get(i), path.get(i + 1));
      walkingTimes.add((int) Math.round(segmentCost)); // Or calculate as needed
    }

    int totalCost = (int) Math.round(cost);

    // Create an instance of ShortestPath with the calculated data
    return new ShortestPath(path, walkingTimes, totalCost);
  }

  @Override
  public String toString() {
    int numberOfBuildings = this.graph.getNodeCount(); // Get the number of nodes
    int numberOfEdges = this.graph.getEdgeCount(); // Get the number of edges
    double totalWalkingTime = 0; // Initialize total walking time

    // Iterate over all recorded node names to calculate total walking time
    for (String nodeName : allNodeNames) {
      try {
        BaseGraph<String, Double>.Node node = this.graph.nodes.get(nodeName);
        for (BaseGraph<String, Double>.Edge edge : node.edgesLeaving) {
          totalWalkingTime += edge.data;
        }
      } catch (NoSuchElementException e) {
        // Handle the case where the node might have been removed
        // This should not happen if allNodeNames is properly maintained
      }
    }

    // Format the graph information string
    DecimalFormat decimalFormat = new DecimalFormat("#.##");
    String formattedTotalWalkingTime = decimalFormat.format(totalWalkingTime);

    return "Graph information:\n" + "Number of buildings (nodes): " + numberOfBuildings + "\n" +
        "Number of edges: " + numberOfEdges + "\n" + "Total walking time (sum of weights): " +
        formattedTotalWalkingTime + " seconds";
  }

  public static void main(String[] args) {
    Frontend frontend = new Frontend(new Scanner(System.in),
        new Backend());
    frontend.startCommandLoop();
  }
}
