/*
Name: Adam Polner
Problem: Write a method called quadratic (and testing code for it) that takes three parameters
(the coefficients of a polynomial equation) and outputs the roots.
Pseudocode: Input a b and c into quadratic formula, simplify then use the two plus and minus functions,
test the roots those give you by plugging it into the original equation
Maintenance log:
Date:       Done:
9/21/2023   Finished GS02-04
 */
public class GS02_04 {
    public static void main(String[] args) {
        Quadratic(1,0,-1);
    }
    static void Quadratic(int a, int b, int c)//if (ax^2+bx+c=0)
    {
        //     -b +- Sqr((b)^2 - 4ac)
        //x =  -----------------------
        //             2a
        int bsquared = b*b;
        int fourac = 4*a*c;
        double sqr = Math.sqrt(bsquared-fourac);
        double rootone = Plus(a,b,sqr);
        double roottwo = Minus(a,b,sqr);
        double axsquaredone = (rootone*rootone) *a;
        double bxone = (rootone)*(b);
        if (axsquaredone+bxone +c==0) {
            System.out.println(rootone + " : first  root");
        }
        double axsquaredtwo = (roottwo*roottwo) *a;
        double bxtwo = (roottwo)*(b);
        if(axsquaredtwo+bxtwo+c == 0) {
            System.out.println(roottwo + " : second root");
        }
    }
    static double Plus(int a, int b, double sqr) {
        double negb = b-(2*b);
        double x = negb + sqr;
        x = x/(2*a);
        return x;
    }
    static double Minus(int a, int b, double sqr) {
        double negb = b-(2*b);
        double x = negb - sqr;
        x = x/(2*a);
        return x;
    }
}
