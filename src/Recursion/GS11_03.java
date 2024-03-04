package Recursion;

public class GS11_03 {
    public static void main(String[] args) {
        writeSequence(6);//done
    }
    public static void writeSequence(int n){
        if(n<1){throw new IllegalArgumentException();}
        else if(n==1){
            System.out.print("1 ");
        }else{
            System.out.print((n+1)/2+" ");
            if(1<=n-2){
                writeSequence(n-2);
            }
            System.out.print((n+1)/2+" ");
        }

    }
}
