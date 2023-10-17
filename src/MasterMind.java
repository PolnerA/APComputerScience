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
        int[] num = new int[]{rng.nextInt(10), rng.nextInt(10), rng.nextInt(10), rng.nextInt(10)};
        String stringnum = "";
        for (int i = 0; i < num.length; i++) {
            stringnum = stringnum + num[i];
            System.out.println(stringnum);
        }
        String stringinputnum;
        do {
            do {
                System.out.println("\n4 digit number:");
                stringinputnum = sc.nextLine();
            } while (stringinputnum.length() != 4);
            int blackpins = 0;
            int whitepins = 0;

            System.out.println("Correct space and number: " + blackpins);
            System.out.println("Incorrect space, Correct number: " + whitepins);

        } while (stringinputnum.charAt(0) != stringnum.charAt(0) || stringinputnum.charAt(1) != stringnum.charAt(1) || stringinputnum.charAt(2) != stringnum.charAt(2) || stringinputnum.charAt(3) != stringnum.charAt(3));
    }
    public static int[] EvaluateGuess(String guess, String num){
        int[] pins = new int[]{0, 0};
        for (int i = 0; i < guess.length(); i++) {
            if (guess.charAt(i) == num.charAt(i)) {
                pins[0]++;
            } else if (guess.charAt(i) == num.charAt(0) || guess.charAt(i) == num.charAt(1) || guess.charAt(i) == num.charAt(2) || guess.charAt(i) == num.charAt(3)) {
                pins[1]++;
            }
        }
        return pins;
    }
}
