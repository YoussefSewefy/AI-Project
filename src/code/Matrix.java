package code;

import java.util.ArrayList;
import java.util.HashSet;

public class Matrix extends GenericSearchProblem {

	static int m, n;

	// carry
	static int maxCarriedNum;

	static int expandedNum;

	static int NeoX, NeoY;
	static int TelephoneX, TelephoneY;
	static int hostagesNum;
	static int pillsNum;
	static int agentsNum;
	static int padsNum;
	static ArrayList<String> finalPathStates = new ArrayList<String>();
	static ArrayList<int[]> takenPositions = new ArrayList<int[]>();
	static ArrayList<int[]> hostages = new ArrayList<int[]>();
	static ArrayList<int[]> pills = new ArrayList<int[]>();
	static ArrayList<int[]> agents = new ArrayList<int[]>();
	static ArrayList<int[][]> pads = new ArrayList<int[][]>();

	static Object[][] grid; // TO BE DELETED

	public Matrix(String[] operators, String initialState) {
		// state has:
		// 0 NeoX,NeoY,NeoDamage;
		// 1 TelephoneX,TelehoneY;
		// 2 AgentX1,AgentY1, ...,AgentXk,AgentYk;
		// 3 AgentMustKillX1,AgentMustKillY1, ...,AgentMustKillX1,AgentMustKillY1;
		// 4 PillX1,PillY1, ...,PillXg,PillYg;
		// 5 HostageX1,HostageY1,HostageDamage1, ...,HostageXw,HostageYw,HostageDamagew;
		// 6 Agents Killed;
		// 7 Hostages Saved;
		// 8 Carried Hostages;
		// 9 Dead Hostages;

		this.operators = operators;
		this.initialState = initialState;
		this.stateSpace = new HashSet<String>();
	}

	String goalTest(Node node) {
		String[] splitState = node.state.split(";");
		int damage = Integer.parseInt(splitState[0].split(",")[2]);
		if (damage >= 100) {
			return "no solution";
		}
		String[] hostages = splitState[5].split(",");
		String[] mustKillAgents = splitState[3].split(",");
		String[] carriedHostages = splitState[8].split(",");
		String[] neo = splitState[0].split(",");
		String[] tel = splitState[1].split(",");
		if (hostages[0].equals("") && mustKillAgents[0].equals("") && carriedHostages[0].equals("")
				&& neo[0].equals(tel[0]) && neo[1].equals(tel[1])) {
			return "goal";
		}
		return "not a goal";
	}

	int[] pathCost(String state, int depth) {
		int[] cost = new int[3];
		String[] stateSplit = state.split(";");
		cost[0] = Integer.parseInt(stateSplit[9]);
		cost[1] = Integer.parseInt(stateSplit[6]);
		cost[2] = depth;
		return cost;
	}

	Node genericSearchProcedure(String strategy) {
		Node goalNode = null;
		if (strategy.toUpperCase().equals(("BF"))) {
			goalNode = Strategies.BF(this);
		} else if (strategy.toUpperCase().equals(("DF"))) {
			goalNode = Strategies.DF(this);
		} else if (strategy.toUpperCase().equals("ID")) {
			goalNode = Strategies.ID(this);
		} else if(strategy.toUpperCase().equals("UC")) {
			goalNode = Strategies.UC(this);
		} else if(strategy.toUpperCase().equals("GR1")) {
			goalNode = Strategies.GR1(this);
		} else if(strategy.toUpperCase().equals("GR2")) {
			goalNode = Strategies.GR2(this);
		}else if(strategy.toUpperCase().equals("AS1")) {
			goalNode = Strategies.AS1(this);
		}else if(strategy.toUpperCase().equals("AS2")) {
			goalNode = Strategies.AS2(this);
		}
		return goalNode;
	}

