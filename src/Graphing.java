import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;

public class Graphing extends JPanel implements ActionListener, KeyListener {
    static int BoardWidth;
    static int BoardHeight;
    Timer frames;
    float ViewSize=1f;
    int tilesize=1;
    int CameraX=0;
    int CameraY=0;
    public class Function{//acts as a tree, recursively has smaller functions within it abstract to be able to utilize.
        Function left;
        Function right;
        boolean isOp=true;

        public double PerformOperation() {
            return 0;
        }

        public Function(){
        }
        public double Evaluate(){
            double l=left.PerformOperation();
            double r=right.PerformOperation();
            return PerformOperation();
        }
    }
    public class Operation extends Function {

    }
    public class Add extends Operation{
        public double PerformOperation(){
            return left.PerformOperation()+right.PerformOperation();
        }
    }
    public class Sub extends Operation{
        public double PerformOperation(){
            return left.PerformOperation()-right.PerformOperation();
        }
    }
    public class Mult extends Operation{
        public double PerformOperation(){
            return left.PerformOperation()*right.PerformOperation();
        }
    }
    public class Div extends Operation{
        public double PerformOperation() {
            return left.PerformOperation()/right.PerformOperation();
        }
    }
    public class Number extends Function{
        double number;
        public Number(double a){
            isOp=false;
            number=a;
        }
        public double PerformOperation(){
            return number;
        }

    }
    /*public static class Function{
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
    }*/
    //to get user input use tokenization reverse polish notation, and java bytecode
    //execute a tree of operations, numbers on stack, perform the operation on the stack,
    //parse the string to get the tree of operations
    public Graphing(int Boardwidth, int Boardheight){
        //rpn gets 5 10 3 +-== 5-(10+3)
        //for each group of two num at the end grab the first op
        // num1 num2 num3 op1 op2, grab num2 op1 num3
        //list of operations when parsing, set the numbers/ops to the left and the right
        BoardWidth=Boardwidth;
        BoardHeight=Boardheight;
        Function a = parseFunction();
        setPreferredSize(new Dimension(BoardWidth, BoardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        Dimension d =getPreferredSize();
        setFocusable(true);
        frames = new Timer(0,this);
        frames.start();
    }
    public Function parseFunction(){
        Scanner sc = new Scanner(System.in);
        String rule =sc.nextLine();
        int rangeend;//ranges for integers to parse
        int rangelength=0;
        for(int i=0;i<rule.length();i++){
            char currentchar =rule.charAt(i);
            if(isNumber(rule,i)){
                rangeend =i;
                rangelength++;
            }else{
                rangelength
            }
        }
        return new Function();
    }
    public Operation GiveOp(String string, int index){//check for ops before numbers are removed
        char character = string.charAt(index);//to check for nums check a string of num possibles (each a num) and the consecutive runs
        if(character=='+'){
            Operation op = new Add();
            return op;

        }if(character=='*'){
            Operation op = new Mult();

            return op;
        }if(character=='/') {
            Operation op = new Div();
            return op;
        }
        //subtraction checks (could also be a negative number)
        if(index==string.length()-1) {
            Operation op = new Sub();
            return op;
        }if(!isNumber(string,index+1)){
            Operation op = new Sub();
            return op;
        }
        return new Operation();

    }
    public boolean isNumber(String string,int index){
        char a = string.charAt(index);
        try{
            String s =""+a;
            Integer.parseInt(s);
        }catch (Exception e){
            return false;
        }
        return true;
    }
    public boolean isNumber(String string,int indexstart,int indexend){
        String s="";
        for(int i=indexstart;i<indexend;i++){
            char a = string.charAt(i);
            s+=a;
        }
        try{
            Integer.parseInt(s);
        }catch (Exception e){
            return false;
        }
        return true;
    }
    public int ParseInt(String string, int indexstart, int indexend){
        String s="";
        for(int i=indexstart;i<indexend;i++){
            s+=string.charAt(i);
        }
        return Integer.parseInt(s);//integer could just parse a substring
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public double test(double x){
        //if((int)x==0){return Math.sqrt(-1);}//returns Nan
        return Math.pow(x,2);
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
            double b=a-(BoardWidth/2);
            double i = (double) (b/ViewSize)+CameraX;//(a/ViewSize)+CameraX;
            double h = (double) ((b-1)/ViewSize)+CameraX;//((a-1)/ViewSize)+CameraX;

            if(!isNan(test(i))&&!isNan(test(h))) {//to avoid drawing lines in asymptotes, check if it crosses through Nan at any time
                //if((0<i&&0<h)||(i<0&&h<0)) {
                g.drawLine((int)((h-CameraX)*ViewSize)+(BoardWidth/2),(int)((BoardHeight-(test(h)+(BoardHeight/2))+CameraY)*ViewSize),(int)((i-CameraX)*ViewSize)+(BoardWidth/2),(int)(((BoardHeight-(test(i)+(BoardHeight/2)))+CameraY)*ViewSize));
                //g.drawLine((int) ((h - CameraX) * ViewSize), (int) ((BoardHeight - test(h) + CameraY) * ViewSize), (int) ((i - CameraX) * ViewSize), (int) ((BoardHeight - test(i) + CameraY) * ViewSize));
                //}
                //g.fillRect((int)((i-CameraX)*ViewSize),(int)((BoardHeight-test(i)+CameraY)*ViewSize),1,1);
            }//g.fillRect((int)((i-CameraX)*ViewSize),(int)((BoardHeight-test2(i)+CameraY)*ViewSize),(int)ViewSize,(int)ViewSize);
        }
    }
    //public double function(double x){
    //    return x;//returns the gotten function as applied to x
    //}
    //public Function getUserFunc(){
    //    return new Function();
    //}
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
