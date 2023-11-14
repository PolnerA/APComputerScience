import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class Wordle {
    enum colors {Gray,Yellow,Green}

    public static void main(String[] args) {
        try{
            while(true) {
                Random rng = new Random();
                Scanner sc = new Scanner(System.in);
                File WordleAns = new File("WordleAns.txt");
                File FiveLetterWords = new File("FiveLetterWords.txt");
                Scanner myReader = new Scanner(WordleAns);
                String word = "error";
                for (int i = 0; i <= rng.nextInt(2315); i++) {//2315 is the number of words in the answers
                    word = myReader.nextLine();
                }
                myReader.close();
                colors[] statuschar = new colors[5];
                for (int j = 1; j <= 6; j++) {
                    System.out.println("Guess number " + j + "/6");
                    boolean realword = false;
                    String fiveletters ="";
                    while (fiveletters.length() != 5 || !realword) {
                        Scanner loopsearch = new Scanner(FiveLetterWords);
                        System.out.print("Five letter word: ");
                        fiveletters = sc.nextLine();
                        while (loopsearch.hasNextLine()) {
                            String line = loopsearch.nextLine();
                            if (line.equals(fiveletters)) {
                                realword = true;
                                break;
                            }
                        }
                        loopsearch.close();
                    }
                    boolean[] removed = new boolean[5];
                    boolean[] removed2 = new boolean[5];
                    for(int i=0;i<5;i++) {
                        if (word.charAt(i) == fiveletters.charAt(i)) {
                            statuschar[i]=colors.Green;
                            removed[i]=true;
                            removed2[i]=true;
                        }
                    }
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
                        }
                        statuschar[i]=colors.Gray;
                    }
                    System.out.println();
                    if (statuschar[0] == colors.Green && statuschar[1] == colors.Green && statuschar[2] == colors.Green && statuschar[3] == colors.Green && statuschar[4] == colors.Green) {
                        System.out.println("You took " + j + " guesses");
                        break;
                    }

                }
                System.out.println("The word was " + word);
                System.out.println("Replay?(1.Y|2.No)");
                if(sc.nextInt()==2){
                    break;
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
