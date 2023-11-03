import java.util.ArrayList;

public class MastermindSolver {
    public static void main(String[] args) {
        ArrayList<Integer> possibilities = new ArrayList<Integer>();
        /*list would help to know what to remove if index is known as opposed to .txt which would have to
          read every line until given number is found. populate index with .txt, make a new one for different pins
          and colors with the following code:
          int max = colors-1
          for(int i=0;i<pins;i++){
            max *= 10;
            max += colors-1;
          }
          String num="";
          for(int i =0; i<=max;i++){

          }
        */
    }
    public static void Solve(){
        String guess="1122";
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
