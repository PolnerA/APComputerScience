public class GS02_03 {
    public static void main(String[] args) {
        DrawStair(1);
    }
    static void DrawStair(int steps) {
        for (int i=0;i<=steps*5;i++) {
            if(i==(steps*5)-3) {
                System.out.print("o");
            }
            else{
                System.out.print(" ");
            }
        }
        for(int i=0;i<=6;i++) {
            System.out.print("*");
        }
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
