/*
Name: Adam Polner
Problem: write a recursive method called starString that accepts an integer n
as a parameter and prints to the console a string of stars that is 2^n long
Pseudocode:Gets the n stopping if it is zero and printing a star, if it is larger than 0 it decreases n
and calls star string twice, this is because of the base of 2 and in the end it will get us 2^n star strings
Notes:
Maintenance log:
Date:       Done:
3/3/2024    started and finished star string
 */
package Recursion;

public class GS11_01 {
    public static void main(String[] args) {
        starString(0);
        System.out.println();
        starString(1);
        System.out.println();
        starString(2);
        System.out.println();
        starString(4);
    }
    public static void starString(int n){
        if(n==0){
            System.out.print("*");
        }else{
            n--;
            starString(n);
            starString(n);
        }

    }
}
