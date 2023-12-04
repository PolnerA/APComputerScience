import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Snake {
    static int snakelength = 1;
    static int snakex = 0;
    static int snakey = 0;

    enum directions {up, left, down, right};
    static directions snakedirections = directions.right;
    public static void main(String[] args) throws InterruptedException {
        /*int mousex =MouseInfo.getPointerInfo().getLocation().x;
        int mousey =MouseInfo.getPointerInfo().getLocation().y;
        if(0<(mousex + 2048/2)-(2048/2)){
            snakedirections=directions.right;
        }//2048 X 1152
        else if(20<mousey){
            snakedirections=directions.down;
        }*/
        Random rng = new Random();
        while (true) {
            int randomdir = rng.nextInt(4);
            if(randomdir==0){
                snakedirections=directions.up;
            } else if (randomdir==1) {
                snakedirections=directions.left;
            }else if (randomdir==2) {
                snakedirections=directions.down;
            }else if (randomdir==3) {
                snakedirections=directions.right;
            }
            switch (snakedirections) {
                case up:
                    snakey--;
                    break;
                case left:
                    snakex--;
                    break;
                case down:
                    snakey++;
                    break;
                case right:
                    snakex++;
                    break;
            }
            DrawSnake();
            System.out.print("\033[H\033[2J");
        }
    }
    public static void DrawSpace() {
        for (int i = 0; i < snakey; i++) {
            System.out.println();
        }
        for (int i = 0; i < snakex; i++) {
            System.out.print(" ");
        }
    }
    public static void DrawSnake() throws InterruptedException {
        DrawSpace();
        for (int i = 0; i < snakelength; i++) {
            System.out.print("*");
        }
        Thread.sleep(500);
        snakelength++;
    }

}
