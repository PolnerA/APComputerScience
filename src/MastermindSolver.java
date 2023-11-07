import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MastermindSolver {
    private static final String Filename = "mastermind_4p6c.txt";
    private static final String firstguess = "0011";
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String> Possibilities = new ArrayList<String>();
        FileReader mastermindutputs = new FileReader(Filename);
        Scanner lineScanner = new Scanner(mastermindutputs);
        int[] ints = new int[0];
        while(lineScanner.hasNextLine()){
            String line = lineScanner.nextLine();
            String[] data = line.split(",");

        }
        /*list would help to know what to remove if index is known as opposed to .txt which would have to
          read every line until given number is found. populate index with .txt, make a new one for different
          pins + colors
        */
        // best guess is the one that removes the most possibilities
        // guess takes pins and removes possibilities.
    }
    public static void Solve(){
        String guess=firstguess;
        int guesses=1;//increments guesses anytime a guess is taken (new string guess)
        String code=MasterMind.GenerateCode();
        int[] pins = MasterMind.EvaluateGuess(guess,code);
        int blackpins = pins[0];
        int whitepins = pins[1];
        while(blackpins!=4){
            //check against file of mastermind_#p#c.txt to find the pins and check against guess
        }
    }
}
