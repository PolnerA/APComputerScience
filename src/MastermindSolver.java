import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class MastermindSolver {
    private static final String testFilename = "mastermind_4p6c.txt";
    private static final String firstguess = "0011";
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String> zeroblackpins = new ArrayList<String>();
        ArrayList<String> oneblackpins = new ArrayList<String>();
        ArrayList<String> twoblackpins = new ArrayList<String>();
        ArrayList<String> threeblackpins = new ArrayList<String>();
        ArrayList<String> fourblackpins = new ArrayList<String>();
        FileReader mastermindutputs = new FileReader(testFilename);
        Scanner lineScanner = new Scanner(mastermindutputs);
        while(lineScanner.hasNextLine()){
            String line = lineScanner.nextLine();
            char blackpins = line.charAt(10);//specific to 4 pin (pins*2+2)
            if(blackpins-'0'==0){
                zeroblackpins.add(line);
            }
            else if(blackpins-'0'==1){
                oneblackpins.add(line);
            }
            else if(blackpins-'0'==2){
                twoblackpins.add(line);
            }
            else if(blackpins-'0'==3){
                threeblackpins.add(line);
            }
            else{
                fourblackpins.add(line);
            }
        }
        /*list would help to know what to remove if index is known as opposed to .txt which would have to
          read every line until given number is found. populate index with .txt, make a new one for different
          pins + colors
        */
    }
    public static void Solve(){
        String guess=firstguess;
        int guesses=1;//increments guesses anytime a guess is taken (new string guess)
        String code=MasterMind.GenerateCode();
        int[] blacknwhitepins = MasterMind.EvaluateGuess(guess,code);
        int blackpins = blacknwhitepins[0];
        int whitepins = blacknwhitepins[1];
        while(blackpins!=4){
            //check against file of mastermind_#p#c.txt to find the pins and check against guess
        }
    }
}
