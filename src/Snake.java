import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;

public class Snake {
    public static void main(String[] args) throws InterruptedException {
        Game maingame = new Game();
        maingame.run();
        Keyboard Object = new Keyboard();
        Object.run();
    }

}
class Game extends Thread {
    static int snakelength = 1;
    static int snakex = 0;
    static int snakey = 0;

    public static enum directions {up, left, down, right}

    ;
    public static directions snakedirections = directions.right;

    public void run() {
        try {
            while (true) {
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
                int direction = 1;
                if (direction == 2 && snakedirections != directions.up) {
                    snakedirections = directions.down;
                } else if (direction == 4 && snakedirections != directions.right) {
                    snakedirections = directions.left;
                } else if (direction == 6 && snakedirections != directions.left) {
                    snakedirections = directions.right;
                } else if (direction == 8 && snakedirections != directions.down) {
                    snakedirections = directions.up;
                }
                System.out.print("\033[H\033[2J");
            }
        } catch (Exception e) {
            System.out.print("Exception is caught.");
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
        for(int i=0;i<snakelength;i++){
            System.out.print("*");
        }
    }
}
class Keyboard extends Thread{
    public void run()
    {
        try {
            final JFrame frame = new JFrame();
            synchronized (frame) {
                frame.setUndecorated(true);
                frame.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
                frame.addKeyListener(new KeyListener() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        synchronized (frame) {
                            frame.setVisible(false);
                            frame.dispose();
                            frame.notify();
                        }
                    }
                    @Override
                    public void keyReleased(KeyEvent e) {
                        Game.snakedirections = Game.directions.down;
                    }
                    @Override
                    public void keyTyped(KeyEvent e) {
                    }
                });
                frame.setVisible(true);
                try {
                    frame.wait();
                } catch (InterruptedException e1) {
                }
            }
        }
        catch (Exception e) {
            // Throwing an exception
            System.out.println("Exception is caught");
        }
    }
}
