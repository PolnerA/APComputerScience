import java.util.Random;

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
 One player put in the secret and the other player guessed based on feedback. Feedback was given in terms of black and white pegs. A black peg signified that the guesser had gotten a pin of the correct color in the correct position. A white peg signified that the guesser had gotten a pin of the correct color in the wrong location.
 */
        Random rng = new Random();
        int num0 = rng.nextInt(10);
        int num1 = rng.nextInt(10);
        int num2 = rng.nextInt(10);
        int num3 = rng.nextInt(10);
        int[] num = {num0,num1,num2,num3};
        for (int j : num) {
            System.out.print(j);
        }
    }
}
