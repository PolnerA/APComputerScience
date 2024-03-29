import javax.swing.*;
import java.awt.*;
//set up with JFrame for the Life application
public class App extends Frame{//an extension of Frame (a window)

    public static void main(String[] args) throws InterruptedException {
        //sets the width and height
        int boardWidth = 1000;
        int boardHeight = 1000;

        //name of the window is set to Life, makes it visible and sets the size
        JFrame frame = new JFrame("Graph");
        frame.setSize(boardWidth, boardHeight);
        //sets the location to the top left
        frame.setLocation(0,0);
        frame.setResizable(true);
        //sets the window to exit on close
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Creates an instance of Life through the Life class, and adds it to the window giving focus to it
        Graphing graph = new Graphing(boardWidth, boardHeight);
        frame.add(graph);
        frame.pack();
        graph.requestFocus();
        frame.setVisible(true);
    }
}
