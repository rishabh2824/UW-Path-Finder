// --== CS400 Fall 2023 File Header Information ==--
// Name: Rishabh Jain
// Email: rvjain@wisc.edu
// Group: G18
// TA: Grant Waldow
// Lecturer: Florian

import java.util.*;
public class ShortestPath implements ShortestPathInterface {
  private final ArrayList<String> path;
  private final ArrayList<Integer> walkingTimes;
  private final int totalCost;

  public ShortestPath(ArrayList<String> path, ArrayList<Integer> walkingTimes, int totalCost) {
    this.path = path;
    this.walkingTimes = walkingTimes;
    this.totalCost = totalCost;
  }

  @Override
  public ArrayList<String> path() {
    return path;
  }

  @Override
  public ArrayList<Integer> walkingTime() {
    return walkingTimes;
  }

  @Override
  public int totalCost() {
    return totalCost;
  }
}
