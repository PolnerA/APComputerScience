package Recursion;

public class GS11_01 {
    static int m=0;
    public static void main(String[] args) {
        starString(3);
        System.out.print("\n"+m);
    }
    public static void starString(int n){
        for(int i=0;i<n;i++){
            System.out.print("*");
            m++;
            starString(--n);
            if(n<0){return;}
        }
    }
    public static void starStringLoop(int n){
        for(int i=0;i<n*n;i++){
            System.out.print("*");
            m++;
        }
    }
}
