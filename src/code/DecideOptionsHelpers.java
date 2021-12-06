package code;

import java.util.ArrayList;
import java.util.Arrays;

public class DecideOptionsHelpers {

    // Set the damage of all hostages in the grid (gets called upon every action) 
    // and if a hostage dies, decide whether he will be an agent or die based on carried.
    // This method is also used for calculating damage after taking a pill.
    public static String setDamage(String[] stateArr, boolean increaseDamage){
        String[] hostagesArr = stateArr[5].split(",");
        String[] carriedHostagesArr = stateArr[8].split(",");
        String[] agentsArr = stateArr[3].split(",");
        ArrayList<String> hostagesArraylist = new ArrayList<String>();
        ArrayList<String> agentsArraylist = new ArrayList<String>(Arrays.asList(agentsArr));
        for(int i = 2; i<hostagesArr.length; i+=3){
            if(increaseDamage){
                int hostDamage = Integer.parseInt(hostagesArr[i]) +2;
                if(hostDamage < 100){
                    hostagesArr[i] = ""+ hostDamage;
                    hostagesArraylist.add(hostagesArr[i-2]);
                    hostagesArraylist.add(hostagesArr[i-1]);
                    hostagesArraylist.add(hostagesArr[i]);
                }
                else{
                    stateArr[9] = ""+ (Integer.parseInt(stateArr[9]) +1) ;
                    agentsArraylist.add(hostagesArr[i-2]);
                    agentsArraylist.add(hostagesArr[i-1]);
                }
            }
            else{
                int hostDamage = Integer.parseInt(hostagesArr[i]) -20;
                if(hostDamage > 0){
                    hostagesArr[i] = ""+ hostDamage;
                    hostagesArraylist.add(hostagesArr[i-2]);
                    hostagesArraylist.add(hostagesArr[i-1]);
                    hostagesArraylist.add(hostagesArr[i]);
                }
                else{
                    hostagesArr[i] = "0";
                    hostagesArraylist.add(hostagesArr[i-2]);
                    hostagesArraylist.add(hostagesArr[i-1]);
                    hostagesArraylist.add(hostagesArr[i]);
                }
            }
        }
        if(!carriedHostagesArr[0].equals("")){
            for(int i = 0; i<carriedHostagesArr.length; i++){
                if(increaseDamage){
                    if(!carriedHostagesArr[i].equals("100")){
                        int hostDamage = Integer.parseInt(carriedHostagesArr[i]) +2;
                        if(hostDamage < 100){
                            carriedHostagesArr[i] = ""+ hostDamage;
                        }
                        else{
                            stateArr[9] = ""+ (Integer.parseInt(stateArr[9]) +1) ;
                            carriedHostagesArr[i] = "100";
                        }
                    }
                }
                else{
                    int hostDamage = Integer.parseInt(carriedHostagesArr[i]) -20;
                    if(hostDamage == 80) {
                    	carriedHostagesArr[i] = "100";
                    }
                    else if(hostDamage > 0){
                        carriedHostagesArr[i] = ""+ hostDamage;
                    }
                    else{
                        carriedHostagesArr[i] = "0";
                    }
                }
            }
        }
        if(!increaseDamage){
            String[] NeoInfo = stateArr[0].split(",");
            int neoDamage = Integer.parseInt(NeoInfo[2]) -20;
            if(neoDamage > 0){
                NeoInfo[2] = ""+ neoDamage;
                ArrayList<String> NeoArrayList = new ArrayList<String>(Arrays.asList(NeoInfo));
                stateArr[0] = GeneralHelpers.arraylistToString(NeoArrayList);
            }
            else{
                NeoInfo[2] = "0";
                ArrayList<String> NeoArrayList = new ArrayList<String>(Arrays.asList(NeoInfo));
                stateArr[0] = GeneralHelpers.arraylistToString(NeoArrayList);
            }
        }
        String hostagesAfterChange = GeneralHelpers.arraylistToString(hostagesArraylist);
        String agentsAfterChange = GeneralHelpers.arraylistToString(agentsArraylist);
        stateArr[5] = hostagesAfterChange;
        stateArr[3] = agentsAfterChange;
        ArrayList<String> carriedHostagesArrayList = new ArrayList<String>(Arrays.asList(carriedHostagesArr));
        stateArr[8] = GeneralHelpers.arraylistToString(carriedHostagesArrayList);
        String state = GeneralHelpers.stateArrayToString(stateArr);
        return state;
    }

