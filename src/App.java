import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
//set up with JFrame for the snake application
public class App extends Frame{//an extension of Frame (a window)

    public static void main(String[] args) throws InterruptedException {
        //sets the width and height, height has to be slightly less to keep squares equal
        int boardWidth = 1000;
        int boardHeight = boardWidth;

        //name of the window is set to snake, makes it visible and sets the size
        JFrame frame = new JFrame("Sorting Algorithms");
        frame.setSize(boardWidth, boardHeight);
        //doesn't keep the boards location relative to a component
        frame.setLocation(500,0);
        //snake doesn't need to be resized
        frame.setResizable(false);
        //sets the window to exit on close
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Creates an instance of the snake-game through the snake class, and adds it to the window giving focus to it
        SortingAlgorithms sorting = new SortingAlgorithms(boardWidth, boardHeight);
        frame.add(sorting);
        frame.pack();
        sorting.requestFocus();
        frame.setVisible(true);
    }
}
