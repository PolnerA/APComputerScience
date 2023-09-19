/*
Name: Adam Polner
Problem: Write a program to output the squares of the numbers 1 to a given number.
         (Challenge yourself to do it without multiplying or using powers.)
Pseudocode: Ask up to which number they want, use it as the number of loops, 1+3 each time add 2 to 3 and keep adding
            the number
Notes: 1+3 = 4+5 = 9+7 = 16+9 = 25+11 = 36+13 = 49 (number +3, +2 to the 3 every iteration)
Maintenance log:
Date:           Done:
9/19/2023       Finished GS02-01
*/

import java.util.Scanner;

public class GS02_01 {
    public static void main(String[] args) {
        System.out.println("up to what number squared?");
        Scanner sc = new Scanner(System.in);
        int max =sc.nextInt();
        int num=1;
        int add=3;
        for(int i=1; i<=max;i++)
        {
            System.out.print(num+"\t");
            num+=add;
            add+=2;
        }
    }
}