    // Check if the hostage Neo is standing in the same cell with will die if Neo makes a kill move.
    public static boolean checkHostageWillDie(Node node){
        String[] stateSplit = node.state.split(";");
        String[] NeoPos = stateSplit[0].split(",");
        String[] hostagesArr = stateSplit[5].split(",");
        for(int i = 1; i<hostagesArr.length-1; i+=3){
            String hostX = hostagesArr[i-1];
            String hostY = hostagesArr[i];
            if(hostX.equals(NeoPos[0]) && hostY.equals(NeoPos[1])){
                if(Integer.parseInt(hostagesArr[i+1]) +2 >=100){
                    return true;
                }
            }
        }
        return false;
    }

    // Remove hostage from grid and add him to the "Carried Hostages" in the state which saves the damages of carried hostages.
    public static String[] removeHostage(String[] stateArr){
        String[] NeoPos = stateArr[0].split(",");
        String[] hostagesArr = stateArr[5].split(",");
        String hostageDamage = "";
        ArrayList<String> hostagesArraylist = new ArrayList<String>();
        for(int i = 2; i<hostagesArr.length; i+=3){
            String hostX = hostagesArr[i-2];
            String hostY = hostagesArr[i-1];
            if(!(hostX.equals(NeoPos[0]) && hostY.equals(NeoPos[1]))){
                hostagesArraylist.add(hostagesArr[i-2]);
                hostagesArraylist.add(hostagesArr[i-1]);
                hostagesArraylist.add(hostagesArr[i]);
            }
            else{
                hostageDamage = hostagesArr[i];
            }
        }
        String hostagesAfterChange = GeneralHelpers.arraylistToString(hostagesArraylist);
        stateArr[5] = hostagesAfterChange;
        stateArr[8] = stateArr[8].equals("")? ""+hostageDamage : stateArr[8] + "," + hostageDamage;
        return stateArr;
    }

    // Remove pill from grid upon taking a pill.
    public static String[] removePill(String[] stateArr){
        String[] NeoPos = stateArr[0].split(",");
        String[] pillsArr = stateArr[4].split(",");
        ArrayList<String> pillsArraylist = new ArrayList<String>();
        for(int i = 1; i<pillsArr.length; i+=2){
            String pillX = pillsArr[i-1];
            String pillY = pillsArr[i];
            if(!(pillX.equals(NeoPos[0]) && pillY.equals(NeoPos[1]))){
                pillsArraylist.add(pillsArr[i-1]);
                pillsArraylist.add(pillsArr[i]);
            }
        }
        String pillsAfterChange = GeneralHelpers.arraylistToString(pillsArraylist);
        stateArr[4] = pillsAfterChange;
        return stateArr;
    }

