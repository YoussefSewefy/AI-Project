package code;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class Strategies {
    
    public static Node BF(Matrix x){
    	Queue<Node> queue = new LinkedList<Node>();
        Node initialNode = new Node(x.initialState, null, "", 0, new int[] {} );
        queue.add(initialNode);

        boolean flag = false;
        while(!flag){
            if(!queue.isEmpty()){
                Node node = queue.remove();
                Matrix.expandedNum++;
                if(x.goalTest(node).equals("goal")){
                    return node;
                }
                else if(x.goalTest(node).equals("not a goal")){
                    Node[] optionNodes = DecideOptions.decideOptions(node, x);
                    for(int i = 0; i<optionNodes.length; i++){
                        if(optionNodes[i] != null && !x.stateSpace.contains(optionNodes[i].state)){
                            queue.add(optionNodes[i]);
                            x.stateSpace.add(optionNodes[i].state);
                        }
                    }
                }
            }
            else{
                return null;
            }
        }
        return null;
    }
    
    public static Node DF(Matrix x){

    	Stack<Node> queue = new Stack<Node>();
        Node initialNode = new Node(x.initialState, null, "", 0, new int[] {} );
        queue.push(initialNode);

        boolean flag = false;
        while(!flag){
            if(!queue.isEmpty()){
                Node node = queue.pop();
                Matrix.expandedNum++;
                if(x.goalTest(node).equals("goal")){
                    return node;
                }else if(x.goalTest(node).equals("not a goal")){
                    Node[] optionNodes = DecideOptions.decideOptions(node, x);
                    for(int i = optionNodes.length-1; i>=0; i--){
                        if(optionNodes[i] != null && !x.stateSpace.contains(optionNodes[i].state)){
                            queue.push(optionNodes[i]);
                            x.stateSpace.add(optionNodes[i].state);
                        }
                    }
                }
            }
            else{
                return null;
            }
        }
        return null;
    }
    
    public static Node ID(Matrix x){

    	Stack<Node> queue = new Stack<Node>();
        ArrayList<Node> finalNodeExpansion = new ArrayList<Node>();
        Node initialNode = new Node(x.initialState, null, "", 0, new int[] {} );
        queue.push(initialNode);

        boolean flag = false;
        boolean flag2 = false;
        int depth = 1;
        while(!flag2){
            while(!flag){
                if(!queue.isEmpty()){
                    Node node = queue.get(0);
                    queue.remove(0);
                    Matrix.expandedNum++;
                    if(x.goalTest(node).equals("goal")){
                        return node;
                    }else if(x.goalTest(node).equals("not a goal")){
                        Node[] optionNodes = DecideOptions.decideOptions(node, x);
                        for(int i = optionNodes.length-1; i>=0; i--){
                            if(optionNodes[i] != null && !x.stateSpace.contains(optionNodes[i].state)){
                                if(node.depth == depth){
                                    finalNodeExpansion.add(0, optionNodes[i]);
                                }
                                else{
                                    queue.push(optionNodes[i]);
                                }
                                x.stateSpace.add(optionNodes[i].state);
                            }
                        }
                    }
                }
                else{
                    break;
                }
            }
            if(finalNodeExpansion.size() == 0)
                return null;
            else{
                depth++;
                x.stateSpace.clear();
                finalNodeExpansion.clear();
                queue.clear();
                queue.add(initialNode);
                x.stateSpace.add(initialNode.state);
            }
        }
        return null;
    }
    
    public static Node UC(Matrix x){
    	PriorityQueue<Node> queue = new PriorityQueue<Node>();
        Node initialNode = new Node(x.initialState, null, "", 0, x.pathCost(x.initialState, 0));
        queue.add(initialNode);

        boolean flag = false;
        while(!flag){
            if(queue.size() != 0){
                Node node = queue.remove();
                Matrix.expandedNum++;
                if(x.goalTest(node).equals("goal")){
                    return node;
                }else if(x.goalTest(node).equals("not a goal")){
                    Node[] optionNodes = DecideOptions.decideOptions(node, x);
                    for(int i = optionNodes.length-1; i>=0; i--){
                        if(optionNodes[i] != null && !x.stateSpace.contains(optionNodes[i].state)) {
                        	optionNodes[i].comparison = "uniform cost";
                        	queue.add(optionNodes[i]);
                        	x.stateSpace.add(optionNodes[i].state);
                        }
                    }
                }
            }
            else{
                return null;
            }
        }
        return null;
    }
    
    public static Node GR1(Matrix x){
    	PriorityQueue<Node> queue = new PriorityQueue<Node>();
        Node initialNode = new Node(x.initialState, null, "", 0, x.pathCost(x.initialState, 0));
        queue.add(initialNode);

        boolean flag = false;
        while(!flag){
            if(queue.size() != 0){
                Node node = queue.remove();
                Matrix.expandedNum++;
                if(x.goalTest(node).equals("goal")){
                    return node;
                }else if(x.goalTest(node).equals("not a goal")){
                    Node[] optionNodes = DecideOptions.decideOptions(node, x);
                    for(int i = optionNodes.length-1; i>=0; i--){
                        if(optionNodes[i] != null && !x.stateSpace.contains(optionNodes[i].state)){
                        	optionNodes[i].heuristic = StrategiesHelpers.calculateHeuristic1(optionNodes[i]);
                        	optionNodes[i].comparison = "greedy";
                        	queue.add(optionNodes[i]);
                        	x.stateSpace.add(optionNodes[i].state);
                        }
                    }
                }
            }
            else{
                return null;
            }
        }
        return null;
    }
    
    public static Node GR2(Matrix x){
    	PriorityQueue<Node> queue = new PriorityQueue<Node>();
        Node initialNode = new Node(x.initialState, null, "", 0, x.pathCost(x.initialState, 0));
        queue.add(initialNode);

        boolean flag = false;
        while(!flag){
            if(queue.size() != 0){
                Node node = queue.remove();
                Matrix.expandedNum++;
                if(x.goalTest(node).equals("goal")){
                    return node;
                }else if(x.goalTest(node).equals("not a goal")){
                    Node[] optionNodes = DecideOptions.decideOptions(node, x);
                    for(int i = optionNodes.length-1; i>=0; i--){
                        if(optionNodes[i] != null && !x.stateSpace.contains(optionNodes[i].state)){
                        	optionNodes[i].heuristic = StrategiesHelpers.calculateHeuristic2(optionNodes[i]);
                        	optionNodes[i].comparison = "greedy";
                        	queue.add(optionNodes[i]);
                        	x.stateSpace.add(optionNodes[i].state);
                        }
                    }
                }
            }
            else{
                return null;
            }
        }
        return null;
    }
    
    public static Node AS1(Matrix x){
    	PriorityQueue<Node> queue = new PriorityQueue<Node>();
        Node initialNode = new Node(x.initialState, null, "", 0, x.pathCost(x.initialState, 0));
        queue.add(initialNode);

        boolean flag = false;
        while(!flag){
            if(queue.size() != 0){
                Node node = queue.remove();
                Matrix.expandedNum++;
                if(x.goalTest(node).equals("goal")){
                    return node;
                }else if(x.goalTest(node).equals("not a goal")){
                    Node[] optionNodes = DecideOptions.decideOptions(node, x);
                    for(int i = optionNodes.length-1; i>=0; i--){
                        if(optionNodes[i] != null && !x.stateSpace.contains(optionNodes[i].state)){
                        	optionNodes[i].heuristic = StrategiesHelpers.calculateHeuristic1(optionNodes[i]);
                        	optionNodes[i].comparison = "a star";
                        	queue.add(optionNodes[i]);
                        	x.stateSpace.add(optionNodes[i].state);
                        }
                    }
                }
            }
            else{
                return null;
            }
        }
        return null;
    }
    
    public static Node AS2(Matrix x){
    	PriorityQueue<Node> queue = new PriorityQueue<Node>();
        Node initialNode = new Node(x.initialState, null, "", 0, x.pathCost(x.initialState, 0));
        queue.add(initialNode);

        boolean flag = false;
        while(!flag){
            if(queue.size() != 0){
                Node node = queue.remove();
                Matrix.expandedNum++;
                if(x.goalTest(node).equals("goal")){
                    return node;
                }else if(x.goalTest(node).equals("not a goal")){
                    Node[] optionNodes = DecideOptions.decideOptions(node, x);
                    for(int i = optionNodes.length-1; i>=0; i--){
                        if(optionNodes[i] != null && !x.stateSpace.contains(optionNodes[i].state)){
                        	optionNodes[i].heuristic = StrategiesHelpers.calculateHeuristic2(optionNodes[i]);
                        	optionNodes[i].comparison = "a star";
                        	queue.add(optionNodes[i]);
                        	x.stateSpace.add(optionNodes[i].state);
                        }
                    }
                }
            }
            else{
                return null;
            }
        }
        return null;
    }
}

