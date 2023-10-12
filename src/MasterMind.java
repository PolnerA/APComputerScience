import java.util.Random;
import java.util.Scanner;

public class MasterMind {
    public static void main(String[] args) {
/*
Write a program that plays a variation of the game Mastermind with a user.
The program should generate a four-digit number. User allowed to guess until she gets the number correct.
Clues are given to the user indicating how many digits are correct and in the correct place. (BJP Ch 7 Proj 3)

You should output two things after the user guesses. The number of digits that are correct AND in the correct location.
The number of digits that are correct BUT not in the correct location. These are mutually exclusive.
A digit cannot be both in the correct location and not in the correct location.

Below is a test case to test your code.
Make sure that your "Secret" is the given number (6684)
and then use the guesses to check your outputs to see that they match up with the test.

By the way, traditional Mastermind had only 4 pins which could have 6 different colors
(we are using 4 pins with 10 colors/digits).
 One player put in the secret and the other player guessed based on feedback.
 Feedback was given in terms of black and white pegs. A black peg signified that the guesser had gotten a pin of the correct color in the correct position. A white peg signified that the guesser had gotten a pin of the correct color in the wrong location.
 */
        Random rng = new Random();
        Scanner sc = new Scanner(System.in);
        //int num0 = rng.nextInt(10);
        //int num1 = rng.nextInt(10);
        //int num2 = rng.nextInt(10);
        //int num3 = rng.nextInt(10);
        int num0=6;
        int num1=6;
        int num2=8;
        int num3=4;
        int[] num = {num0,num1,num2,num3};
        for (int j : num) {
            System.out.print(j);
        }
        int intinputnum;
        int numinputs;
        do {
            System.out.println("\n4 digit number:");
            numinputs=0;
            intinputnum = sc.nextInt();
            int n = intinputnum;
            while (n != 0) {
                n /= 10;
                numinputs++;
            }
        }while (numinputs!=4);
        int inputnum0 = intinputnum/1000;
        int inputnum1 = intinputnum/100 - inputnum0*10;
        int inputnum2 = intinputnum/10 - inputnum1*10 - inputnum0*100;
        int inputnum3 = intinputnum-inputnum0*1000-inputnum1*100-inputnum2*10;
        int[] inputnum = {inputnum0,inputnum1,inputnum2,inputnum3};
        int blackpins =0;
        int whitepins =0;
        for (int i =0;i<inputnum.length;i++){
            if(inputnum[i]==num[i]){
                blackpins++;
            }
            else if(inputnum[i]==num0||inputnum[i]==num1||inputnum[i]==num2||inputnum[i]==num3){
                whitepins++;
            }
        }
        System.out.println("Correct space and number: "+blackpins);
        System.out.println("Incorrect space, Correct number: "+whitepins);
    }
}
