package Recursion;

public class GS11_05 {
    static int m=0;
    public static void main(String[] args) {
        System.out.println(fib(5));
    }
    public static void fibonacci(int n){//make more efficient version
        if(n<=2){
            m++;
            //returns 1 the amount of times one occurs in the fibonacci num.
        } else{
          fibonacci(n-1);
          fibonacci(n-2);
        }
    }
    public static int fib(int a){
        m=0;
        fibonacci(a);
        return m;
    }
}
