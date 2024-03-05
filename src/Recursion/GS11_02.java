/*
Name: Adam Polner
Problem: write a recursive method called writeNums that takes an integer n as a parameter and prints to the console the
first n integers starting with 1 in sequential order, separated by commas
Pseudocode: if n is 0 it returns, as it starts with n it goes down to 0 causing the things after the recursive call to
happen from the bottom up, the n++ after causes the numbers to go from 1 to n recursively
Notes:
Maintenance log:
Date:       Done:
2/20/2024   started and finished writeNums
 */
package Recursion;
public class GS11_02 {
    public static void main(String[] args) {
        writeNums(5);
    }
    public static void writeNums(int n){
        if(n==0){return;}
        n--;
        writeNums(n);
        n++;
        if(n==1){System.out.print(n);}
        else {
            System.out.print("," + n);
        }
    }
}
