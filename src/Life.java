import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/*
rules:
Any live cell with fewer than two live neighbors dies, as if by underpopulation.
Any live cell with two or three live neighbors lives on to the next generation.
Any live cell with more than three live neighbors dies, as if by overpopulation.
Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
 */
public class Life extends JPanel implements ActionListener{
    private static class Tile{
        int x;
        int y;
        Tile(int x, int y){
            this.x=x;
            this.y=y;
        }
    }
    int tilesize=10;
    private static class Cell{

    }
    int BoardWidth;
    int BoardHeight;
    int[][] pixels;
    Timer frames;
    public Life(int boardwidth,int boardheight){
        BoardHeight=boardheight;
        BoardWidth=boardwidth;
        setPreferredSize(new Dimension(BoardWidth,BoardHeight));
        setBackground(Color.black);
        setFocusable(true);
        frames = new Timer(10,this);
        frames.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        draw(g);
    }
    public void draw(Graphics g){
        g.setColor(new Color(255,255,255));
    }

    @Override
    public void actionPerformed(ActionEvent e) { //called every x milliseconds by gameLoop timer
       repaint();
    }
}
