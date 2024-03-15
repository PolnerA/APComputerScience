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
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String Equation = sc.nextLine();
        Stack<Integer> stack = new Stack<>();
        boolean PrevIsInt = false;
        int PrevInt=0;
        int Int=0;
        boolean IsInt = false;
        int j=0; //level of digit
        boolean intisDone=false;
        boolean intisStarted=false;
        for (int i = 0; i < Equation.length(); i++) {
            char a = Equation.charAt(i);
            if(isNumber(Equation,i)){
                IsInt=true;
                intisStarted=true;
                Int = Integer.parseInt(""+a);//don't parse int
            }else{
                if (intisStarted) {
                    stack.push(PrevInt);
                    intisDone = true;
                }
                IsInt = false;
                intisStarted=false;
                Int = 0;
                PrevInt = 0;
                j = 0;
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
                Operation op= GiveOp(Equation,i);
                if(op.id!=0){//if it is an operation
                    int i1 = stack.pop();
                    int i2 = stack.pop();
                    int result = op.PerformOperation(i1,i2);
                    stack.push(result);
                    System.out.println(stack);
                }
            }
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
    public static Operation GiveOp(String string,int index){
        char character = string.charAt(index);
        if(character=='+'){
            Operation op = new Operation('+');
            return op;

        }if(character=='*'){
            Operation op = new Operation('*');

            return op;
        }if(character=='/'){
            Operation op = new Operation('/');
            return op;
        }if(character=='-'){
            if(index==string.length()-1) {
                Operation op = new Operation('-');
                return op;
            }if(!isNumber(string,index+1)){
                Operation op = new Operation('-');
                return op;
            }
        }
        return new Operation(0);
    }
}