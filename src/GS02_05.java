import java.util.Scanner;
/*
Name:
Problem:
Pseudocode:
Notes: use cosine rule measure of angle C when given a, b and c is c^2 = a^2 + b^2 -2ab cos C
use sine rule measure of angle B when given a, b, c and C : B=arcsine((b sine C)/c)
Maintenance log:
Date:       Done:
 */
public class GS02_05 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("First Side:");
        double a= sc.nextDouble();
        System.out.println("Second Side:");
        double b= sc.nextDouble();
        System.out.println("Third Side:");
        double c= sc.nextDouble();
        double twoab = 2*a*b;
        double aaPlusbbMinuscc= (a*a+b*b)-c*c;
        double cosCequals = aaPlusbbMinuscc/twoab;
        double C=Math.acos((cosCequals*Math.PI)/180);
        double B=Math.asin((((b*Math.sin(C))/c)*Math.PI)/180);
        double A=180-c-b;
        System.out.println("A: "+A+"\nB: +"+B+"\nC: "+C);
    }
}
