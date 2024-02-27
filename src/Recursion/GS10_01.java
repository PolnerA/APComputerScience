package Recursion;

public class GS10_01 {
    public static void main(String[] args) {
        int n=4;
        System.out.println(fibonacci(n));
        int[] seq=new int[n];
        int m=0;
        for(int i=0;i<n;i++){
            if(i<2){
                seq[i]=1;
                m=1;
            }else{
                seq[i]=seq[i-1]+seq[i-2];
                m+=m;
            }
        }
        System.out.println(seq[n-1]+"\n"+m);
    }

    public static int fibonacci(int n){//make more efficient version
        if(n<=2){
            return 1;
            //returns 1 the amount of times one occurs in the fibonacci num.
        } else{
            return fibonacci(n-1)+fibonacci(n-2);
        }
    }
}
