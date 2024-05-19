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
        //          w o r d - > w o r d s
        //          0 1 2 3 4 5 6 7 8 9 10
        String a = "word->words";
        System.out.println(a.lastIndexOf(">"));
        //a.substring(a.lastIndexOf(">")+1)
        System.out.println(a.substring(6));
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