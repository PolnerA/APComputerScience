package Recursion;

public class GS11_01 {
    static int m=0;
    public static void main(String[] args) {
        starString(2);
        System.out.print("\n"+m);
    }
    public static void starString(int n){
        m++;
        System.out.print("*");
        if(n<=0){
            return;
        }
        n--;
        starString(n);
        if(n<=0){return;}
        starString(n);
    }
    public static void starStringLoop(int n){
        for(int i=0;i<Math.pow(2,n);i++){
            System.out.print("*");
            m++;
        }
    }
}
