/*
Name: Adam Polner
Problem: Make a more efficient method than the one in the book, but with the same header by using a helper method
Pseudocode:Calls the fibonacci sequence at n creating a list for all the numbers (for easier future reference)
it uses recursion to calculate the previous numbers, making the first 2 one and the subsequent ones a sum of the
previous two. after the recursive function calculates it, it returns the end of the sequence with the required number
of the sequence
Notes:
Maintenance log:
Date:       Done:
3/3/2024    started and finished more efficient fibonacci sequence
 */
package Recursion;

public class GS11_05 {
    public static void main(String[] args) {
        for(int i=1;i<=92;i++){
            System.out.println("\n"+fibonacci(i));
        }
    }
    public static long fibonacci(int n){
        long[] PreviousNumbers = new long[n];
        fib(n,PreviousNumbers);
        return PreviousNumbers[n-1];
    }
    public static void fib(int n, long[] prevnums){
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
