import java.util.Scanner;

public class Wordle {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String word = "words";
        char[] chararray = new char[5];
        chararray[0]=word.charAt(0);
        chararray[1]=word.charAt(1);
        chararray[2]=word.charAt(2);
        chararray[3]=word.charAt(3);
        chararray[4]=word.charAt(4);
        System.out.print("Five letter word: ");
        String fiveletters = sc.nextLine();
        while(fiveletters.length()!=5){
            System.out.print("Five letter word: ");
            fiveletters = sc.nextLine();
        }
        int[] statuschar = new int[5];
        //automatically gray (Gray 0 | Yellow 1 | Green 2)
        for(int i=0;i<5;i++){
            statuschar[i] = 0;
        }
        for(int i=0;i<5;i++){
            if(fiveletters.charAt(i)==chararray[i]){
                statuschar[i] = 2;
            }
            else if(fiveletters.charAt(i)==chararray[0]||fiveletters.charAt(i)==chararray[1]||fiveletters.charAt(i)==chararray[2]||fiveletters.charAt(i)==chararray[3]||fiveletters.charAt(i)==chararray[4]){
                statuschar[i]=1;
            }
        }
        for(int i=0;i<5;i++){
            System.out.println(statuschar[i]);
        }

    }
}
