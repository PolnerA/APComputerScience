package Recursion;

public class GS11_03 {
    public static void main(String[] args) {
        writeSequenceloop(2);
    }
    public static void writeSequence(int n){
        if(n<1){throw new IllegalArgumentException();}
        System.out.print(Math.round((float) n/2));
        n--;
        if(n<=0){return;}
        writeSequence(n);

    }
    public static void writeSequenceloop(int n){
        for(int i=n;0<i;i--){
            System.out.print(i);
        }
        for(int i=2;i<n;i++){
            System.out.print(i);
        }
    }
}
