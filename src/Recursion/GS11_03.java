package Recursion;

public class GS11_03 {
    public static void main(String[] args) {
        writeSequence(5);
    }
    public static void writeSequence(int n){
        if(n<1){throw new IllegalArgumentException();}
        if(n%2==1){
            if(Math.round((float) n/2)!=1){
            System.out.print(Math.round((float) n/2));}
        }
        n--;
        if(n<=0){return;}
        writeSequence(n);
        n++;
        if(n%2==0){
            System.out.print(Math.round((float) n/2));
        }

    }
}
