import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class Wordle {
    public static void main(String[] args) {
        try{
            int ans =1;
            while(ans==1) {
                Random rng = new Random();
                Scanner sc = new Scanner(System.in);
                File WordleAns = new File("/Users/polnera/IdeaProjects/APComputerScience/src/WordleAns.txt");
                File FiveLetterWords = new File("/Users/polnera/IdeaProjects/APComputerScience/src/FiveLetterWords.txt");
                Scanner myReader = new Scanner(WordleAns);
                String word = "error";
                for (int i = 0; i <= rng.nextInt(15919); i++) {//15919
                    word = myReader.nextLine();
                }
                myReader.close();
                char[] chararray = new char[5];
                chararray[0] = word.charAt(0);
                chararray[1] = word.charAt(1);
                chararray[2] = word.charAt(2);
                chararray[3] = word.charAt(3);
                chararray[4] = word.charAt(4);
                int[] statuschar = new int[5];
                for (int j = 1; j <= 6; j++) {
                    System.out.println("Guess number " + j + "/6");
                    System.out.print("Five letter word: ");
                    String fiveletters = sc.nextLine();
                    boolean realword = false;
                    Scanner search = new Scanner(FiveLetterWords);
                    while (search.hasNextLine()) {
                        String line = search.nextLine();
                        if (line.equals(fiveletters)) {
                            realword = true;
                            break;
                        }
                    }
                    search.close();
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
                    //automatically gray (Gray 0 | Yellow 1 | Green 2)
                    for (int i = 0; i < 5; i++) {
                        statuschar[i] = 0;
                    }
                    for (int i = 0; i < 5; i++) {
                        if (fiveletters.charAt(i) == chararray[i]) {
                            statuschar[i] = 2;
                        } else if (fiveletters.charAt(i) == chararray[0] || fiveletters.charAt(i) == chararray[1] || fiveletters.charAt(i) == chararray[2] || fiveletters.charAt(i) == chararray[3] || fiveletters.charAt(i) == chararray[4]) {
                            statuschar[i] = 1;
                        }
                    }
                    for (int i = 0; i < 5; i++) {
                        if (statuschar[i] == 0) {
                            System.out.print(Colors.RED + fiveletters.charAt(i) + Colors.RESET);
                        } else if (statuschar[i] == 1) {
                            System.out.print(Colors.YELLOW_BRIGHT + fiveletters.charAt(i) + Colors.RESET);
                        } else {
                            System.out.print(Colors.GREEN_BOLD_BRIGHT + fiveletters.charAt(i) + Colors.RESET);
                        }
                    }
                    System.out.println();
                    if (statuschar[0] == 2 && statuschar[1] == 2 && statuschar[2] == 2 && statuschar[3] == 2 && statuschar[4] == 2) {
                        System.out.println("You took " + j + " guesses");
                        break;
                    }

                }
                System.out.println("The word was " + word);
                System.out.println("Replay?(1.Y|2.No)");
                ans = sc.nextInt();
            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
