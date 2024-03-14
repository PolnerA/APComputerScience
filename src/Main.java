import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String Equation = sc.nextLine();
        Stack<Integer> stack = new Stack<>();
        boolean PrevIsInt = false;
        int PrevInt=0;
        int Int=0;
        boolean IsInt = false;
        //have an Integer array with ranges for the integers
        /*55 52 +
        if (Equation.charAt(i)=='-') {//checks if negative is an operator (minus) or negative number
                if(i==Equation.length()-1){
                    number = false;
                }else if(!isNumber(Equation.charAt(i+1))){
                    number=false;
                }else{
                    number=true;
                }

            }
         */
        int j=0; //level of digit
        boolean intisDone=false;
        boolean intisStarted=false;
        for (int i = 0; i < Equation.length(); i++) {
            char a = Equation.charAt(i);
            if(isNumber(a)){
                IsInt=true;
                intisStarted=true;
                Int = Integer.parseInt(""+a);
            }else{
                if(intisStarted){
                    stack.push(PrevInt);
                }
                IsInt=false;
                Int=0;
                PrevInt=0;
                j=0;

            }
            if(PrevIsInt&&IsInt){
                PrevInt*=Math.pow(10,j);
            }
            PrevInt+=Int;
            if(IsInt){
                PrevIsInt=true;
                j++;
            }
            if(2<=stack.size()){
                if(isOp(Equation,i)){
                    int i1 = stack.pop();
                    int i2 = stack.pop();

                }
            }
        }
    }
    public static boolean isNumber(char a){
        try{
            String string =""+a;
            Integer.parseInt(string);
        }catch (Exception e){
            return false;
        }
        return true;
    }
    public static boolean isOp(String string,int index){
        char character = string.charAt(index);
        if(character=='+'){
            return true;
        }if(character=='*'){
            return true;
        }if(character=='/'){
            return true;
        }if(character=='-'){
            if(index==string.length()-1) {
                return true;
            }if(!isNumber(string.charAt(index+1))){
                return true;
            }
        }
        return false;
    }
}