	public static String genGrid() {
		// generate grid dimensions
		m = (int)(Math.random() * (15 - 5 + 1) + 5);
		n = (int)(Math.random() * (15 - 5 + 1) + 5);

		// generate number of members Neo can carry
		maxCarriedNum = (int) (Math.random() * (4 - 1 + 1) + 1);

		// genereate Neo X and Y
		NeoX = (int) (Math.random() * ((m - 1) - 0 + 1) + 0);
		NeoY = (int) (Math.random() * ((n - 1) - 0 + 1) + 0);
		int[] neo = { NeoX, NeoY };
		takenPositions.add(neo);

		boolean canAddPos = false;
		while (!canAddPos) {
			TelephoneX = (int) (Math.random() * ((m - 1) - 0 + 1) + 0);
			TelephoneY = (int) (Math.random() * ((n - 1) - 0 + 1) + 0);
			int[] tel = { TelephoneX, TelephoneY };
			canAddPos = GenGridHelper.inArray(takenPositions, tel) ? false : true;
		}
		if (canAddPos) {
			int[] tel = { TelephoneX, TelephoneY };
			takenPositions.add(tel);
		}
		hostagesNum = (int) (Math.random() * (10 - 3 + 1) + 3);
		for (int i = 0; i < hostagesNum; i++) {
			int damage = (int) (Math.random() * (99 - 1 + 1) + 1);
			int hostageX = 0;
			int hostageY = 0;
			canAddPos = false;
			while (!canAddPos) {
				hostageX = (int) (Math.random() * ((m - 1) - 0 + 1) + 0);
				hostageY = (int) (Math.random() * ((n - 1) - 0 + 1) + 0);
				int[] hostagePos = { hostageX, hostageY };
				canAddPos = GenGridHelper.inArray(takenPositions, hostagePos) ? false : true;
			}
			if (canAddPos) {
				int[] hostagePos = { hostageX, hostageY };
				takenPositions.add(hostagePos);
				int[] hostage = { hostageX, hostageY, damage };
				hostages.add(hostage);
			}
		}
		pillsNum = (int) (Math.random() * (hostagesNum - 0 + 1) + 0);
		for (int i = 0; i < pillsNum; i++) {
			int pillX = 0;
			int pillY = 0;
			canAddPos = false;
			while (!canAddPos) {
				pillX = (int) (Math.random() * ((m - 1) - 0 + 1) + 0);
				pillY = (int) (Math.random() * ((n - 1) - 0 + 1) + 0);
				int[] pillPos = { pillX, pillY };
				canAddPos = GenGridHelper.inArray(takenPositions, pillPos) ? false : true;
			}
			if (canAddPos) {
				int[] pillPos = { pillX, pillY };
				takenPositions.add(pillPos);
				pills.add(pillPos);
			}
		}
		int maxNumberSoFar = (m * n) - takenPositions.size();
		agentsNum = (int) (Math.random() * (maxNumberSoFar - 1 + 1) + 1);
		for (int i = 0; i < agentsNum; i++) {
			int agentX = 0;
			int agentY = 0;
			canAddPos = false;
			while (!canAddPos) {
				agentX = (int) (Math.random() * ((m - 1) - 0 + 1) + 0);
				agentY = (int) (Math.random() * ((n - 1) - 0 + 1) + 0);
				int[] agentPos = { agentX, agentY };
				canAddPos = GenGridHelper.inArray(takenPositions, agentPos) ? false : true;
			}
			if (canAddPos) {
				int[] agentPos = { agentX, agentY };
				takenPositions.add(agentPos);
				agents.add(agentPos);
			}
		}
		maxNumberSoFar = (m * n) - takenPositions.size();
		padsNum = (int) ((Math.random() * (maxNumberSoFar - 1 + 1) + 1) / 2);
		for (int i = 0; i < padsNum; i++) {
			int startPadX = 0;
			int startPadY = 0;
			int finishPadX = 0;
			int finishPadY = 0;
			canAddPos = false;
			int[][] padCouple = new int[2][2];
			while (!canAddPos) {
				startPadX = (int) (Math.random() * ((m - 1) - 0 + 1) + 0);
				startPadY = (int) (Math.random() * ((n - 1) - 0 + 1) + 0);
				int[] startPadPos = { startPadX, startPadY };
				canAddPos = GenGridHelper.inArray(takenPositions, startPadPos) ? false : true;
			}
			int[] startPadPos = { startPadX, startPadY };
			if (canAddPos) {
				takenPositions.add(startPadPos);
				padCouple[0] = startPadPos;
			}
			canAddPos = false;
			while (!canAddPos) {
				finishPadX = (int) (Math.random() * ((m - 1) - 0 + 1) + 0);
				finishPadY = (int) (Math.random() * ((n - 1) - 0 + 1) + 0);
				int[] finishPadPos = { finishPadX, finishPadY };
				canAddPos = GenGridHelper.inArray(takenPositions, finishPadPos) ? false : true;
			}
			int[] finishPadPos = { finishPadX, finishPadY };
			if (canAddPos) {
				takenPositions.add(finishPadPos);
				padCouple[1] = finishPadPos;
				pads.add(padCouple);
			}
		}
		String gridString = m + "," + n + ";" + maxCarriedNum + ";" + NeoX + "," + NeoY + ";" + TelephoneX + ","
				+ TelephoneY + ";" + GenGridHelper.arraysToString(agents) + ";" + GenGridHelper.arraysToString(pills)
				+ ";" + GenGridHelper.arrays2DToString(pads) + ";" + GenGridHelper.hostagesToString(hostages);
		return gridString;
	}

