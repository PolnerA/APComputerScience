package Recursion;

public class GS11_01 {
    static int m=0;
    public static void main(String[] args) {
        starString(2);
        System.out.print("\n"+m);
    }
    public static int starString(int n){
        if(n==0){
            return 1;
        }else{
            return (int) (2* Math.pow(2,n-1));
        }

    }
    public static void starStringLoop(int n){
        for(int i=0;i<Math.pow(2,n);i++){
            System.out.print("*");
            m++;
        }
    }
}
