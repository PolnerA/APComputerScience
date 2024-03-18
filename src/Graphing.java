import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Graphing extends JPanel implements ActionListener, KeyListener {
    static int BoardWidth;
    static int BoardHeight;
    Timer frames;
    float ViewSize=1f;
    int tilesize=1;
    int CameraX=0;
    int CameraY=0;
    public static class Operations{
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
            return a;
        }
    }
    public static class Function{
        String rule;
        ArrayList<Integer> Queue;
        public Function(){

        }
        public Function(String rule){
            this.rule =rule;
            CreateQueue(rule);
        }
        public void CreateQueue(String rule){
            ArrayList<Character> List = new ArrayList<>();
            char[] chars = rule.toCharArray();
            for(int i=0;i<chars.length;i++){
                List.add(chars[i]);
            }
            //120 should mark the start point of the queue
            int level =0;//first removes all spaces, parenthesis
            //check parentheses  (:40 ):41
            //Exponents/Radicals ^:94,
            //Multiplication/Division *:42  /:47
            //Addition/Subtraction  +:43   -:45
            for(int i=0;i<List.size();i++){
                if (List.get(i)==' '){
                    List.remove(i);
                }
            }
            for(int i=0;i<List.size();i++){//multiplication/division
                if(List.get(i)=='('){

                }
            }
        }
        public String partialQueue(String rule, int start, int end){//for parenthesis prob. need a return of an operation queue

            return rule.substring(start,end);
        }
    }
    //to get user input use tokenization reverse polish notation, and java bytecode
    //execute a tree of operations, numbers on stack, perform the operation on the stack,
    //parse the string to get the tree of operations
    public Graphing(int Boardwidth, int Boardheight){
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
        g.drawLine((int)(BoardHeight),(int)(((CameraY)*ViewSize)+(BoardHeight/2)),0,(int)(((CameraY)*ViewSize)+(BoardHeight/2)));//horizontal
        g.drawLine((int)((-CameraX*ViewSize)+(BoardWidth/2)),0,(int)((-CameraX*ViewSize)+(BoardHeight/2)),(int)(BoardHeight));//vertical
        g.setColor(Color.red);


        for (int a = 1; a < BoardWidth; a++) {//goes through each pixel
            double i = (double) (a/ViewSize)+CameraX;
            double h = (double) ((a-1)/ViewSize)+CameraX;

            if(!isNan(test(i))&&!isNan(test(h))) {//to avoid drawing lines in asymptotes, check if it crosses through Nan at any time
                //if((0<i&&0<h)||(i<0&&h<0)) {
                    g.drawLine((int) ((h - CameraX) * ViewSize)+(BoardHeight/2), (int) ((BoardHeight - test(h) + CameraY) * ViewSize)+(BoardHeight/2), (int) ((i - CameraX) * ViewSize)+(BoardHeight/2), (int) ((BoardHeight - test(i) + CameraY) * ViewSize)+(BoardHeight/2));
                //}
                g.fillRect((int)((i-CameraX)*ViewSize),(int)((BoardHeight-test(i)+CameraY)*ViewSize),1,1);
            }//g.fillRect((int)((i-CameraX)*ViewSize),(int)((BoardHeight-test2(i)+CameraY)*ViewSize),(int)ViewSize,(int)ViewSize);
        }
    }
    public double function(double x){
        return x;//returns the gotten function as applied to x
    }
    public Function getUserFunc(){
        return new Function();
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
