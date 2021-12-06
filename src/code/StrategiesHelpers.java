package code;

public class StrategiesHelpers {
    
    // distance to phone booth * num of mutated hostages as long as distance is less than 300
    public static int calculateHeuristic1(Node node) {
    	String[] stateArr = node.state.split(";");
    	String[] telPos = stateArr[1].split(",");
    	String[] neoPos= stateArr[0].split(",");
    	
    	int neoX = Integer.parseInt(neoPos[0]);
    	int neoY = Integer.parseInt(neoPos[1]);
    	
    	int telX = Integer.parseInt(telPos[0]);
    	int telY = Integer.parseInt(telPos[1]);
    	
    	String[] mutatedAgents= node.state.split(";")[3].split(",");
    	int numOfMutated=0;
    	if(!mutatedAgents[0].equals("")) {
    		numOfMutated=mutatedAgents.length;
    	}
    	int distance = (int) Math.abs(neoX-telX) + Math.abs(neoY-telY);
    	int total = Math.min(distance, 300)*numOfMutated;
    	return total;
    }
    
   // Number of hostages that will be dead on the way back to the phone booth
    public static int calculateHeuristic2(Node node) {
    	String[] stateArr = node.state.split(";");
    	String[] hostagesArr = stateArr[5].split(",");
    	String[] carriedHostages = stateArr[9].split(",");
    	int numberOfHostagesThatWillBeDead = 0;
    	
    	String[] telPos = stateArr[1].split(",");
    	String[] neoPos= stateArr[0].split(",");
    	
    	int neoX = Integer.parseInt(neoPos[0]);
    	int neoY = Integer.parseInt(neoPos[1]);
    	
    	int telX = Integer.parseInt(telPos[0]);
    	int telY = Integer.parseInt(telPos[1]);
    	
    	// number of moves needed to reach phone booth
    	int distanceToPhone = (int) Math.abs(neoX-telX) + Math.abs(neoY-telY);
    	int totalDamageTillPhone = distanceToPhone*2;
    	
    	if(!hostagesArr[0].equals(""))
	    	for(int i = 0; i<hostagesArr.length; i++) {
	    		if(Integer.parseInt(hostagesArr[i]) + totalDamageTillPhone >= 100) {
	    			numberOfHostagesThatWillBeDead++;
	    		}
	    	}
    	if(!carriedHostages[0].equals(""))
	    	for(int i = 0; i<carriedHostages.length; i++) {
	    		if(Integer.parseInt(carriedHostages[i]) + totalDamageTillPhone >= 100) {
	    			numberOfHostagesThatWillBeDead++;
	    		}
	    	}
    	return numberOfHostagesThatWillBeDead;
    }
}

