package code;

import java.util.ArrayList;

public class GeneralHelpers{
    
    public static String stateArrayToString(String[] array){
        String output = "";
        if(array.length != 0){
            for(int i =0; i<array.length-1; i++){
                output += array[i] +";"; 
            }
            output += array[array.length-1];
        }
        return output; 
    }

    public static String arraylistToString(ArrayList<String> arraylist){
        String output = "";
        if(arraylist.size() != 0){
            for(int i =0; i<arraylist.size()-1; i++){
                if(!arraylist.get(i).equals(""))
                    output += arraylist.get(i) +","; 
            }
            output += arraylist.get(arraylist.size()-1);
        }
        return output; 
    }
}