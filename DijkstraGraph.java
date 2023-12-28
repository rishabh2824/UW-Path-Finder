// --== CS400 Fall 2023 File Header Information ==--
// Name: Rishabh Jain
// Email: rvjain@wisc.edu
// Group: G18
// TA: Grant Waldow
// Lecturer: Florian

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.*;

/**
 * This class extends the BaseGraph data structure with additional methods for computing the total
 * cost and list of node data along the shortest path connecting a provided starting to ending
 * nodes. This class makes use of Dijkstra's shortest path algorithm.
 */
public class DijkstraGraph<NodeType, EdgeType extends Number> extends BaseGraph<NodeType, EdgeType>
    implements GraphADT<NodeType, EdgeType> {

  /**
   * While searching for the shortest path between two nodes, a SearchNode contains data about one
   * specific path between the start node and another node in the graph. The final node in this path
   * is stored in its node field. The total cost of this path is stored in its cost field. And the
   * predecessor SearchNode within this path is referenced by the predecessor field (this field is
   * null within the SearchNode containing the starting node in its node field).
   * SearchNodes are Comparable and are sorted by cost so that the lowest cost SearchNode has the
   * highest priority within a java.util.PriorityQueue.
   */
  protected class SearchNode implements Comparable<SearchNode> {
    public Node node;
    public double cost;
    public SearchNode predecessor;

    public SearchNode(Node node, double cost, SearchNode predecessor) {
      this.node = node;
      this.cost = cost;
      this.predecessor = predecessor;
    }

    public int compareTo(SearchNode other) {
      return Double.compare(cost, other.cost);
    }
  }

  /**
   * Constructor that sets the map that the graph uses.
   *
   * @param map the map that the graph uses to map a data object to the node object it is stored in
   */
  public DijkstraGraph(MapADT<NodeType, Node> map) {
    super(map);
  }

  /**
   * This helper method creates a network of SearchNodes while computing the shortest path between
   * the provided start and end locations. The SearchNode that is returned by this method is
   * represents the end of the shortest path that is found: it's cost is the cost of that shortest
   * path, and the nodes linked together through predecessor references to represent all the nodes
   * along that shortest path (ordered from end to start).
   *
   * @param start the data item in the starting node for the path
   * @param end   the data item in the destination node for the path
   * @return SearchNode for the final end node within the shortest path
   * @throws NoSuchElementException when no path from start to end is found or when either start or
   *                                end data do not correspond to a graph node
   */
  protected SearchNode computeShortestPath(NodeType start, NodeType end) {
    if (!this.containsNode(start) || !this.containsNode(end)) {
      throw new NoSuchElementException("Start or end node not found in the graph.");
    }

    PriorityQueue<SearchNode> queue = new PriorityQueue<>();
    MapADT<NodeType, Double> shortestDistances = new PlaceholderMap<>();
    MapADT<NodeType, SearchNode> visitedNodes = new PlaceholderMap<>();

    // Initialize the start node distance to 0 and add it to the queue
    shortestDistances.put(start, 0.0);
    queue.add(new SearchNode(nodes.get(start), 0, null));

    while (!queue.isEmpty()) {
      SearchNode currentNode = queue.poll();
      NodeType currentData = currentNode.node.data;

      // If the current node is the end node, we've found the shortest path
      if (currentData.equals(end)) {
        return currentNode;
      }

      // Skip this node if we have already found a shorter path to it
      if (visitedNodes.containsKey(currentData)) {
        continue;
      }

      // Mark the node as visited
      visitedNodes.put(currentData, currentNode);

      // Explore all edges leaving the current node
      for (Edge edge : currentNode.node.edgesLeaving) {
        NodeType successorData = edge.successor.data;
        double edgeWeight = edge.data.doubleValue();
        double newCost = currentNode.cost + edgeWeight;

        // If the new cost is less than the known cost, or if the successor is not in the map, update it
        try {
          if (!shortestDistances.containsKey(successorData) || newCost < shortestDistances.get(successorData)) {
            shortestDistances.remove(successorData); // Remove the existing mapping if any
            shortestDistances.put(successorData, newCost); // Put the new cost
            queue.add(new SearchNode(edge.successor, newCost, currentNode));
          }
        } catch (NoSuchElementException e) {
          // This catch block can be used to handle the case where get() is called on a non-existent key
          shortestDistances.put(successorData, newCost);
          queue.add(new SearchNode(edge.successor, newCost, currentNode));
        }
      }
    }

    // If the end node was not reached, throw an exception
    throw new NoSuchElementException("No path exists from " + start + " to " + end);
  }


  /**
   * Returns the list of data values from nodes along the shortest path from the node with the
   * provided start value through the node with the provided end value. This list of data values
   * starts with the start value, ends with the end value, and contains intermediary values in the
   * order they are encountered while traversing this shortest path. This method uses Dijkstra's
   * shortest path algorithm to find this solution.
   *
   * @param start the data item in the starting node for the path
   * @param end   the data item in the destination node for the path
   * @return list of data item from node along this shortest path
   */
  public List<NodeType> shortestPathData(NodeType start, NodeType end) {
    SearchNode endNode = computeShortestPath(start, end);
    List<NodeType> path = new LinkedList<>();

    // Traverse from the end node to the start node using the predecessor references
    for (SearchNode node = endNode; node != null; node = node.predecessor) {
      path.add(0, node.node.data); // Insert at the beginning of the list
    }

    return path;
  }


  /**
   * Returns the cost of the path (sum over edge weights) of the shortest path from the node
   * containing the start data to the node containing the end data. This method uses Dijkstra's
   * shortest path algorithm to find this solution.
   *
   * @param start the data item in the starting node for the path
   * @param end   the data item in the destination node for the path
   * @return the cost of the shortest path between these nodes
   */
  public double shortestPathCost(NodeType start, NodeType end) {
    return computeShortestPath(start, end).cost;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////       Junit Testers       //////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * Tests the algorithm with a sample graph.
   */
  @Test
  public void testDijkstraGraph1() {
    DijkstraGraph<String, Double> graph = new DijkstraGraph<>(new PlaceholderMap<>());

    // Insert nodes
    graph.insertNode("P");
    graph.insertNode("Q");
    graph.insertNode("R");
    graph.insertNode("S");
    graph.insertNode("T");

    // Insert edges with different weights
    graph.insertEdge("P", "Q", 3.0); // Changed
    graph.insertEdge("P", "R", 1.0); // Changed
    graph.insertEdge("Q", "R", 1.0); // Changed
    graph.insertEdge("Q", "S", 7.0);
    graph.insertEdge("R", "S", 4.0); // Changed
    graph.insertEdge("S", "T", 2.0);

    // Assert the cost of the shortest path from P to T
    Assertions.assertEquals(graph.shortestPathCost("P", "T"), 7.0, .0001); // Changed the expected cost

    // Expected path from P to T
    List<String> expectedPath = new LinkedList<>();
    expectedPath.add("P");
    expectedPath.add("R");
    expectedPath.add("S");
    expectedPath.add("T");

    // Assert the actual path matches the expected path
    Assertions.assertEquals(graph.shortestPathData("P", "T"), expectedPath);
  }

  /**
   * Test case with another graph.
   */
  @Test
  public void testDijkstraGraph2() {
    DijkstraGraph<String, Double> graph = new DijkstraGraph<>(new PlaceholderMap<>());

    // Insert nodes
    graph.insertNode("X");
    graph.insertNode("Y");
    graph.insertNode("Z");
    graph.insertNode("W");
    graph.insertNode("V");
    graph.insertNode("U");
    graph.insertNode("T");
    graph.insertNode("S");

    // Insert edges with different weights
    graph.insertEdge("X", "Y", 3.0); // Changed
    graph.insertEdge("X", "Z", 1.0); // Changed
    graph.insertEdge("X", "V", 12.0); // Changed
    graph.insertEdge("Y", "V", 9.0); // Changed
    graph.insertEdge("Y", "W", 2.0); // Changed
    graph.insertEdge("Z", "W", 4.0); // Changed
    graph.insertEdge("W", "V", 3.0); // Changed
    graph.insertEdge("W", "U", 1.0); // Changed
    graph.insertEdge("U", "W", 3.0); // Changed
    graph.insertEdge("U", "S", 5.0); // Changed
    graph.insertEdge("T", "S", 2.0); // Changed

    // Assert the cost of the shortest path from X to V
    Assertions.assertEquals(graph.shortestPathCost("X", "V"), 8.0, .0001); // Changed the expected cost

    // Expected path from X to V
    List<String> expectedPath = new LinkedList<>();
    expectedPath.add("X");
    expectedPath.add("Z");
    expectedPath.add("W");
    expectedPath.add("V");

    // Assert the actual path matches the expected path
    Assertions.assertEquals(graph.shortestPathData("X", "V"), expectedPath);
  }

  /**
   * Test case with no path between nodes.
   */
  @Test
  public void testDijkstraGraph3() {
    DijkstraGraph<String, Double> graph = new DijkstraGraph<>(new PlaceholderMap<>());

    // Insert nodes
    graph.insertNode("P");
    graph.insertNode("Q");
    graph.insertNode("R");
    graph.insertNode("S");
    graph.insertNode("T");

    // Insert edges where no direct path from P to S exists
    graph.insertEdge("P", "Q", 3.0); // Changed
    graph.insertEdge("P", "R", 1.0); // Changed
    graph.insertEdge("Q", "R", 1.0); // Changed
    // The edge between S and T ensures that there are disconnected components
    graph.insertEdge("S", "T", 6.0); // Changed

    // Attempts to find the shortest path from P to S, which should not exist
    try {
      graph.shortestPathCost("P", "S");
      Assertions.fail("Expected a NoSuchElementException to be thrown");
    } catch (NoSuchElementException e) {
      Assertions.assertTrue(true); // This is expected
    }

    // Attempt to retrieve the shortest path data from P to S, which should not exist
    try {
      graph.shortestPathData("P", "S");
      Assertions.fail("Expected a NoSuchElementException to be thrown");
    } catch (NoSuchElementException e) {
      Assertions.assertTrue(true); // This is expected
    }
  }

}
