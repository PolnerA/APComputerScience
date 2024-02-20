package Recursion;

public class GS11_04 {
    public static void main(String[] args) {//done
        System.out.print(sumTo(4));
    }
    public static double sumTo(int n){
        if(n<=0){return 0;}
        double sum=1/(double)n;
        sum+=sumTo(--n);
        return sum;
    }
}
