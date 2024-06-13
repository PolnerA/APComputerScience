import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static class Operation{
        public int id;
        //94 is exponents ^
        //Multiplication and Division is *:42 /:47
        //Addition and subtraction +:43 -:45
        //0 means not an op
        public Operation(int id) {
            this.id=id;
        }
        public int PerformOperation(int a, int b){
            if(id==94){
                return (int)Math.pow(a,b);
            }if(id==42){
                return a*b;
            }if(id==47){
                return a/b;
            }if(id==43){
                return a+b;
            }if(id==45){
                return a-b;
            }
            return a;
        }
    }
    public static void main(String[] args) throws IOException {
        // Sample ArrayList of HashSets of strings
        ArrayList<HashSet<String>> arrayList = new ArrayList<>();
        HashSet<String> set1 = new HashSet<>();
        set1.add("A");
        set1.add("B");
        set1.add("C");

        HashSet<String> set2 = new HashSet<>();
        set2.add("X");
        set2.add("Y");

        arrayList.add(set1);
        arrayList.add(set2);

        // Method call to generate combinations
        generateCombinations(arrayList);
    }
    public static void generateCombinations(ArrayList<HashSet<String>> arrayList) {
        int[] indices = new int[arrayList.size()];
        int totalCombinations = 1;
        for (HashSet<String> set : arrayList) {
            totalCombinations *= set.size();
        }

        for (int i = 0; i < totalCombinations; i++) {
            ArrayList<String> combination = new ArrayList<>();
            for (int j = 0; j < arrayList.size(); j++) {
                HashSet<String> set = arrayList.get(j);
                int setSize = set.size();
                int index = indices[j];
                String[] setArray = set.toArray(new String[0]);
                combination.add(setArray[index]);
                if ((i / totalCombinations) % setSize == 0) {
                    indices[j] = (indices[j] + 1) % setSize;
                }
            }
            System.out.println(combination);
        }
    }
    public static boolean isNumber(String string,int index){
        char a = string.charAt(index);
        try{
            String s =""+a;
            Integer.parseInt(s);
        }catch (Exception e){
            if(a=='-'){
                if(index==string.length()-1) {
                    return false;
                }if(isNumber(string,index+1)){
                    return true;
                }
            }
            return false;
        }
        return true;
    }

}