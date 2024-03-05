/*
Name: Adam Polner
Problem: Write a recursive method called sumTo that accepts an integer parameter n and returns a real number
representing the sum of the first n reciprocals, sumTo(n) returns (1+1/2+1/3...+1/n)
Pseudocode:if n is 0 it returns 0, throws illegal argument exception if it is less than 0. it is given the value of 1/n
and adds 1/n-1 to it all the way until it returns 0 going all the way back up the calls until 1/n +1/n-1 is for all the
numbers giving the final returned value
Notes:
Maintenance log:
Date:       Done:
2/20/2024   Started and finished sumTo
 */
package Recursion;

public class GS11_04 {
    public static void main(String[] args) {
        System.out.print(sumTo(4));
    }
    public static double sumTo(int n){
        if(n<0){throw new IllegalArgumentException();}
        if(n==0){return 0;}
        double sum=1/(double)n;
        sum+=sumTo(--n);
        return sum;
    }
}
