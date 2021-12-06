package code;

public class Node implements Comparable<Object>{

    String state;
    Node parentNode;
    String operator;
    int depth;
    int[] pathCost;
    
    String comparison;
    int heuristic;

    public Node(String state, Node parentNode, String operator, int depth, int[] pathCost){
        this.state = state;
        this.parentNode = parentNode;
        this.operator = operator;
        this.depth = depth;
        this.pathCost = pathCost;
    }
    
    public int compareTo(Object o) {
    	Node node = (Node)o;
    	if(comparison.equals("greedy")) {
    		return node.heuristic - this.heuristic;
    	}
    	else if (comparison.equals("a star")) {
    		int thisWeightedCost = (this.pathCost[0] * 500) + (this.pathCost[1] * 300) 
    				+ (this.pathCost[2] * 100);
    		int nodeWeightedCost = (node.pathCost[0] * 500) + (node.pathCost[1] * 300) 
    				+ (node.pathCost[2] * 100);
    		if(this.heuristic + thisWeightedCost < node.heuristic + nodeWeightedCost) {
    			return 1;
    		}
    		else if(this.heuristic + thisWeightedCost > node.heuristic + nodeWeightedCost){
    			return -1;
    		}
    		else {
    			return 0;
    		}
    	}
    	else {
        	if(node.pathCost[0] > this.pathCost[0] || node == null) {
    			return 1;
    		}
    		else if(node.pathCost[0] < this.pathCost[0] || this == null) {
    			return -1;
    		}
    		else 
    			if(node.pathCost[1] > this.pathCost[1] || node == null) {
    				return 1;
    			}
    			else if(node.pathCost[1] < this.pathCost[1] || this == null) {
    				return -1;
    			}
    			else 
    				if(node.pathCost[2] > this.pathCost[2] || node == null) {
    					return 1;
    				}
    				else if(node.pathCost[2] < this.pathCost[2] || this == null) {
    					return -1;
    				}
        	return 0;
    	}
    }
}
