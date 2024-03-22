import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
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

        public double PerformOperation(double input) {
            return 0;
        }

        public Function(){
        }
        public double Evaluate(double input){
            if(left!=null){
                double l=left.PerformOperation(input);
            }if(right!=null) {
                double r = right.PerformOperation(input);
            }
            return PerformOperation(input);
        }
    }
    public class Operation extends Function {

    }
    public class Add extends Operation{
        public double PerformOperation(double input){
            return left.PerformOperation(input)+right.PerformOperation(input);
        }
    }
    public class Sub extends Operation{
        public double PerformOperation(double input){
            return left.PerformOperation(input)-right.PerformOperation(input);
        }
    }
    public class Mult extends Operation{
        public double PerformOperation(double input){
            return left.PerformOperation(input)*right.PerformOperation(input);
        }
    }
    public class Div extends Operation{
        public double PerformOperation(double input) {
            return left.PerformOperation(input)/right.PerformOperation(input);
        }
    }
    public class Exp extends Operation{
        public double PerformOperation(double input) {
            return Math.pow(left.PerformOperation(input),right.PerformOperation(input));
        }
    }
    public class Number extends Function{
        double number;
        boolean entrance=false;
        public Number(double a){
            number=a;
        }
        public Number(){
            entrance=true;//if it is a variable it is flagged to return the input instead of the value of nothing
        }//for variables (prob. a-z) a blank number is created (if a method is called at x like the current functions it searches through the function it has (or list of funcs) to find the null numbers and plugs the argument recieved in)
        public double PerformOperation(double input){
            if(entrance){return input;}
            return number;
        }

    }
    //to get user input use tokenization reverse polish notation, and java bytecode
    //execute a tree of operations, numbers on stack, perform the operation on the stack,
    //parse the string to get the tree of operations
    public ArrayList<Function> Functions = new ArrayList<>();
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
        parseFunction("0");
    }
    //public String IFNtoPFN(){//infix notation to postfix notation (rpn)
        
    //}
    public void AddFunctionRPN(){
        Scanner sc = new Scanner(System.in);
        System.out.println("write out equation in rpn, x or X counts as a variable");
        System.out.print("y=");
        String rule =sc.nextLine();
        Functions.add(parseFunction(rule));
    }
    public Function parseFunction(String rule){
        int rangeend=0;//ranges for integers to parse
        int rangelength=0;
        ArrayList<Function> nums = new ArrayList<>();
        ArrayList<Function> ops = new ArrayList<>();
        for(int i=0;i<rule.length();i++){//puts all the numbers and ops in seperat array lists
            if(isNumber(rule,i)){
                rangeend =i;
                rangelength++;
            }else{
                if(rangelength==1&&rule.charAt(rangeend)=='.'){rangelength =0;}//ignores period by itself
                if(rangelength==1&&rule.charAt(rangeend)=='-'){rangelength=0;}//ignores neg by itself as number (checks in op)
                if(rangelength!=0){
                    nums.add(new Number(Double.parseDouble(rule.substring((rangeend+1)-rangelength,rangeend+1))));
                }
                if(rule.charAt(i)=='x'||rule.charAt(i)=='X'){
                    nums.add(new Number());
                }
                rangelength=0;
            }
            //char currentchar =rule.charAt(i);
            if(isOp(rule,i)){
                if(!isNumber(rule,i)) {//doesn't keep the negative
                    ops.add(GiveOp(rule, i));
                }
            }
        }
        if(rangeend==rule.length()-1&&rule.charAt(rangeend)!='x'&&rule.charAt(rangeend)!='X'){nums.add(new Number(Double.parseDouble(rule.substring(rangeend+1-rangelength,rangeend+1))));}
        //gets from the right 2 from nums and does the operation on the right
        //the next right 2 take the function of the operation and the next number with the next operand
        //should have a split point function(num) function(op of two nums)| function(op)

        return CreateAFT(nums,ops);
    }
    public Function CreateAFT(ArrayList<Function> nums,ArrayList<Function> ops){//creates an abstract function tree (tree of functions that make up the total function)
        for(int i=0;i<ops.size();i++){
            Function op =ops.get(i);
            op.left=nums.get(nums.size()-2);
            op.right=nums.get(nums.size()-1);
            if(1<=nums.size()){
                nums.remove(nums.size()-1);
            }
            if(1<=nums.size()){
                nums.remove(nums.size()-1);
            }
            nums.add(op);
        }
        return nums.get(0);
    }
    public boolean isOp(String string, int index){
        char a = string.charAt(index);
        if(a=='+'||a=='-'||a=='*'||a=='/'||a=='^'){return true;}
        return false;
    }
    public Operation GiveOp(String string, int index){//check for ops before numbers are removed
        char character = string.charAt(index);//to check for nums check a string of num possibles (each a num) and the consecutive runs
        if(character=='+'){
            Operation op = new Add();
            return op;

        }if(character=='*'){
            Operation op = new Mult();

            return op;
        }if(character=='^'){
            Operation op = new Exp();
            return op;
        }if(character=='/') {
            Operation op = new Div();
            return op;
        }
        return new Sub();

    }
    public boolean isNumber(String string,int index){
        char a = string.charAt(index);
        try{
            String s =""+a;
            Double.parseDouble(s);
        }catch (Exception e){
            if(a=='.'){
                return true;//returns true for decimal spots ie. 5.2
            }               //period by itself wouldn't work
            if(a=='-'){
                return true;//returns true for negative numbers
                            //- by itself should be an operation
            }
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
            Double.parseDouble(s);
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
            if(1<=Functions.size()) {
                for (Function function : Functions) {
                    double fi = function.Evaluate(i);
                    double fh = function.Evaluate(h);
                    if (!isNan(fi) && !isNan(fh)) {//to avoid drawing lines in asymptotes, check if it crosses through Nan at any time
                        //if((0<i&&0<h)||(i<0&&h<0)) {
                        g.drawLine((int) ((h - CameraX) * ViewSize) + (BoardWidth / 2), (int) ((BoardHeight - (fh + (BoardHeight / 2)) + CameraY) * ViewSize), (int) ((i - CameraX) * ViewSize) + (BoardWidth / 2), (int) (((BoardHeight - (fi + (BoardHeight / 2))) + CameraY) * ViewSize));
                        //g.drawLine((int) ((h - CameraX) * ViewSize), (int) ((BoardHeight - test(h) + CameraY) * ViewSize), (int) ((i - CameraX) * ViewSize), (int) ((BoardHeight - test(i) + CameraY) * ViewSize));
                        //}
                        //g.fillRect((int)((i-CameraX)*ViewSize),(int)((BoardHeight-test(i)+CameraY)*ViewSize),1,1);
                    }//g.fillRect((int)((i-CameraX)*ViewSize),(int)((BoardHeight-test2(i)+CameraY)*ViewSize),(int)ViewSize,(int)ViewSize);
                }
            }
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
        if(e.getKeyCode()==KeyEvent.VK_R) {
            AddFunctionRPN();
        }
    }
}
