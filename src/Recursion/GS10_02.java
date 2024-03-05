/*
Name: Adam Polner
Problem:Write a recursive method called writeSquares that accepts an integer parameter n
and prints the first n squares separated by commas, with the odd squares in descending order
followed by the even squares in ascending order.
Pseudocode: if the number in the function is odd, print the square, decrease n and recall the function,
returning when n equals 0 as it goes back up through the recursive calls from 1,
it goes back up to its true value (n++) and check if n is even, if it is it prints the square
Notes:
Maintenance log:
Date:       Done:
2/22/2024   Started writeSquares
2/23/2024   Finished writeSquares
 */
package Recursion;

public class GS10_02 {
    public static void main(String[] args) {
        writeSquares(8);
    }
    public static void writeSquares(int n){
        if(n%2==1){System.out.print(n*n+" ");}
        n--;
        if(n<=0){return;}
        writeSquares(n);
        n++;
        if(n%2==0){
            System.out.print(n*n+" ");
        }
    }
}
