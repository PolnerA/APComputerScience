import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Graphing extends JPanel implements ActionListener, KeyListener {
    int BoardWidth;
    int BoardHeight;
    Timer frames;
    float ViewSize=1f;
    int CameraX=0;
    int CameraY=0;
    public Graphing(int Boardwidth,int Boardheight){
        BoardWidth=Boardwidth;
        BoardHeight=Boardheight;
        setPreferredSize(new Dimension(BoardWidth, BoardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        Dimension d =getPreferredSize();
        setFocusable(true);
        frames = new Timer(0,this);
        frames.start();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public int test(int x){
        return (int)Math.pow(x,2);
    }
    public void draw(Graphics g){
        g.setColor(Color.GRAY);
        g.drawLine(0+CameraX,0,BoardWidth+CameraX,0);
        g.drawLine(0+CameraX,0,-BoardWidth+CameraX,0);
        g.drawLine(0+CameraX,0,0+CameraX,-BoardHeight);
        g.drawLine(0+CameraX,0,0+CameraX,BoardHeight);
        g.setColor(Color.white);
        g.drawRect(0-CameraX,BoardHeight-test(0)+CameraY,1,1);
        for (int i = 1+CameraX; i < BoardHeight-CameraX; i++) {
            g.drawLine(i-1-CameraX,BoardHeight - test(i-1)+CameraY,i-CameraX,BoardHeight-test(i)+CameraY);
            g.drawRect(i-CameraX,BoardHeight-test(i)+CameraY,1,1);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_E){
            ViewSize*=1.1f;//multiplies the view-size making things bigger
        }if(e.getKeyCode()==KeyEvent.VK_Q){
            ViewSize*=0.9f;//divides the view-size making things smaller
        }if(e.getKeyCode()==KeyEvent.VK_W) {
            CameraY+=10;
        }if(e.getKeyCode()==KeyEvent.VK_A) {//does the same for all WASD keys
            CameraX-=10;
        }if(e.getKeyCode()==KeyEvent.VK_S) {
            CameraY-=10;
        }if(e.getKeyCode()==KeyEvent.VK_D) {
            CameraX+=10;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
