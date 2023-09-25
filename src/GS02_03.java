public class GS02_03 {
    public static void main(String[] args) {
        DrawStair(1);
    }
    static void DrawStair(int steps) {
        //draw set amount of spaces +2 then an o then 2 spaces followed by 6 * then a * at the end depending on height
        //after same set amount of spaces +1 space then a /|\ (\\) then a space and an *, amount of spaces vary, with an * to end it off
        //same set amount of spaces +1 space then a / with a space then \ (\\) then a space followed by * amount of spaces vary *
        DrawTopStair(steps);
    }
    static void DrawTopStair(int steps) {
        for (int i = 0; i <= 2 + ((steps-1) * 5); i++) {
        System.out.print(" ");
        }
        System.out.print("o");
        System.out.print("  ");
        System.out.print("*******\n");
        for (int i = 0; i <= 1 + ((steps-1) * 5); i++) {
            System.out.print(" ");
        }
        System.out.print("/|\\ *");

    }
}
//
//
//
//                      o  *******
//                     /|\ *     *
//                     / \ *     *
//                 o  ******     *
//                /|\ *          *
//                / \ *          *
//            o  ******          *
//           /|\ *               *
//           / \ *               *
//       o  ******               *
//      /|\ *                    *
//      / \ *                    *
//  o  ******                    *
// /|\ *                         *
// / \ *                         *
//********************************
