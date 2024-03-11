import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Graphing extends JPanel implements ActionListener, KeyListener {
    int BoardWidth;
    int BoardHeight;
    Timer frames;
    float ViewSize=1f;
    int tilesize=1;
    int CameraX=0;
    int CameraY=0;
    public class Operations{
        int id;
        String Type;
        public Operations(int id){
            this.id=id;
        }
        public double PerformOperation(double a, double b){
            if(id==94){
                return Math.pow(a,b);
            }if(id==42){
                return a*b;
            }if(id==47){
                return a/b;
            }if(id==43){
                return a+b;
            }if(id==45){
                return a-b;
            }
        }
    }
    private class Function{
        String rule;
        ArrayList<Integer> Queue;
        public Function(){

        }
        public Function(String rule){
            this.rule =rule;
            CreateQueue(rule);
        }
        public void CreateQueue(String rule){
            char[] array = rule.toCharArray();
            int i=0;
            while(true){//loops until the queue is gotten from the rule then breaks
                //check parentheses  (:40 ):41
                //Exponents/Radicals ^:94,
                //Multiplication/Division *:42  /:47
                //Addition/Subtraction  +:43   -:45
                i++;
                if(i== array.length-1){
                    i=0;
                }
            }
        }
    }
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
    public double test(double x){
        if(x==0){return Math.sqrt(-1);}//returns Nan
        return 1/x;
    }
    public double test2(double x){//returns Nan for negative values of x
        double number = Math.sqrt(x);
        return number;
    }
    public void draw(Graphics g){
        g.setColor(Color.GRAY);
        //first point at origin
        g.drawLine((int)(BoardHeight),(int)((BoardHeight+CameraY)*ViewSize),0,(int)((BoardHeight+CameraY)*ViewSize));//horizontal
        g.drawLine((int)(-CameraX*ViewSize),0,(int)(-CameraX*ViewSize),(int)(BoardHeight));//vertical
        g.setColor(Color.white);


        for (int a = 1; a < BoardWidth; a++) {//goes through each pixel
            double i = (double) (a/ViewSize)+CameraX;
            double h = (double) ((a-1)/ViewSize)+CameraX;
            if(!isNan(test(i))&&!isNan(test(h))) {//to avoid drawing lines in asymptotes, check if it crosses through Nan at any time
                //if((0<i&&0<h)||(i<0&&h<0)) {
                    g.drawLine((int) ((h - CameraX) * ViewSize), (int) ((BoardHeight - test(h) + CameraY) * ViewSize), (int) ((i - CameraX) * ViewSize), (int) ((BoardHeight - test(i) + CameraY) * ViewSize));
                //}
                g.fillRect((int)((i-CameraX)*ViewSize),(int)((BoardHeight-test(i)+CameraY)*ViewSize),1,1);
            }//g.fillRect((int)((i-CameraX)*ViewSize),(int)((BoardHeight-test2(i)+CameraY)*ViewSize),(int)ViewSize,(int)ViewSize);
        }
    }
    public boolean isNan(double v){
        return (v!=v);//Nan isn't equal to itself
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
            CameraY+=5;
        }if(e.getKeyCode()==KeyEvent.VK_A) {//does the same for all WASD keys
            CameraX-=5;
        }if(e.getKeyCode()==KeyEvent.VK_S) {
            CameraY-=5;
        }if(e.getKeyCode()==KeyEvent.VK_D) {
            CameraX+=5;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
