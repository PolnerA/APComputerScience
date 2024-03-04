package Recursion;

public class GS11_05 {
    static int m=0;
    public static void main(String[] args) {
        System.out.println("\n"+fibonacci(7));//done
    }
    public static int fibonacci(int n){
        int[] PreviousNumbers = new int[n];
        fib(n,PreviousNumbers);
        return PreviousNumbers[n-1];
    }
    public static void fib(int n, int[] prevnums){
        if(n==0){
            return;
        }
        fib(--n,prevnums);
        if(2<=n){
            prevnums[n]=prevnums[n-1]+prevnums[n-2];
        }else{
            prevnums[n]=1;
        }
    }
}