    // Remove agent from grid upon "Kill" action
    public static String[] removeAgent(String[] stateArr, int[] killPosition){
        String[] agentsArr = stateArr[2].split(",");
        String[] mustKillAgentsArr = stateArr[3].split(",");
        ArrayList<String> agentsArrayList = new ArrayList<String>();
        ArrayList<String> mustKillAgentsArrayList = new ArrayList<String>();
        for(int i = 1; i<agentsArr.length; i+=2){
            String agentX = agentsArr[i-1];
            String agentY = agentsArr[i];
            if(!((agentX.equals(""+(killPosition[0]-1)) && agentY.equals(""+killPosition[1])) 
            || (agentX.equals(""+(killPosition[0]+1)) && agentY.equals(""+killPosition[1])) 
            || (agentX.equals(""+killPosition[0]) && agentY.equals(""+(killPosition[1]-1)))
            || (agentX.equals(""+killPosition[0]) && agentY.equals(""+(killPosition[1]+1))))){
                agentsArrayList.add(agentsArr[i-1]);
                agentsArrayList.add(agentsArr[i]);
            }
            else {
            	stateArr[6] = "" + (Integer.parseInt(stateArr[6])+1);
            }
        }
        for(int i = 1; i<mustKillAgentsArr.length; i+=2){
            String agentX = mustKillAgentsArr[i-1];
            String agentY = mustKillAgentsArr[i];
            if(!((agentX.equals(""+(killPosition[0]-1)) && agentY.equals(""+killPosition[1])) 
            || (agentX.equals(""+(killPosition[0]+1)) && agentY.equals(""+killPosition[1])) 
            || (agentX.equals(""+killPosition[0]) && agentY.equals(""+(killPosition[1]-1)))
            || (agentX.equals(""+killPosition[0]) && agentY.equals(""+(killPosition[1]+1))))){
                mustKillAgentsArrayList.add(mustKillAgentsArr[i-1]);
                mustKillAgentsArrayList.add(mustKillAgentsArr[i]);
            }
            else {
            	stateArr[6] = "" + (Integer.parseInt(stateArr[6])+1);
            }
        }
        String agentsAfterChange = GeneralHelpers.arraylistToString(agentsArrayList);
        String mustKillAgentsAfterChange = GeneralHelpers.arraylistToString(mustKillAgentsArrayList);
        stateArr[2] = agentsAfterChange;
        stateArr[3] = mustKillAgentsAfterChange;
        return stateArr;
    }

    // Create node with operator "left"
    public static Node left(Node node, Matrix x){
        String state = node.state;
        String[] stateArr = state.split(";");
        String[] NeoPos = stateArr[0].split(",");
        int NeoY = Integer.parseInt(NeoPos[1]) -1;
        NeoPos[1] = ""+NeoY;
        stateArr[0] = NeoPos[0]+","+NeoPos[1]+","+NeoPos[2];
        String newState = setDamage(stateArr, true);
        Node newNode = new Node(newState, node, "left", node.depth +1, x.pathCost(newState, node.depth +1)); //me7taga tet3'ayar lama ne5osh fel 7agat beta3et el cose
        return newNode;
    }

    // Create node with operator "right"
    public static Node right(Node node, Matrix x){
        String state = node.state;
        String[] stateArr = state.split(";");
        String[] NeoPos = stateArr[0].split(",");
        int NeoY = Integer.parseInt(NeoPos[1]) +1;
        NeoPos[1] = ""+NeoY;
        stateArr[0] = NeoPos[0]+","+NeoPos[1]+","+NeoPos[2];
        String newState = setDamage(stateArr, true);
        Node newNode = new Node(newState, node, "right", node.depth +1, x.pathCost(newState, node.depth +1)); //me7taga tet3'ayar lama ne5osh fel 7agat beta3et el cose
        return newNode;
    }

    // Create node with operator "up"
    public static Node up(Node node, Matrix x){
        String state = node.state;
        String[] stateArr = state.split(";");
        String[] NeoPos = stateArr[0].split(",");
        int NeoX = Integer.parseInt(NeoPos[0]) -1;
        NeoPos[0] = ""+NeoX;
        stateArr[0] = NeoPos[0]+","+NeoPos[1]+","+NeoPos[2];
        String newState = setDamage(stateArr, true);
        Node newNode = new Node(newState, node, "up", node.depth +1, x.pathCost(newState, node.depth +1)); //me7taga tet3'ayar lama ne5osh fel 7agat beta3et el cose
        return newNode;
    }

