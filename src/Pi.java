import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Objects;
import java.util.Scanner;

public class Pi {//commented out tried attempts, successful python code in main
    /*public static int atan1over5;
    public static int atan1over239;
    public static void main(String[] args) {
        System.out.println(1/239);
        System.out.println(16*atan(5,1,false)-4*atan(239,1,false));
        /*
        16atan(1/5)-4atan(1/239)
        atan(x) = x-x^3/3+x^5/5-x^7/7+x^9/9-x^11/11... until 1001

    }
    public static double atan(double denom,int power,boolean plus){//from 1 increasing by 2 for power, plus or minus alternates
        double sum = Math.pow(denom,power);
        if(power==1001){
            return sum;
        }
        if(plus){
            sum += atan(x,power+2,false);
        }else if(!plus){
            sum -= atan(x,power+2,true);
        }
        return sum;
    }*/
    // System.out.println(1/239);
    // double m=3;
    // for(int n=1;n<17;n++){
    //     m=2+Math.sqrt(m);
    //     System.out.println(3*(Math.pow(2,n))*Math.sqrt(4-m));
    // }
    /*
    1/pi = 12 times k from 0->inf sum of ((Math.pow(-1,k))(6k)!(13591409 + 545140134k))/(3k)!(Math.pow(k!,3))(Math.pow(640320,3k+1.5))
     */
    public static void main(String[] args) throws FileNotFoundException {
        File pi = new File("C:\\Users\\adamp\\IdeaProjects\\APComputerScience\\src\\Pi.txt");
        Scanner sc = new Scanner(pi);
        File genpi =new File("C:\\Users\\adamp\\IdeaProjects\\APComputerScience\\src\\GeneratedPi.txt");
        Scanner sc2 = new Scanner(genpi);
        if(Objects.equals(sc.nextLine(), sc2.nextLine())){
            System.out.println("Happy 3-14");
        }
        /*
        import decimal


def binary_split(a, b):
    if b == a + 1:
        Pab = -(6*a - 5)*(2*a - 1)*(6*a - 1)
        Qab = 10939058860032000 * a**3
        Rab = Pab * (545140134*a + 13591409)
    else:
        m = (a + b) // 2
        Pam, Qam, Ram = binary_split(a, m)
        Pmb, Qmb, Rmb = binary_split(m, b)

        Pab = Pam * Pmb
        Qab = Qam * Qmb
        Rab = Qmb * Ram + Pam * Rmb
    return Pab, Qab, Rab


def chudnovsky(n):
    """Chudnovsky algorithm."""
    P1n, Q1n, R1n = binary_split(1, n)
    return (426880 * decimal.Decimal(10005).sqrt() * Q1n) / (13591409*Q1n + R1n)


decimal.getcontext().prec = 100000
n=10000
print(f"{n=} {chudnovsky(n)}\n")
         */
    }
}
