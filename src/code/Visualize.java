package code;

import java.util.ArrayList;
import java.util.Scanner;

public class Visualize {
	
	public static void visualize(String grid, ArrayList<String> states){
		String[] gridSplit = grid.split(";");
		String[] gridDimensions = gridSplit[0].split(",");
		int m = Integer.parseInt(gridDimensions[0]);
		int n = Integer.parseInt(gridDimensions[1]);
		String outGridString = "";
		String[] telPos = gridSplit[3].split(",");
		int telX = Integer.parseInt(telPos[0]);
		int telY = Integer.parseInt(telPos[1]);
		String[] pads = gridSplit[6].split(",");
		
		Scanner sc = new Scanner(System.in);
		String cont = "yes";
		int c = 0;
		while(cont.toLowerCase().equals("yes") || cont.toLowerCase().equals("y")) {
			if(c >= states.size()) {
				outGridString = "Goal State Reached";
				break;
			}
			String[][] gridArr = new String[m][n];
			for(int i = 3; i<pads.length; i+=4) {
				gridArr[Integer.parseInt(pads[i-3])][Integer.parseInt(pads[i-2])] = "P ("+pads[i-1]+","+pads[i]+")";
			}
			
			gridArr[telX][telY] = "TB";
			String[] state = states.get(c).split(";");
			String agentsKilled = state[6];
			String[] carriedHostages = state[8].split(",");
			String deadHostages = state[9];
			String[] neoPos = state[0].split(",");
			for(int i = 0; i<state.length; i++) {
				String[] agents = state[2].split(",");
				String[] agentsMustKill = state[3].split(",");
				String[] pills = state[4].split(",");
				String[] hostages = state[5].split(",");
				
				if(!agents[0].equals(""))
					for(int j = 1; j<agents.length; j+=2) {
						gridArr[Integer.parseInt(agents[j-1])][Integer.parseInt(agents[j])] = "A";
					}
				if(!agentsMustKill[0].equals(""))
					for(int j = 1; j<agentsMustKill.length; j+=2) {
						gridArr[Integer.parseInt(agentsMustKill[j-1])][Integer.parseInt(agentsMustKill[j])] = "A";
					}
				if(!pills[0].equals(""))
					for(int j = 1; j<pills.length; j+=2) {
						gridArr[Integer.parseInt(pills[j-1])][Integer.parseInt(pills[j])] = "P";
					}
				if(!hostages[0].equals(""))
					for(int j = 2; j<hostages.length; j+=3) {
						gridArr[Integer.parseInt(hostages[j-2])][Integer.parseInt(hostages[j-1])] 
								= "H ("+hostages[j]+")";
					}
			}
			if(gridArr[Integer.parseInt(neoPos[0])][Integer.parseInt(neoPos[1])] == null)
				gridArr[Integer.parseInt(neoPos[0])][Integer.parseInt(neoPos[1])] = "NEO";
			else {
				gridArr[Integer.parseInt(neoPos[0])][Integer.parseInt(neoPos[1])] = "NEO  "
						+ gridArr[Integer.parseInt(neoPos[0])][Integer.parseInt(neoPos[1])];
			}
			if(state.length > 10)
				outGridString = gridToString(gridArr, agentsKilled, carriedHostages, 
						deadHostages, n, state[10], neoPos[2]);
			else {
				outGridString = gridToString(gridArr, agentsKilled, carriedHostages, 
						deadHostages, n, "", neoPos[2]);
			}
			c++;
			System.out.println(outGridString);
			System.out.println("Would you like to see the next step? (y/n)/(yes/no)");
			cont = sc.next();
		}
		sc.close();
		System.out.println("You have exited the game visualization. This is the last state.");
		String[][] gridArr = new String[m][n];
		for(int i = 3; i<pads.length; i+=4) {
			gridArr[Integer.parseInt(pads[i-3])][Integer.parseInt(pads[i-2])] = "P ("+pads[i-1]+","+pads[i]+")";
		}
		
		gridArr[telX][telY] = "TB";
		String[] state = states.get(states.size()-1).split(";");
		String agentsKilled = state[6];
		String[] carriedHostages = state[8].split(",");
		String deadHostages = state[9];
		String[] neoPos = state[0].split(",");
		for(int i = 0; i<state.length; i++) {
			String[] agents = state[2].split(",");
			String[] agentsMustKill = state[3].split(",");
			String[] pills = state[4].split(",");
			String[] hostages = state[5].split(",");
			
			if(!agents[0].equals(""))
				for(int j = 1; j<agents.length; j+=2) {
					gridArr[Integer.parseInt(agents[j-1])][Integer.parseInt(agents[j])] = "A";
				}
			if(!agentsMustKill[0].equals(""))
				for(int j = 1; j<agentsMustKill.length; j+=2) {
					gridArr[Integer.parseInt(agentsMustKill[j-1])][Integer.parseInt(agentsMustKill[j])] = "A";
				}
			if(!pills[0].equals(""))
				for(int j = 1; j<pills.length; j+=2) {
					gridArr[Integer.parseInt(pills[j-1])][Integer.parseInt(pills[j])] = "P";
				}
			if(!hostages[0].equals(""))
				for(int j = 2; j<hostages.length; j+=3) {
					gridArr[Integer.parseInt(hostages[j-2])][Integer.parseInt(hostages[j-1])] 
							= "H ("+hostages[j]+")";
				}
		}
		if(gridArr[Integer.parseInt(neoPos[0])][Integer.parseInt(neoPos[1])] == null)
			gridArr[Integer.parseInt(neoPos[0])][Integer.parseInt(neoPos[1])] = "NEO";
		else {
			gridArr[Integer.parseInt(neoPos[0])][Integer.parseInt(neoPos[1])] = "NEO  "
					+ gridArr[Integer.parseInt(neoPos[0])][Integer.parseInt(neoPos[1])];
		}
		if(state.length > 10)
			outGridString = gridToString(gridArr, agentsKilled, carriedHostages, deadHostages, n, state[10], neoPos[2]);
		else {
			outGridString = gridToString(gridArr, agentsKilled, carriedHostages, deadHostages, n, "", neoPos[2]);
		}
		
		System.out.println(outGridString);

		System.out.println("You have exited the game visualization. This is the last state.");
	}
	
