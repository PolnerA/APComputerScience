import java.util.Scanner;

public class GS02_01 {
    public static void main(String[] args) {
        System.out.println("up to what number squared?");
        Scanner sc = new Scanner(System.in);
        int max =sc.nextInt();
        int num=1;
        int add=3;
        //1+3 = 4+5 = 9+7 = 16+9 = 25+11 = 36+13 = 49
        for(int i=1; i<=max;i++)
        {
            System.out.print(num+"\t");
            num+=add;
            add+=2;
        }
    }
}
