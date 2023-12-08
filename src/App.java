import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class App extends Frame{

    public static void main(String[] args) {
        //new App();

    //public App (){

            //setVisible(true);
            //setSize(300, 300);

/*
        // Creating a panel to add
        JFrame f = new JFrame();
        f.setSize(400,400);
        // Setting the size of frame
        Graphics g =f.getGraphics();

        g.drawLine(0,0,100,100);
        f.show();*/
        int boardWidth = 2000;
        int boardHeight = boardWidth;

        JFrame frame = new JFrame("Snake");
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Snake snake = new Snake(boardWidth, boardHeight);
        frame.add(snake);
        snake.requestFocus();
    }
    //public void paint(Graphics g)
    //{
        //g.drawLine(0, 0, 200, 200);
    //}
}
