package code;
public class DecideOptions {
    
    // Decide what are the nodes to create for the options that Neo has at his current position
    public static Node[] decideOptions(Node node, Matrix x){
        Node[] options = new Node[9];
        String state = node.state;
        String[] stateArr = state.split(";");
        String[] NeoPos = stateArr[0].split(",");
        String[] TelephonePos = stateArr[1].split(",");
        String[] AgentsArr = stateArr[2].split(",");
        String[] AgentsMustKill = stateArr[3].split(",");
        String[] PillsArr = stateArr[4].split(",");
        String[] HostagesArr = stateArr[5].split(",");
        String[] carriedHostages = stateArr[8].split(",");

        boolean neighborAgent = false;
        // checking left
        if(Integer.parseInt(NeoPos[1])!=0){
            if(DecideOptionsHelpers.checkAgentInPosition(new int[] {Integer.parseInt(NeoPos[0]), Integer.parseInt(NeoPos[1])-1}, AgentsArr, AgentsMustKill)
             && !DecideOptionsHelpers.checkHostageWillDie(node) ){
                neighborAgent = true;
            }
            else if(!DecideOptionsHelpers.checkAgentInPosition(new int[] {Integer.parseInt(NeoPos[0]), Integer.parseInt(NeoPos[1])-1}, AgentsArr, AgentsMustKill)
            		&&!DecideOptionsHelpers.checkNeighborHostageDamage(node, new int[] {Integer.parseInt(NeoPos[0]), Integer.parseInt(NeoPos[1])-1})
             && !node.operator.equals("right")){
                options[4] = DecideOptionsHelpers.left(node, x);
            }
        }
        // checking right
        if(Integer.parseInt(NeoPos[1]) != Matrix.n-1){
            if(DecideOptionsHelpers.checkAgentInPosition(new int[] {Integer.parseInt(NeoPos[0]), Integer.parseInt(NeoPos[1])+1}, AgentsArr, AgentsMustKill)
            && !DecideOptionsHelpers.checkHostageWillDie(node)){
                neighborAgent = true;
            }
            else if(!DecideOptionsHelpers.checkAgentInPosition(new int[] {Integer.parseInt(NeoPos[0]), Integer.parseInt(NeoPos[1])+1}, AgentsArr, AgentsMustKill)
            		&&!DecideOptionsHelpers.checkNeighborHostageDamage(node, new int[] {Integer.parseInt(NeoPos[0]), Integer.parseInt(NeoPos[1])+1})
            && !node.operator.equals("left")){
                options[5] = DecideOptionsHelpers.right(node, x);
            }
        }
        // checking up
        if(Integer.parseInt(NeoPos[0]) != 0){
            if(DecideOptionsHelpers.checkAgentInPosition(new int[] {Integer.parseInt(NeoPos[0])-1, Integer.parseInt(NeoPos[1])}, AgentsArr, AgentsMustKill)
            && !DecideOptionsHelpers.checkHostageWillDie(node)){
                neighborAgent = true;
            }
            else if(!DecideOptionsHelpers.checkAgentInPosition(new int[] {Integer.parseInt(NeoPos[0])-1, Integer.parseInt(NeoPos[1])}, AgentsArr, AgentsMustKill)
            		&&!DecideOptionsHelpers.checkNeighborHostageDamage(node, new int[] {Integer.parseInt(NeoPos[0])-1, Integer.parseInt(NeoPos[1])})
            && !node.operator.equals("down")){
                options[6] = DecideOptionsHelpers.up(node, x);
            }
        }
        // checking down
        if(Integer.parseInt(NeoPos[0]) != Matrix.m-1){
            if(DecideOptionsHelpers.checkAgentInPosition(new int[] {Integer.parseInt(NeoPos[0])+1, Integer.parseInt(NeoPos[1])}, AgentsArr, AgentsMustKill)
            && !DecideOptionsHelpers.checkHostageWillDie(node)){
                neighborAgent = true;
            }
            else if(!DecideOptionsHelpers.checkAgentInPosition(new int[] {Integer.parseInt(NeoPos[0])+1, Integer.parseInt(NeoPos[1])}, AgentsArr, AgentsMustKill)
            		&&!DecideOptionsHelpers.checkNeighborHostageDamage(node, new int[] {Integer.parseInt(NeoPos[0])+1, Integer.parseInt(NeoPos[1])})
            && !node.operator.equals("up")){
                options[7]= DecideOptionsHelpers.down(node, x);
            }
        }
        if(neighborAgent){
            options[8] = DecideOptionsHelpers.kill(node, x);
        }

        // checking current position
        if(DecideOptionsHelpers.checkPillInPosition(new int[] {Integer.parseInt(NeoPos[0]), Integer.parseInt(NeoPos[1])}, PillsArr)){
            options[2] = DecideOptionsHelpers.takePill(node, x);
        }
        else if(TelephonePos[0].equals(NeoPos[0]) && TelephonePos[1].equals(NeoPos[1]) && !carriedHostages[0].equals("")){
            options[1] = DecideOptionsHelpers.drop(node, x);
        }
        else if(DecideOptionsHelpers.checkHostageInPosition(new int[] {Integer.parseInt(NeoPos[0]), Integer.parseInt(NeoPos[1])}, HostagesArr) 
                && (carriedHostages.length < Matrix.maxCarriedNum || carriedHostages[0].equals(""))){
            options[0] = DecideOptionsHelpers.carry(node, x);
        }
        else if(DecideOptionsHelpers.checkPadInPosition(new int[] {Integer.parseInt(NeoPos[0]), Integer.parseInt(NeoPos[1])})[0] != -1 
        && !node.operator.equals("fly")){
            options[3] = DecideOptionsHelpers.fly(node, x);
        }
        return options;
    }
}
