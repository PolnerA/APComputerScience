import java.util.Random;
import java.util.Scanner;

public class RockPaperScissors
{
    public static void main(String[] args) {
        boolean rerun = true;
        Random rng = new Random();
        Scanner sc = new Scanner(System.in);
        int wins =0;
        int losses=0;
        int draws =0;
        while(rerun) {
            rerun =false;
            int choice = rng.nextInt(3);
            System.out.println("Rock (0), paper (1), scissors (2)?");
            int playerchoice=100;
            while (playerchoice != 0&&playerchoice != 1&&playerchoice != 2)
            {
                playerchoice = sc.nextInt();
            }
            int Gameoutcome = 0;//0 -> draw, 1-> Win, 2-> loss
            String output = "";
            switch (choice) {
                case 0:
                    output = "rock";
                    break;
                case 1:
                    output = "paper";
                    break;
                case 2:
                    output = "scissors";
                    break;
            }
            System.out.println("Other person played " + output + ".");
            if (choice == 0 && playerchoice == 1) {
                Gameoutcome = 1;
            } else if (choice == 1 && playerchoice == 2) {
                Gameoutcome = 1;
            } else if (choice == 2 && playerchoice == 0) {
                Gameoutcome = 1;
            } else if (playerchoice == 0 && choice == 1) {
                Gameoutcome = 2;
            } else if (playerchoice == 1 && choice == 2) {
                Gameoutcome = 2;
            }else if (playerchoice == 2 && choice == 0) {
                Gameoutcome = 2;
            }switch (Gameoutcome) {
                case 0:
                    System.out.println("draw");
                    draws++;
                    break;
                case 1:
                    System.out.println("win");
                    wins++;
                    break;
                case 2:
                    System.out.println("loss");
                    losses++;
                    break;
            }System.out.println("wins: "+wins+"| losses: "+losses+"| draws: "+draws);
            System.out.println("replay?(1.yes|2.No)");
            int ans = sc.nextInt();
            if(ans ==1) {
                rerun=true;
            }
        }
    }
}
