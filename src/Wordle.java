import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
public class Wordle {
    public static void main(String[] args) {
        try{
            Random rng = new Random();
            Scanner sc = new Scanner(System.in);
            File myObj = new File("/Users/polnera/IdeaProjects/APComputerScience/src/FiveLetterWords.txt");
            Scanner myReader = new Scanner(myObj);
            String word = "error";
            for(int i=0;i<= rng.nextInt(15919);i++) {//15919
                word = myReader.nextLine();
            }
            myReader.close();
            char[] chararray = new char[5];
            chararray[0]=word.charAt(0);
            chararray[1]=word.charAt(1);
            chararray[2]=word.charAt(2);
            chararray[3]=word.charAt(3);
            chararray[4]=word.charAt(4);
            for (int j=1;j<=6;j++) {
                System.out.print("Five letter word: ");
                String fiveletters = sc.nextLine();
                boolean realword = false;
                Scanner search = new Scanner(myObj);
                while (search.hasNextLine()) {
                    String line = search.nextLine();
                    if (line.equals(fiveletters)) {
                        realword = true;
                        System.out.println("It matches");
                        break;
                    }
                }
                search.close();
                Scanner loopsearch = new Scanner(myObj);
                while (fiveletters.length() != 5 || !realword) {
                    System.out.print("Five letter word: ");
                    fiveletters = sc.nextLine();
                    while (loopsearch.hasNextLine()) {
                        String line = loopsearch.nextLine();
                        if (line.equals(fiveletters)) {
                            realword = true;
                            System.out.println("It matches");
                            break;
                        }
                    }
                }
                int[] statuschar = new int[5];
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
                    System.out.println(statuschar[i]);
                }
                if(statuschar[0]==2&&statuschar[1]==2&&statuschar[2]==2&&statuschar[3]==2&&statuschar[4]==2){
                    System.out.println("The word was "+word);
                    System.out.println("You took "+j+" guesses");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
