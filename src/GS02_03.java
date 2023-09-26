/*
Name: Adam Polner
Problem: Write a program that produces the ASCII stair output (see book figure).
Use a class constant to make it possible to change the number of stairs in the figure.
PseudoCode: See DrawStair Function
Notes:
Maintenance log:
Date:       Done:
9/21/2023   Started GS02-03
9/25/2023   Wrote what the program needs to do depending on number of steps
9/26/2023   Finished GS02-03

 */
public class GS02_03 {
    public static void main(String[] args) {
        DrawStair(100);
    }
    static void DrawStair(int steps) {
        //draw set amount of spaces +2 then an o then 2 spaces followed by 6 * then a * at the end depending on height
        //after same set amount of spaces +1 space then a /|\ (\\) then a space and an *, amount of spaces vary, with an * to end it off
        //same set amount of spaces +1 space then a / with a space then \ (\\) then a space followed by * amount of spaces vary *
        //DrawTopStair(steps);
        int secondspace =-1;
        for(int i=steps;1<=i;i--){
            int spaces=(i-1)*5;
            for(int j=0;j<=spaces;j++){
                System.out.print(" ");
            }
            System.out.print("  o  ******");
            for(int j=0;j<=secondspace;j++){
                System.out.print(" ");
            }
            secondspace+=5;
            System.out.print("*\n");
            for(int j=0;j<=spaces;j++){
                System.out.print(" ");
            }
            System.out.print(" /|\\ *");
            for(int j=0;j<=secondspace;j++){
                System.out.print(" ");
            }
            System.out.print("*\n");
            for(int j=0;j<=spaces;j++){
                System.out.print(" ");
            }
            System.out.print(" / \\ *");
            for(int j=0;j<=secondspace;j++){
                System.out.print(" ");
            }
            System.out.print("*\n");
        }
        DrawFloor(steps);
    }
    /*
    static void DrawTopStair(int steps) {
        for (int i = 0; i <= 2 + ((steps-1) * 5); i++) {
        System.out.print(" ");
        }
        System.out.print("o");
        System.out.print("  ");
        System.out.print("*******\n");
        for (int i = 0; i <= 1 + ((steps-1) * 5); i++) {
            System.out.print(" ");
        }
        System.out.print("/|\\ *");

    }*/
    static void DrawFloor(int steps){
        for(int i=0;i<=(steps*5)+7;i++){
            System.out.print("*");
        }
    }
}
//
//
//
//                      o  *******
//                     /|\ *     *
//                     / \ *     *
//                 o  ******     *
//                /|\ *          *
//                / \ *          *
//            o  ******          *
//           /|\ *               *
//           / \ *               *
//       o  ******               *
//      /|\ *                    *
//      / \ *                    *
//  o  ******                    *
// /|\ *                         *
// / \ *                         *
//********************************
