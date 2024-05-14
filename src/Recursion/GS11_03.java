/*
Name: Adam Polner
Problem: Write a recursive method that called writeSequence that accepts an integer n as a parameter and prints to the
console a sequence of n numbers composed of descending integers that ends in 1, followed by a sequence of ascending
integers that begins with 1. When n is odd, the sequence has a single 1 in the middle, but when n is even, it has
two 1s in the middle. throw an IllegalArgumentException if it is passed a value less than 1
Pseudocode:gets given n if it is less than one throws the exception. If it is 1 it prints it out, otherwise it adds one
to n and divides it by two, to get the correct number. prints it. If there won't be an exception it decrements n by 2
and calls the function again, and prints out n+1/2 on the way back up from all the calls as well.
Notes:
Maintenance log:
Date:       Done:
2/23/24     Worked on WriteSequence
3/3/2024    Finished WriteSequence

 */
package Recursion;

public class GS11_03 {
    public static void main(String[] args) {
        for(int i=1;i<=10;i++){
            System.out.println();
            writeSequence(i);
        }
    }
    public static void writeSequence(int n){
        if(n<1){throw new IllegalArgumentException();}
        else if(n==1){
            System.out.print("1 ");
        }else{
            System.out.print((n+1)/2+" ");
            if(1<=n-2){
                writeSequence(n-2);
            }
            System.out.print((n+1)/2+" ");
        }

    }
}
