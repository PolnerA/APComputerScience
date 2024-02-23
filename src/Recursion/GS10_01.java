package Recursion;

public class GS10_01 {
    public static void main(String[] args) {
        System.out.print(fibonacci(3));
    }
    public static int fibonacci(int n){
        if(n<=0){return 0;}
        return fibonacci(n)+fibonacci(n-1);
    }
}