    // Create node with operator "down"
    public static Node down(Node node, Matrix x){
        String state = node.state;
        String[] stateArr = state.split(";");
        String[] NeoPos = stateArr[0].split(",");
        int NeoX = Integer.parseInt(NeoPos[0]) +1;
        NeoPos[0] = ""+NeoX;
        stateArr[0] = NeoPos[0]+","+NeoPos[1]+","+NeoPos[2];
        String newState = setDamage(stateArr, true);
        Node newNode = new Node(newState, node, "down", node.depth +1,x.pathCost(newState, node.depth +1)); //me7taga tet3'ayar lama ne5osh fel 7agat beta3et el cose
        return newNode;
    }

    // Create node with operator "drop"
    public static Node drop(Node node, Matrix x){
        String state = node.state;
        String[] stateArr = state.split(";");
        String[] carriedHostages = stateArr[8].split(",");
        int numberOfDeadCarried = 0;
        for(int i =0; i<carriedHostages.length; i++){
            if(!carriedHostages[i].equals(""))
                if(Integer.parseInt(carriedHostages[i]) >=100){
                    numberOfDeadCarried++;
                } 
        }
        stateArr[8] = "";
        if(Integer.parseInt(stateArr[7]) +carriedHostages.length-1 - numberOfDeadCarried == -1)
            stateArr[7] = "0";
        else
            stateArr[7] = "" + (Integer.parseInt(stateArr[7]) +carriedHostages.length-1 - numberOfDeadCarried);
        String newState =  setDamage(stateArr, true);
        Node newNode = new Node(newState, node, "drop", node.depth +1, x.pathCost(newState, node.depth +1)); //me7taga tet3'ayar lama ne5osh fel 7agat beta3et el cose
        return newNode;
    }

    // Create node with operator "carry"
    public static Node carry(Node node, Matrix x){
        String state = node.state;
        String[] stateArr = state.split(";");
        stateArr = removeHostage(stateArr);
        String newState =  setDamage(stateArr, true);
        Node newNode = new Node(newState, node, "carry", node.depth +1, x.pathCost(newState, node.depth +1)); //me7taga tet3'ayar lama ne5osh fel 7agat beta3et el cose
        return newNode;
    }

    // Create node with operator "takePill"
    public static Node takePill(Node node, Matrix x){
        String state = node.state;
        String[] stateArr = state.split(";");
        stateArr = removePill(stateArr);
        String newState =  setDamage(stateArr, false);
        Node newNode = new Node(newState, node, "takePill", node.depth +1, x.pathCost(newState, node.depth +1)); //me7taga tet3'ayar lama ne5osh fel 7agat beta3et el cose
        return newNode;
    }

    // Create node with operator "fly"
    public static Node fly(Node node, Matrix x){
        String state = node.state;
        String[] stateArr = state.split(";");
        String[] NeoPos = stateArr[0].split(",");
        int NeoX = Integer.parseInt(NeoPos[0]);
        int NeoY = Integer.parseInt(NeoPos[1]);
        for(int i =0; i<Matrix.pads.size(); i++){
            int[][] padCouple = Matrix.pads.get(i);
            if(padCouple[0][0] == NeoX && padCouple[0][1] == NeoY){
                NeoX = padCouple[1][0];
                NeoY = padCouple[1][1];
                break;
            }
            else if(padCouple[1][0] == NeoX && padCouple[1][1] == NeoY){
                NeoX = padCouple[0][0];
                NeoY = padCouple[0][1];
                break;
            }
        }
        NeoPos[0] = ""+NeoX;
        NeoPos[1] = ""+NeoY;
        stateArr[0] = NeoPos[0]+","+NeoPos[1]+","+NeoPos[2];
        String newState =  setDamage(stateArr, true);
        Node newNode = new Node(newState, node, "fly", node.depth +1, x.pathCost(newState, node.depth +1)); //me7taga tet3'ayar lama ne5osh fel 7agat beta3et el cose
        return newNode;
    }

