import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
public class Snake {
    public static void main(String[] args) throws InterruptedException {
        Thread maingame = new Thread(new Game1());
        maingame.run();
        Thread input = new Thread(new Keyboard());
        input.run();
    }
    public static class Game1 implements Runnable{
        int snakelength = 1;
        int snakex = 0;
        int snakey = 0;

        public enum directions {up, left, down, right};
        public static directions snakedirections = directions.right;
        public Game1(){

        }
        @Override
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
                    System.out.print("\033[H\033[2J");
                }
            } catch (Exception e) {
                System.out.print("Exception is caught.");
            }
        }
        public void DrawSpace() {
            for (int i = 0; i < snakey; i++) {
                System.out.println();
            }
            for (int i = 0; i < snakex; i++) {
                System.out.print(" ");
            }
        }
        public void DrawSnake() throws InterruptedException {
            DrawSpace();
            for(int i=0;i<snakelength;i++){
                System.out.print("*");
            }
            Thread.sleep(500);
        }
    }

}
class Game implements Runnable {
}
class Keyboard implements Runnable{
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
