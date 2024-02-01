import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
//set up with JFrame for the sorting algorithm application
public class App extends Frame{//an extension of Frame (a window)

    public static void main(String[] args) throws InterruptedException {
        //sets the width and height
        int boardWidth = 1000;
        int boardHeight = boardWidth;

        //name of the window is set to sorting algorithms, makes it visible and sets the size
        JFrame frame = new JFrame("Sorting Algorithms");
        frame.setSize(boardWidth, boardHeight);
        //sets the location to about the middle of the screen
        frame.setLocation(500,0);
        frame.setResizable(true);
        //sets the window to exit on close
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Creates an instance of the Sorting algorithms through the Sorting algorithms class, and adds it to the window giving focus to it
        SortingAlgorithms sorting = new SortingAlgorithms(boardWidth, boardHeight);
        frame.add(sorting);
        frame.pack();
        sorting.requestFocus();
        frame.setVisible(true);
    }
}