	private static void createArrayListsFromString(String grid) {
		String[] gridSplit = grid.split(";");
		String[] size = gridSplit[0].split(",");
		int maxCarried = Integer.parseInt(gridSplit[1]);
		String[] NeoPos = gridSplit[2].split(",");
		String[] TelPos = gridSplit[3].split(",");
		String[] agentsArr = gridSplit[4].split(",");
		String[] pillsArr = gridSplit[5].split(",");
		String[] padsArr = gridSplit[6].split(",");
		String[] hostagesArr = gridSplit[7].split(",");

		m = Integer.parseInt(size[0]);
		n = Integer.parseInt(size[1]);

		maxCarriedNum = maxCarried;

		NeoX = Integer.parseInt(NeoPos[0]);
		NeoY = Integer.parseInt(NeoPos[1]);

		TelephoneX = Integer.parseInt(TelPos[0]);
		TelephoneY = Integer.parseInt(TelPos[1]);

		for (int i = 1; i < agentsArr.length; i += 2) {
			agents.add(new int[] { Integer.parseInt(agentsArr[i - 1]), Integer.parseInt(agentsArr[i]) });
		}
		for (int i = 1; i < pillsArr.length; i += 2) {
			pills.add(new int[] { Integer.parseInt(pillsArr[i - 1]), Integer.parseInt(pillsArr[i]) });
		}
		for (int i = 3; i < padsArr.length; i += 4) {
			pads.add(new int[][] { { Integer.parseInt(padsArr[i - 3]), Integer.parseInt(padsArr[i - 2]) },
					{ Integer.parseInt(padsArr[i - 1]), Integer.parseInt(padsArr[i]) } });
		}
		for (int i = 2; i < hostagesArr.length; i += 3) {
			hostages.add(new int[] { Integer.parseInt(hostagesArr[i - 2]), Integer.parseInt(hostagesArr[i - 1]),
					Integer.parseInt(hostagesArr[i]) });
		}
	}


	public static String getSolutionString(Node node) {
		String solution = "";
		String state = node.state;
		String[] stateSplit = state.split(";");
		int deadHostages = Integer.parseInt(stateSplit[9]);
		int killedAgents = Integer.parseInt(stateSplit[6]);
		while (node != null) {
			solution = "," + node.operator + solution;
			finalPathStates.add(0, node.state+";"+node.operator);
			node = node.parentNode;
		}
		solution = solution.substring(2);
		solution += ";" + deadHostages + ";" + killedAgents + ";" + expandedNum;
		return solution;
	}

	public static String solve(String grid, String strategy, boolean visualize) {
		createArrayListsFromString(grid);
		String[] splitGrid = grid.split(";");
		String[] operators = new String[] { "left", "right", "up", "down", "carry", "drop", "takePill", "kill", "fly" };
		String initialState = splitGrid[2] + ",0;" + splitGrid[3] + ";" + splitGrid[4] + ";;" + splitGrid[5] + ";"
				+ splitGrid[7] + ";0;0;;0;";
		Matrix x = new Matrix(operators, initialState);

		Node goalNode = x.genericSearchProcedure(strategy);
		if (goalNode == null) {
			return "No Solution";
		}
		String solution = getSolutionString(goalNode);
		
		if(visualize) {
			Visualize.visualize(grid, finalPathStates);
		}
		return solution;
	}

	public static void main(String[] args) {
		String gridString = genGrid();
//		Example 1:
//		String gridString = "5,5;3;1,3;4,0;0,1,3,2,4,3,2,4,0,4;3,4,3,0,4,2;1,4,1,2,1,2,1,4,0,3,1,0,1,0,0,3;4,4,45,3,3,12,0,2,88";
		
//		Example 2:
//		String gridString = "5,5;2;3,0;4,3;2,1,2,2,3,1,0,0,1,1,4,2,3,3,1,3,0,1;2,4,3,2,3,4,0,4;4,4,4,0,4,0,4,4;1,4,57,2,0,46";
		System.out.println("HII");
		System.out.println("The grid is: " + gridString);
		String solution = solve(gridString, "AS2", false);
		System.out.println("\nThe solution is: " +solution);
	}
}
