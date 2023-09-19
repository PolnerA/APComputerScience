import java.util.Scanner;

/*
Name:Adam Polner
Problem:Write a program to output a given number of the Fibonacci series. (Use nested loops and not recursion.)
        You can have a method to find a given number in the series and a method that calls that method.
Pseudocode: start with a number and a past number to a loop to the found number the loop gets the past number, writes
            the current number saves the number (but only writes it when it's reached the given number of loops) rotates
            the past number to the current number and the current number to the past and the current number;
Notes: ints overflow so use longs
Maintenance log:
Date:       Done:
9/19/2023   Finished GS02-02
 */
public class GS02_02 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long num =1;
        long pastnum =0;
        System.out.println("which number of the Fibonacci series do you want");
        int givennum = sc.nextInt();
        long foundnum=0;
        for(long i=num;i<=givennum;i++) {
            long pastpastnum =pastnum;
            System.out.print(num+"\t");
            foundnum=num;
            pastnum=num;
            num=pastpastnum+num;
        }
        System.out.print("\n"+"Number: "+foundnum);
    }
}
