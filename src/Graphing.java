import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.Stack;

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


        public Function(){
        }
        public double PerformOperation(double input){
            if(left!=null){
                double l=left.PerformOperation(input);
            }if(right!=null) {
                double r = right.PerformOperation(input);
            }
            return PerformOperation(input);
        }
    }
    public class Operation extends Function {//used to give an operation (not a number) when parsing input

    }
    public class Add extends Operation{
        public double PerformOperation(double input){
            return left.PerformOperation(input) + right.PerformOperation(input);
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
    public class Mod extends Operation{
        public double PerformOperation(double input) {
            return left.PerformOperation(input)%right.PerformOperation(input);
        }
    }
    public class Sin extends Operation{
        public double PerformOperation(double input) {
            return Math.sin(input);
        }
    }
    public class Cos extends Operation{
        public double PerformOperation(double input) {
            return Math.cos(input);
        }
    }
    public class Tan extends Operation{
        public double PerformOperation(double input) {
            return Math.tan(input);
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
        Function a=parseFunction("x");
        //Functions.add(a);
        Function b = parseFunction("2 eq0 *");
        //Functions.add(b);
        setPreferredSize(new Dimension(BoardWidth, BoardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        Dimension d =getPreferredSize();
        setFocusable(true);
        frames = new Timer(0,this);
        frames.start();
        parseFunction("0");
    }
    public Function Inverse(Function originalFunction){
        //get lists of operations and reverse it
        return originalFunction;
    }
    public int precedence(char x){
        if(x=='^'){
            return 2;
        }if(x=='*'||x=='/'){
            return 1;
        }if(x=='+'||x=='-'){
            return 0;
        }
        return -1;//not an operator
    }
    public String IFNtoRPN(String IFN){//infix notation to postfix notation (rpn)
        Stack<Character> stk = new Stack<>();
        String ans="";
        for(int i=0;i<IFN.length();i++){
            char x=IFN.charAt(i);
            if(isNumber(IFN,i)){
                ans+=x;
            }else if(x=='('){
                stk.push('(');
            }else if(x==')'){
                while(!stk.isEmpty()&&stk.peek()!='('){
                    ans+=stk.pop();
                }if(!stk.isEmpty()){
                    stk.pop();
                }
            }else{
                while(!stk.isEmpty()&&precedence(x)<=precedence(stk.peek())){
                    ans+=stk.pop();
                }
                stk.push(x);
            }
        }
        while(!stk.isEmpty()){
            ans+= stk.pop();
        }
        return ans;
    }
    public void AddFunctionIFN(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Equation "+Functions.size()+" write out equation in IFN, x counts as a variable, only whole numbers no decimals :( ");
        System.out.print("y=");
        String rule =sc.nextLine();
        Functions.add(parseFunction(IFNtoRPN(rule)));
    }
    public void AddFunctionRPN(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Equation "+Functions.size()+" write out equation in rpn, x counts as a variable");
        System.out.print("y=");
        String rule =sc.nextLine();
        Functions.add(parseFunction(rule));
    }
    public Function parseFunction(String rule){
        int rangeend=0;//ranges for integers to parse
        int rangelength=0;
        ArrayList<Function> nums = new ArrayList<>();
        ArrayList<Function> ops = new ArrayList<>();
        rule=rule.toLowerCase();
        for(int i=0;i<rule.length();i++){//puts all the numbers and ops in separate array lists
            if(isNumber(rule,i)){
                rangeend =i;
                rangelength++;
            }else{
                if(rangelength==1&&rule.charAt(rangeend)=='.'){rangelength =0;}//ignores period by itself
                if(rangelength==1&&rule.charAt(rangeend)=='-'){rangelength=0;}//ignores neg by itself as number (checks in op)
                if(rangelength!=0){
                    nums.add(new Number(Double.parseDouble(rule.substring((rangeend+1)-rangelength,rangeend+1))));
                }
                if(rule.charAt(i)=='x'){
                    nums.add(new Number());
                }
                rangelength=0;
            }
            if(isOp(rule,i)){
                if(!isNumber(rule,i)) {//doesn't keep the negative
                    ops.add(GiveOp(rule, i));
                }
            }
        }
        if(rangeend==rule.length()-1&&rule.charAt(rangeend)!='x'){nums.add(new Number(Double.parseDouble(rule.substring(rangeend+1-rangelength,rangeend+1))));}
        //gets from the right 2 from nums and does the operation on the right
        //the next right 2 take the function of the operation and the next number with the next operand
        //should have a split point function(num) function(op of two nums)| function(op)
        Function tree =CreateTree(nums,ops);
        return Optimize(tree);
    }
    public Function Optimize(Function tree){
        //go through until the tree isn't changed if op of 2 numbers it can
        boolean changed=false;
        if(tree.left!=null){
            Optimize(tree.left);
        }if(tree.right!=null){
            Optimize(tree.right);
        }
        return tree;
    }
    public Function CreateTree(ArrayList<Function> nums,ArrayList<Function> ops){//creates an abstract function tree (tree of functions that make up the total function)
        for(int i=0;i<ops.size();i++){
            Function op =ops.get(i);
            if(0<=nums.size()-2){
                if(op.left==null) {
                    op.left = nums.get(nums.size() - 2);
                }
            }
            if(op.right==null){
                op.right=nums.get(nums.size()-1);
            }
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
        if(a=='+'||a=='-'||a=='*'||a=='/'||a=='^'||a=='s'||a=='c'||a=='t'||a=='%'){return true;}
        //for equation reference use eq#
        //check for three from end
        if(index<string.length()-3){
            if(a=='e'&&string.charAt(index+1)=='q'&&isNumber(string,index+2)){
                return true;//counts as an equation
            }
        }
        return false;
    }
    public Function GiveOp(String string, int index){//check for ops before numbers are removed
        char character = string.charAt(index);//to check for nums check a string of num possibles (each a num) and the consecutive runs
        if(character=='s'){
            Operation op = new Sin();
            return op;
        }if(character=='c'){
            Operation op = new Cos();
            return op;
        }if(character=='t'){
            Operation op = new Tan();
            return op;
        }
        //only supports 10 equations
        if(character=='e'&&string.charAt(index+1)=='q'&&'0'<=string.charAt(index+2)&&string.charAt(index+2)<='9'){
            Function op = Functions.get(index+2);
            return op;
        }
        if(character=='%'){
            Operation op = new Mod();
            return op;
        }
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
        if(2<=index){
            if(string.charAt(index-1)=='q'&&string.charAt(index-2)=='e'){
                return false;
            }
        }
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
        g.setColor(Color.WHITE);
        for (int a = 1; a < BoardWidth*100; a++) {//goes through each 1/100th of a pixel
            //check for vertical distance, if it is
            double da= (double)a/100;
            double b=da-(BoardWidth/2);//shifts the pixels from 0- BoardWidth to instead show negative values with 0 in middle
            double i = (double) (b/ViewSize)+CameraX;//(a/ViewSize)+CameraX;//transposes the domain (if zoomed in by 2, divides domain by 2 then adds the camera shift of values)
            double h = (double) ((b-1)/ViewSize)+CameraX;//((a-1)/ViewSize)+CameraX;
            if(1<=Functions.size()) {
                for (Function function : Functions) {
                    double fi = function.PerformOperation(i);
                    double fh = function.PerformOperation(h);
                    if (!isNan(fi) && !isNan(fh)) {//to avoid drawing lines in asymptotes, check if it crosses through Nan at any time
                        //if((0<i&&0<h)||(i<0&&h<0)) {
                        double x1;//i & h
                        double y1;//fi & fh
                        double x2;
                        double y2;

                        x1=doTransformationsX(h);
                        x2=doTransformationsX(i);
                        y1=doTransformationsY(fh);
                        y2=doTransformationsY(fi);
                        //if y1 and y2's difference is greater than one do fractions
                        //if(1<Math.abs(y1-y2)){
                            //drawsubsections(g,x1,x2);
                        //}//else{return;}

                        g.drawRect((int) x1, (int) y1, 1, 1);
                        //g.drawLine((int) ((h - CameraX) * ViewSize), (int) ((BoardHeight - test(h) + CameraY) * ViewSize), (int) ((i - CameraX) * ViewSize), (int) ((BoardHeight - test(i) + CameraY) * ViewSize));
                        //}
                        //g.fillRect((int)((i-CameraX)*ViewSize),(int)((BoardHeight-test(i)+CameraY)*ViewSize),1,1);
                    }//g.fillRect((int)((i-CameraX)*ViewSize),(int)((BoardHeight-test2(i)+CameraY)*ViewSize),(int)ViewSize,(int)ViewSize);
                }
            }
        }
    }//y=x 2 x % -
    public void drawsubsections(Graphics g,double xstart,double xend){
        double difference =Math.abs(xstart-xend);
        for (int a = 0; a < 100; a++) {//goes through each 1/100th of a pixel
            //check for vertical distance, if it is
            double da= (double)a/100;
            da+=xstart;
            double b=da-(BoardWidth/2);//shifts the pixels from 0- BoardWidth to instead show negative values with 0 in middle
            double i = (double) (b/ViewSize)+CameraX;//(a/ViewSize)+CameraX;//transposes the domain (if zoomed in by 2, divides domain by 2 then adds the camera shift of values)
            double h = (double) ((b-1)/ViewSize)+CameraX;//((a-1)/ViewSize)+CameraX;
            if(1<=Functions.size()) {
                for (Function function : Functions) {
                    double fi = function.PerformOperation(i);
                    double fh = function.PerformOperation(h);
                    if (!isNan(fi) && !isNan(fh)) {//to avoid drawing lines in asymptotes, check if it crosses through Nan at any time
                        //if((0<i&&0<h)||(i<0&&h<0)) {
                        double x1;//i & h
                        double y1;//fi & fh
                        double x2;
                        double y2;

                        x1=doTransformationsX(h);
                        x2=doTransformationsX(i);
                        y1=doTransformationsY(fh);
                        y2=doTransformationsY(fi);
                        //if y1 and y2's difference is greater than one do fractions
                        if(1<Math.abs(y1-y2)){
                            drawsubsections(g,x1,x2);
                        }

                        g.drawRect((int) x1, (int) y1, 1, 1);
                        //g.drawLine((int) ((h - CameraX) * ViewSize), (int) ((BoardHeight - test(h) + CameraY) * ViewSize), (int) ((i - CameraX) * ViewSize), (int) ((BoardHeight - test(i) + CameraY) * ViewSize));
                        //}
                        //g.fillRect((int)((i-CameraX)*ViewSize),(int)((BoardHeight-test(i)+CameraY)*ViewSize),1,1);
                    }//g.fillRect((int)((i-CameraX)*ViewSize),(int)((BoardHeight-test2(i)+CameraY)*ViewSize),(int)ViewSize,(int)ViewSize);
                }
            }
        }
    }
    public double doTransformationsX(double X){//takes a double x
        X-=CameraX;
        X*=ViewSize;
        X+=BoardWidth/2;
        return X;
    }
    public double doTransformationsY(double Y){
        Y-=CameraY;
        Y*=ViewSize;
        Y+=BoardHeight/2;
        Y=BoardHeight-Y;
        return Y;
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
        }if(e.getKeyCode()==KeyEvent.VK_L){
            AddFunctionIFN();
        }if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE){
            Functions.remove(Functions.size()-1);
        }if(e.getKeyCode()==KeyEvent.VK_G){
            GetAt();
        }
    }
    public void GetAt(){
        Scanner sc = new Scanner(System.in);
        System.out.print("answer to get y value at a certain equation number and what x answer in :Equation number,X :");
        String input = sc.next();
        String s1="0";
        String s2="0";
        for(int i=0;i<input.length();i++){
            if(input.charAt(i)==','){
                s1 = input.substring(0,i);
                s2 = input.substring(i+1);
            }
        }
        Function function = Functions.get(Integer.parseInt(s1));
        System.out.println("\n"+"Eq"+s1+"("+s2+") = "+function.PerformOperation(Double.parseDouble(s2)));
    }
}
