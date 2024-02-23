package Recursion;

public class GS11_05 {
    public static void main(String[] args) {
        System.out.print(fibonacci(3));
    }
    public static int fibonacci(int n){
        if(n<=2){
            return 1;
        } else{
          return fibonacci(n-1)+fibonacci(n-2);
        }
    }
    public static int fib(int a,int b){//stores previous numbers

        return a;
    }
}
