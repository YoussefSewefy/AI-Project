package code;

import java.util.HashSet;

public abstract class GenericSearchProblem {
    String[] operators;
    String initialState;
    HashSet<String> stateSpace;
    
    // if the current node is the goal node return true, return false otherwise
    abstract String goalTest(Node node);
    // returns tuple of [number of deaths, number of killed agents, depth of the node]
    abstract int[] pathCost(String state, int depth);
    // returns goal node
    abstract Node genericSearchProcedure(String strategy);
}
