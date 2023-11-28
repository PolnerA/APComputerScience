import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
/*
Name: Adam Polner
Problem:Create a game which lets the user play a game of wordle
Pseudocode: 2 files (one for answers one for guesses), then it picks a random line from the answers file,
after this it asks the user for a guess, it keeps asking until the guess matches what is in the guesses file it compares
the guess to the saved answer and sees how many are in the correct spot, wrong place, not in the word and right spot and
place
it prints it out and exits the loop if the guesses is correct otherwise starts a new guess. (out of 6 guesses)
Notes: Same evaluation of guess as mastermind
*/
public class Wordle {
    enum colors {Gray,Yellow,Green}
    //colors enum for the different wordle colors to improve code readability
    public static void main(String[] args) {
        try{
            //on start up adds all 26 characters in the alphabet to all the usable characters for the answer
            ArrayList<Character> UsableCharacters = new ArrayList<>(26);
            ResetLetters(UsableCharacters);
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
                    while (!realword) {
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
                    //for each of the five letters checks if any match -> sets to green, originally sets to gray
                    for(int i=0;i<5;i++) {
                        statuschar[i]=colors.Gray;
                        if (word.charAt(i) == fiveletters.charAt(i)) {
                            statuschar[i]=colors.Green;
                            removed[i]=true;
                            removed2[i]=true;
                        }
                    }
                    //goes through both lists and checks each index excluding ones that are the same,
                    //as they are removed
                    for(int i=0;i<5;i++){
                        for (int k = 0; k < 5; k++) {
                            //if both the words are not removed
                            if (!removed2[k] && !removed[i]) {
                                //makes comparison
                                if (word.charAt(i) == fiveletters.charAt(k)) {
                                    removed[i] = true;
                                    removed2[k] = true;
                                    //removes both of them and changes the status to yellow
                                    //(user input value is checked at k)
                                    statuschar[k] = colors.Yellow;
                                }
                            }
                        }
                    }
                    //gray by default, green if same place and char, yellow if different space and same char
                    for (int i = 0; i < 5; i++) {//for each one of the characters checks the status nad outputs the char
                        if (statuschar[i] == colors.Green) {
                            System.out.print(Colors.GREEN_BOLD_BRIGHT + fiveletters.charAt(i) + Colors.RESET);
                        } else if (statuschar[i] == colors.Yellow) {
                            System.out.print(Colors.YELLOW_BRIGHT + fiveletters.charAt(i) + Colors.RESET);
                        } else {//since default color in terminal is gray red is used, instead
                            System.out.print(Colors.RED + fiveletters.charAt(i) + Colors.RESET);
                            if(UsableCharacters.contains(fiveletters.charAt(i))){
                                //checks if the grayed out characters are int the UsableCharacters, if so removes it.
                                UsableCharacters.remove((Character) fiveletters.charAt(i));
                            }
                        }
                    }
                    //goes to new line as soon as the characters are done.
                    System.out.println();
                    if (statuschar[0] == colors.Green && statuschar[1] == colors.Green && statuschar[2] == colors.Green && statuschar[3] == colors.Green && statuschar[4] == colors.Green) {
                        System.out.println("You took " + j + " guesses");
                        //if all the characters are green it breaks out of the 6 guess loop early
                        break;
                    }
                    System.out.println("Not eliminated characters: "+ UsableCharacters);
                    //prints out the Character array of all the usable characters
                }
                System.out.println("The word was " + word);
                System.out.println("Replay?(1.Y|2.No)");
                if(sc.nextInt() ==2){
                    break;
                    //breaks out of the while(true) ending the infinite game loop
                }
                //reset possible chars after replay
                    ResetLetters(UsableCharacters);
            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static void ResetLetters(ArrayList<Character> list){
        list.removeAll(list);//resets the list and later populates with a-z
        for(int i=0;i<26;i++){
            list.add((char)('a'+i));
            //'a'+0 is a, 'a'+1 is b, and so on all the way to z
        }
    }
}