    // Create node with operator "kill"
    public static Node kill(Node node, Matrix x){
        String state = node.state;
        String[] stateArr = state.split(";");
        String[] NeoPos = stateArr[0].split(",");
        int NeoX = Integer.parseInt(NeoPos[0]);
        int NeoY = Integer.parseInt(NeoPos[1]);
        stateArr = removeAgent(stateArr, new int[] {NeoX,NeoY});
        stateArr[0] = ""+ NeoX +","+NeoY+"," + (Integer.parseInt(NeoPos[2])+20);
        String newState =  setDamage(stateArr, true);
        Node newNode = new Node(newState, node, "kill", node.depth +1, x.pathCost(newState, node.depth +1));
        return newNode;
    }

    // Check if there is an agent or a mutated hostage in a certain index
    public static boolean checkAgentInPosition(int[] index, String[] AgentsArr, String[] AgentsMustKill){
        boolean agentIn = false;
        for(int i = 0; i<AgentsArr.length-1; i+=2){
            if(Integer.parseInt(AgentsArr[i]) == index[0] && Integer.parseInt(AgentsArr[i+1]) == index[1]){
                agentIn = true;
            }
        }
        for(int i = 0; i<AgentsMustKill.length-1; i+=2){
            if(!AgentsMustKill[i].equals("") && !AgentsMustKill[i+1].equals(""))
                if(Integer.parseInt(AgentsMustKill[i]) == index[0] && Integer.parseInt(AgentsMustKill[i+1]) == index[1]){
                    agentIn = true;
                }
        }
        return agentIn;
    }

    // Check if there is a pill in a certain index
    public static boolean checkPillInPosition(int[] index, String[] PillsArr){
        boolean pillIn = false;
        for(int i = 0; i<PillsArr.length-1; i+=2){
            if(Integer.parseInt(PillsArr[i]) == index[0] && Integer.parseInt(PillsArr[i+1]) == index[1]){
                pillIn = true;
            }
        }
        return pillIn;
    }

    // Check if there is a hostage in a certain index
    public static boolean checkHostageInPosition(int[] index, String[] HostagesArr){
        boolean hostageIn = false;
        for(int i = 2; i<HostagesArr.length; i+=3){
            if(Integer.parseInt(HostagesArr[i-2]) == index[0] && Integer.parseInt(HostagesArr[i-1]) == index[1]){
                return true;
            }
        }
        return hostageIn;
    }

    // Check if there is a pad in a certain index and returns the pad to fly to
    public static int[] checkPadInPosition(int[] index){
        int[] flyTo = new int[] {-1, -1};
        for(int i = 0; i<Matrix.pads.size(); i++){
            if(Matrix.pads.get(i)[0][0] == index[0] && Matrix.pads.get(i)[0][1] == index[1]){
                flyTo[0] = Matrix.pads.get(i)[1][0];
                flyTo[1] = Matrix.pads.get(i)[1][1];
            }
            else if(Matrix.pads.get(i)[1][0] == index[0] && Matrix.pads.get(i)[1][1] == index[1]){
                flyTo[0] = Matrix.pads.get(i)[0][0];
                flyTo[1] = Matrix.pads.get(i)[0][1];
            }
        }
        return flyTo;
    }

    // Check if there is a neighboring hostage has damage more than 98 in a certain index
    public static boolean checkNeighborHostageDamage(Node node, int[] index){
        String[] stateArr = node.state.split(";");
        String[] hostagesArr = stateArr[5].split(",");
        for(int i = 2; i<hostagesArr.length; i+=3){
            if(Integer.parseInt(hostagesArr[i-2]) == index[0] && Integer.parseInt(hostagesArr[i-1]) == index[1]){
                if(Integer.parseInt(hostagesArr[i]) >=98){
                    return true;
                }
            }
        }
        return false;
    }
}
