import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Wordle {
    enum colors {Gray,Yellow,Green}
    //colors enum for the different wordle colors, added to improve code readability
    public static void main(String[] args) {
        try{
            //on start up adds all 26 characters in the alphabet to all the useable characters for the answer
            ArrayList<Character> useablechars = new ArrayList<>(26);
            for(int i=0;i<26;i++){
                useablechars.add((char)(i+'a'));
            }
            //game loop
            while(true) {
                //Sets up the rng, and a scanner for user input
                //adds 2 files one for the useable words another for the answers
                Random rng = new Random();
                Scanner sc = new Scanner(System.in);
                File WordleAns = new File("WordleAns.txt");
                File FiveLetterWords = new File("FiveLetterWords.txt");
                Scanner myReader = new Scanner(WordleAns);
                //initial word to guess is set to error -> gets overwritten
                String word = "error";

                //goes linearly through the file until it hits a random line 0-number of lines
                for (int i = 0; i <= rng.nextInt(2315); i++) {//2315 is the number of lines in the answers
                    word = myReader.nextLine();
                }
                myReader.close();
                //starts an array with 5 colors for the color of each char
                colors[] statuschar = new colors[5];

                for (int j = 1; j <= 6; j++) {
                     //loop for the 6 guesses starting at 1 because it gets written out
                    System.out.println("Guess number " + j + "/6");
                    boolean realword = false;
                    String fiveletters ="";
                    //guess is set to an empty string which isn't considered a real word
                    while (fiveletters.length() != 5 || !realword) {
                        //sets up a scanner in the acceptable input file
                        //sets the input to a lowercase version, and compares to the file
                        Scanner loopsearch = new Scanner(FiveLetterWords);
                        System.out.print("Five letter word: ");
                        fiveletters = sc.nextLine().toLowerCase();
                        while (loopsearch.hasNextLine()) {
                            String line = loopsearch.nextLine();
                            if (line.equals(fiveletters)) {
                                //as soon as it finds a real word which fits it exits
                                realword = true;
                                break;
                            }
                        }
                        loopsearch.close();
                    }
                    //two boolean arrays for which letter has been currently compared, soon as it is it's removed
                    boolean[] removed = new boolean[5];
                    boolean[] removed2 = new boolean[5];
                    //for each of the five letters checks if any match -> sets to green, originally sets gray
                    for(int i=0;i<5;i++) {
                        statuschar[i]=colors.Gray;
                        if (word.charAt(i) == fiveletters.charAt(i)) {
                            statuschar[i]=colors.Green;
                            removed[i]=true;
                            removed2[i]=true;
                        }
                    }
                    //goes through both with a 
                    for(int i=0;i<5;i++){
                        for (int k = 0; k < 5; k++) {
                            if (!removed2[k] && !removed[i]) {
                                if (word.charAt(i) == fiveletters.charAt(k)) {
                                    removed[i]=true;
                                    removed2[k]=true;
                                    statuschar[k]=colors.Yellow;
                                }
                            }
                        }
                    }//to do add eliminated letters
                    for (int i = 0; i < 5; i++) {
                        if (statuschar[i] == colors.Green) {
                            System.out.print(Colors.GREEN_BOLD_BRIGHT + fiveletters.charAt(i) + Colors.RESET);
                        } else if (statuschar[i] == colors.Yellow) {
                            System.out.print(Colors.YELLOW_BRIGHT + fiveletters.charAt(i) + Colors.RESET);
                        } else {
                            System.out.print(Colors.RED + fiveletters.charAt(i) + Colors.RESET);
                            if(useablechars.contains(fiveletters.charAt(i))){
                                useablechars.remove((Character) fiveletters.charAt(i));
                            }
                        }
                    }
                    System.out.println();
                    if (statuschar[0] == colors.Green && statuschar[1] == colors.Green && statuschar[2] == colors.Green && statuschar[3] == colors.Green && statuschar[4] == colors.Green) {
                        System.out.println("You took " + j + " guesses");
                        break;
                    }
                    System.out.println("Not eliminated characters: "+useablechars);
                }
                System.out.println("The word was " + word);
                System.out.println("Replay?(1.Y|2.No)");
                if(sc.nextInt() ==2){
                    break;
                }
                //reset possible chars after replay here
                useablechars = new ArrayList<>(26);
                for(int i=0;i<26;i++){
                    useablechars.add((char)(i+'a'));
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
