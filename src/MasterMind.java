import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MasterMind {
    public static final int pins=4;
    public static final int colors=6;//max 10 as the chars wouldn't work 0-9 is the amount in one place
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
        Scanner sc = new Scanner(System.in);
        String stringnum=GenerateCode();
        String stringinputnum;
        System.out.println(Math.abs(0x80000000));
        do {
            do {
                System.out.println("\n4 digit number:");
                stringinputnum = sc.nextLine();
            } while (stringinputnum.length() != 4);

            int[] blackwhitepins = EvaluateGuess("1121",stringnum);
            int blackpins = blackwhitepins[0];
            int whitepins = blackwhitepins[1];
            System.out.println("Correct space and number: " + blackpins);
            System.out.println("Incorrect space, Correct number: " + whitepins);

        } while (stringinputnum.charAt(0) != stringnum.charAt(0) || stringinputnum.charAt(1) != stringnum.charAt(1) || stringinputnum.charAt(2) != stringnum.charAt(2) || stringinputnum.charAt(3) != stringnum.charAt(3));
    }
    public static String GenerateCode(){
        Random rng = new Random();
        String code = "";
        for(int i=0;i<pins;i++){
            code+=rng.nextInt(colors);
        }
        return code;
    }
    public static int[] EvaluateGuess(String codeword1, String codeword2){
        int blackpins=0;
        int whitepins=0;
        boolean[] removed = new boolean[pins];
        boolean[] removed2 = new boolean[pins];
        for(int i=0;i<pins;i++) {
            if (codeword1.charAt(i) == codeword2.charAt(i)) {
                blackpins++;
                removed[i]=true;
                removed2[i]=true;
            }
        }
        if(blackpins==pins){
            return new int[] {blackpins,whitepins};//returns 4,0
        }//1112 1121
        for(int i=0;i<pins;i++){
                for (int j = 0; j < pins; j++) {
                    if (!removed2[j] && !removed[i]) {
                        if (codeword1.charAt(i) == codeword2.charAt(j)) {
                            removed[i]=true;
                            removed2[j]=true;
                            whitepins++;
                        }
                    }
                }
        }
        return new int[] {blackpins,whitepins};
    }
    public static int CalculateBlackPins(String codeword1,String codeword2){
        int blackpins=0;
        for(int i=0;i<pins;i++){
            if(codeword1.charAt(i)==codeword2.charAt(i)){
                blackpins++;
            }
        }
        return blackpins;
    }
}
