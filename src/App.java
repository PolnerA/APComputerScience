import javax.swing.*;
import java.awt.*;
//set up with JFrame for the sorting algorithm application
public class App extends Frame{//an extension of Frame (a window)

    public static void main(String[] args) throws InterruptedException {
        //sets the width and height
        int boardWidth = 2042;
        int boardHeight = 1148;

        //name of the window is set to sorting algorithms, makes it visible and sets the size
        JFrame frame = new JFrame("Life");
        frame.setSize(boardWidth, boardHeight);
        //sets the location to about the middle of the screen
        frame.setLocation(0,0);
        frame.setResizable(true);
        //sets the window to exit on close
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Creates an instance of the Sorting algorithms through the Sorting algorithms class, and adds it to the window giving focus to it
        Life life = new Life(boardWidth, boardHeight);
        frame.add(life);
        frame.pack();
        life.requestFocus();
        frame.setVisible(true);
    }
}
