package code;

import java.util.ArrayList;

public class GenGridHelper {

    public static boolean inArray(ArrayList<int[]> inputArr, int[] checkArr){
        for(int i =0; i<inputArr.size(); i++){
            if(inputArr.get(i)[0] == checkArr[0] && inputArr.get(i)[1] == checkArr[1]){
                return true;
            }
        }
        return false;
    }
    public static String arraysToString(ArrayList<int[]> inputArr){
        String out = "";
        if(inputArr.size() == 0){
            return out;
        }
        for(int i = 0; i<inputArr.size()-1; i++){
            out += inputArr.get(i)[0] + "," + inputArr.get(i)[1]+",";
        }
        out += inputArr.get(inputArr.size()-1)[0] + "," + inputArr.get(inputArr.size()-1)[1];
        return out;
    }
    public static String hostagesToString(ArrayList<int[]> inputArr){
        String out = "";
        if(inputArr.size() == 0){
            return out;
        }
        for(int i = 0; i<inputArr.size()-1; i++){
            out += inputArr.get(i)[0] + "," + inputArr.get(i)[1]+"," + inputArr.get(i)[2]+",";
        }
        out += inputArr.get(inputArr.size()-1)[0] + "," + inputArr.get(inputArr.size()-1)[1] + "," + inputArr.get(inputArr.size()-1)[2];
        return out;
    }
    public static String arrays2DToString(ArrayList<int[][]> inputArr){
        String out = "";
        if(inputArr.size() == 0){
            return out;
        }
        for(int i = 0; i<inputArr.size()-1; i++){
            out += inputArr.get(i)[0][0] + "," + inputArr.get(i)[0][1]+",";
            out += inputArr.get(i)[1][0] + "," + inputArr.get(i)[1][1]+",";
            out += inputArr.get(i)[1][0] + "," + inputArr.get(i)[1][1]+",";
            out += inputArr.get(i)[0][0] + "," + inputArr.get(i)[0][1]+",";
        }
        out += inputArr.get(inputArr.size()-1)[0][0] + "," + inputArr.get(inputArr.size()-1)[0][1] +",";
        out += inputArr.get(inputArr.size()-1)[1][0] + "," + inputArr.get(inputArr.size()-1)[1][1] +",";
        out += inputArr.get(inputArr.size()-1)[1][0] + "," + inputArr.get(inputArr.size()-1)[1][1] +",";
        out += inputArr.get(inputArr.size()-1)[0][0] + "," + inputArr.get(inputArr.size()-1)[0][1];
        return out;
    }

}

