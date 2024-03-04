package Recursion;

public class GS10_01 {
    public static void main(String[] args) {
        System.out.print(fibonacci(3));//done
    }

    public static int fibonacci(int n){//make more efficient version
        if(n<=2){
            return 1;
            //returns 1 the amount of times one occurs in the fibonacci num.
        } else{
            return fibonacci(n-1)+fibonacci(n-2);//bad way of getting the nth number, improved in GS11-05
        }
    }
}
