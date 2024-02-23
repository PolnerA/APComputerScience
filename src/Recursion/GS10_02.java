package Recursion;

public class GS10_02 {
    public static void main(String[] args) {
        writeSquares(8);//done
    }
    public static void writeSquares(int n){
        if(n%2==1){System.out.print(n*n+",");}
        n--;
        if(n<=0){return;}
        writeSquares(n);
        n++;
        if(n%2==0){
            System.out.print(n*n+" ");
        }
    }
}
