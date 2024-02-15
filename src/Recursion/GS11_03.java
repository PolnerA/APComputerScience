package Recursion;

public class GS11_03 {
    public static void main(String[] args) {
        writeSequence(5);
    }
    public static void writeSequence(int n){//correct numbers, wrong sequence
        System.out.print(Math.round((float) n/2));
        n--;
        if(n<=0){return;}
        writeSequence(n);
    }
}
