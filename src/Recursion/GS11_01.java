package Recursion;

public class GS11_01 {
    public static void main(String[] args) {
        starString(0);//done
    }
    public static void starString(int n){
        if(n==0){
            System.out.print("*");
        }else{
            n--;
            starString(n);
            starString(n);
        }

    }
}