	public static String gridToString(String[][] grid, String agentsKilled, 
			String[] carriedHostages, String deadHostages, int n, String action, String neoDamage) {
		
		String gridString = "-------------------------------------------------------------------------\n\n"
				+ "Neo Damage: " + neoDamage+"  Action: " + action +"  Kills: "+ agentsKilled +"  Deaths: "+deadHostages+"  Carried Hostages: ";
		if(!carriedHostages[0].equals("")) {
			for(int i = 0; i<carriedHostages.length-1; i++) {
				gridString += carriedHostages[i]+", ";
			}
			gridString += carriedHostages[carriedHostages.length-1];
		}
		else {
			gridString += "none";
		}
		gridString += "\n\n";
		gridString += " "+new String(new char[15*n-1]).replace("\0", "-")+"\n";
		for(int i = 0; i<grid.length; i++) {
			gridString += "|";
			for(int j = 0; j<grid.length; j++) {
				if(grid[i][j] != null) {
					int addedSpace = 14 - grid[i][j].length() - 2;
					String addedSpaceString = new String(new char[addedSpace]).replace("\0", " ");
					gridString += "  "+ grid[i][j]+addedSpaceString+"|";
				}
				else {
					gridString += new String(new char[14]).replace("\0", " ")+"|";
				}
			}
			gridString += "\n "+ new String(new char[15*n-1]).replace("\0", "-")+"\n";
		}
		return gridString;
	}
}
