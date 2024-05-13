import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
        String s = "Word";
        char[] arr = s.toCharArray();
        ArrayList<Character> charlist = new ArrayList<>();
        for(int i=0;i< arr.length;i++){
            charlist.add(arr[i]);
        }
        File neighbor = new File("dictionaryWithNeighbors");
        neighbor.createNewFile();
        FileWriter writer = new FileWriter(neighbor);
        writer.write(s);
        for(int i=0;i<=s.length();i++){
            for(int j =0;j<26;j++){
                char g = (char)('a'+j);
                ArrayList<Character> newlist= new ArrayList<Character>();
                for(int k =0;k<charlist.size();k++){
                    newlist.add(charlist.get(k));
                }
                String newWord2="";
                newlist.add(i,g);
                for(int k=0;k<newlist.size();k++){
                    newWord2= newWord2 + newlist.get(k);
                }
                writer.write("|"+newWord2);

            }
        }
        writer.close();

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