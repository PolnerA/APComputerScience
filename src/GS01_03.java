/*
Name: Adam Polner
Problem: Do BJP Ch 1 exercise 9 (make sure to use a static method).
Pseudocode: Use methods for each part and arrange accordingly to exercise 9
Notes:
Maintenance log:
Date:       Done:
9/26/2023   Finished GS01-03
 */
public class GS01_03 {
    public static void main(String[] args) {
        Top();
        Bottom();
        Middle();
        Top();
        Bottom();
        Middle();
        Bottom();
        Top();
        Middle();
        Bottom();
    }
    public static void Top() {
        System.out.print("  _______\n");
        System.out.print(" /       \\\n");
        System.out.print("/         \\\n");
    }
    public static void Middle(){
        System.out.print("-\"-'-\"-'-\"-\n");
    }
    public static void Bottom(){
        System.out.print("\\         /\n");
        System.out.print(" \\       /\n");
        System.out.print("  -------\n");
    }
}
