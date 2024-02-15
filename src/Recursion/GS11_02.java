package Recursion;

public class GS11_02 {
    public static void main(String[] args) {
        writeNums(10);
    }
    public static void writeNums(int n){
        if(n==0){return;}
        n--;
        writeNums(n);
        n++;
        if(n==1){System.out.print(n);}
        else {
            System.out.print("," + n);
        }
    }
}
