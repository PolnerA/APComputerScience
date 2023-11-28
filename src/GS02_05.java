import java.util.Scanner;
/*
Name: Adam Polner
Problem:Do BJP Ch 3 Project 4. Write a program that prompts 
for the three sides of a triangle and reports the three angles.
Pseudocode: Ask for the first three, check if it's a real triangle then use the cosine rule to find
angles c and b, then subtract from 180 to get a
Notes: use cosine rule measure of angle C when given a, b and c is c^2 = a^2 + b^2 -2ab cos C
Measure of angle C = arccos((a^2+b^2-c^2)/(2*a*b))
B = arccos((a^2+c^2-b^2)/(2*c*a))
Maintenance log:
Date:       Done:
9/28/2023    Finished Gs02-05
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
        if(a<b+c||b<c+a||c<b+a) {
            double C = acos((a * a + b * b - c * c) / (2 * a * b));//Function converts to radian and returns in deg
            double B = acos((a * a + c * c - b * b) / (2 * c * a));
            double A = 180 - C - B;
            System.out.println("A: " + A + "\nB: " + B + "\nC: " + C);
        }
    }
    public static double acos(double r)
    {
        return ((Math.acos(r)*180)/Math.PI);
    }
}
