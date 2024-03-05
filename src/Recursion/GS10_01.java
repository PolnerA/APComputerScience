/*
Name: Adam Polner
Problem:Write a recursive method that takes an integer argument n and gives the nth number in hte fibonacci series.
Pseudocode: Recursive method starts at given value n and calculates the previous 2 fib numbers adding them up, until it reaches to the first 2 digits in which case it returns 1
Notes: better way of getting the fib number in GS11-05
Maintenance log:
Date:       Done:
2/22/2024   Started and finished recursive fib
 */
package Recursion;

public class GS10_01 {
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
}
