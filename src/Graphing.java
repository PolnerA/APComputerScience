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
    int BoardWidth;
    int BoardHeight;
    Timer frames;
    boolean gridlines=false;
    float ViewSize=1f;
    int tilesize=1;
    int CameraX=0;
    int CameraY=0;
    public class Input{
        double value;
        boolean check;
        public Input(){
            check=true;
        }
        public Input(double a){
            value=a;
        }
    }
    public class Function{//acts as a tree, recursively has smaller functions within it abstract to be able to utilize.
        Function left;
        Function right;

        public Function(){
        }

        @Override
        public boolean equals(Object o) {
            Function other = (Function) o;
            //checks if the recursively goes through all the branches comparing each class (doesn't return true for 2*x and x*2)
            if (left != null) {
                if(left.equals(other.left)&&left.getClass()==other.left.getClass()){
                    if(right!=null){
                        if(right.equals(other.right)&&right.getClass()==other.right.getClass()){
                            return true;
                        }
                    }
                    else if(other.right==null){
                        return true;
                    }
                }
            }
            else if(other.left ==null){
                return true;
            }
            return false;
        }

        public double PerformOperation(Input input){
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
        public double PerformOperation(Input input){
            return left.PerformOperation(input) + right.PerformOperation(input);
        }
    }
    public class Sub extends Operation{
        public double PerformOperation(Input input){
            return left.PerformOperation(input)-right.PerformOperation(input);
        }
    }
    public class Mult extends Operation{
        public double PerformOperation(Input input){
            return left.PerformOperation(input)*right.PerformOperation(input);
        }
    }
    public class Div extends Operation{
        public double PerformOperation(Input input) {
            return left.PerformOperation(input)/right.PerformOperation(input);
        }
    }
    public class Exp extends Operation{
        public double PerformOperation(Input input) {
            return Math.pow(left.PerformOperation(input),right.PerformOperation(input));
        }
    }
    public class Number extends Function{
        double number;
        boolean entrance=false;

        public Number(double a){
            number=a;
        }

        @Override
        public boolean equals(Object o) {
            if(super.equals(o)){
                if(o.getClass()== Number.class){
                    Number other=(Number)o;
                    if(!entrance){
                        if(other.number==number){
                            return true;
                        }
                    }else{
                        if(other.entrance){
                            return true;
                        }
                    }
                }
            }
            return false;

        }

        public Number(){
            entrance=true;//if it is a variable it is flagged to return the input instead of the value of nothing
        }//for variables (prob. a-z) a blank number is created (if a method is called at x like the current functions it searches through the function it has (or list of funcs) to find the null numbers and plugs the argument recieved in)
        public double PerformOperation(Input input){
            if(entrance){
                if(!input.check){
                    return input.value;
                }
            }
            return number;
        }

    }
    //to get user input use tokenization reverse polish notation, and java bytecode
    //execute a tree of operations, numbers on stack, perform the operation on the stack,
    //parse the string to get the tree of operations
    public ArrayList<Function> Functions = new ArrayList<>();
    public ArrayList<Function> InvFunctions = new ArrayList<>();
    public Graphing(int Boardwidth, int Boardheight){
        BoardWidth=Boardwidth;
        //inv doesn't work as you don't know what is on what side of the mult, so how div?
        Function b = new Mult();
        b.left=new Number();
        b.right=new Number(2);
        Functions.add(b);//eq0 == y=x
        Function a = parseFunction("10 eq0 *");
        BoardHeight=Boardheight;
        setPreferredSize(new Dimension(BoardWidth, BoardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        Dimension d =getPreferredSize();
        setFocusable(true);
        frames = new Timer(0,this);
        frames.start();
    }
    public Function Inverse(Function originalFunction){
        //get lists of operations and reverse it
        return originalFunction;
    }


    public void AddFunctionRPN(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Equation "+Functions.size()+" write out equation in rpn, x counts as a variable");
        System.out.print("y=");
        String rule =sc.nextLine();
        Functions.add(parseFunction(rule));
    }
    public Function parseFunction(String rule){//eq# would count as a number as it should return one instead of an operation
        int rangeend=0;//ranges for integers to parse
        int rangelength=0;
        ArrayList<Function> nums = new ArrayList<>();// 10 eq0 +
        ArrayList<Function> ops = new ArrayList<>();
        rule=rule.toLowerCase();
        for(int i=0;i<rule.length();i++){//puts all the numbers and ops in separate array lists
            if(isNumber(rule,i)){
                rangeend =i;
                rangelength++;
            }else{
                if(rangelength==1&&rule.charAt(rangeend)=='.'){rangelength =0;}//ignores period by itself
                if(rangelength==1&&rule.charAt(rangeend)=='-'){rangelength=0;}//ignores neg by itself as number (checks in op)
                if(2<=rangeend){
                    if('0'<=rule.charAt(rangeend)&&rule.charAt(rangeend)<='9'&&rule.charAt(rangeend-1)=='q'&&rule.charAt(rangeend-2)=='e'){
                        rangelength=0;
                        int number =rule.charAt(rangeend)-'0';
                        rangeend=0;
                        Function eq = Functions.get(number);
                        nums.add(eq);
                    }
                }
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
        //adds nums one last time, doesn't go through the loop on last one so the addition can't be done in the else
        if(rangeend==rule.length()-1&&rule.charAt(rangeend)!='x'){nums.add(new Number(Double.parseDouble(rule.substring(rangeend+1-rangelength,rangeend+1))));}
        //gets from the right 2 from nums and does the operation on the right
        //the next right 2 take the function of the operation and the next number with the next operand
        //should have a split point function(num) function(op of two nums)| function(op)
        Function tree =CreateTree(nums,ops);
        //ArrayList<Function> invnums = (ArrayList<Function>) nums.clone();
        //ArrayList<Function> invops = (ArrayList<Function>) ops.clone();
        //for(int i=0;i<ops.size();i++){
            //Function op = ops.get(i);
            //invops.set(invops.size()-(i+1),invOp((Operation) op));
       //}
        //Function invtree=CreateTree(invnums,invops);
        //InvFunctions.add(invtree);
        //         if(character=='e'&&string.charAt(index+1)=='q'&&'0'<=string.charAt(index+2)&&string.charAt(index+2)<='9'){
        //            int number =string.charAt(index+2)-'0';
        //            Function op = Functions.get(number);
        //            return op;
        //        }
        return tree;
    }
    public Function Optimize(Function tree){//traverse the tree and try to simplify
        //go through until the tree isn't changed if op of 2 numbers it can
        boolean changed=false;
        if(tree.left!=null){
            Optimize(tree.left);
        }if(tree.right!=null){
            Optimize(tree.right);
        }
        return tree;
    }
    public Function CreateTree(ArrayList<Function> nums,ArrayList<Function> ops){//creates a function in tree form from the ops read left to right and nums from the right to left
        for(int i=0;i<ops.size();i++){//shouldn't assume that the function needs a left and right
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
    }//2*x+10 inv -> 2 x * 10 + | nums {2,x,10} ops {*, +}
    //read ops right to left and nums right to left after getting invOp()
    public Operation invOp(Operation a){//for exponents the inverse would be the 1/n of the number that is in the exponent
        if(a.getClass()==Add.class){//for inv func things that happen to x in a list reversed, with the op, {*2,+10} would become {-10,/2}
            return new Sub();
        }else if(a.getClass()==Sub.class){
            return new Add();
        }else if(a.getClass()==Mult.class){
            return new Div();
        }else if(a.getClass()==Div.class){
            return new Mult();
        }
        return new Operation();
    }
    public boolean isOp(String string, int index){
        char a = string.charAt(index);
        if(a=='+'||a=='-'||a=='*'||a=='/'||a=='^'){return true;}
        //for equation reference use eq#
        //check for three from end
        return false;
    }
    public Function GiveOp(String string, int index){//check for ops before numbers are removed
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
        if(2<=index){
            if(string.charAt(index-1)=='q'&&string.charAt(index-2)=='e'){
                return true;
            }
        }
        try{
            String s =""+a;
            Double.parseDouble(s);
        }catch (Exception e){
            if(a=='.'){
                return true;//returns true for decimal spots ie. 5.2
            }               //period by itself wouldn't work
            //if(a=='-'){
                //return true;//returns true for negative numbers
                            //- by itself should be an operation
            //}
            return false;
        }
        return true;
    }
    public Function ParseDouble(String string, int rangelength, int rangeend){
        String numstring = string.substring((rangeend+1)-rangelength,rangeend+1);
        Function num = new Number();//counts as x
        double a= 0.0;
        try {
            a=Double.parseDouble(numstring);
        }catch (Exception e){
            for(int i=0;i<numstring.length();i++){
                //find neg and make anything after it negative
                //if(numstring.charAt(i)=='-'){
                    //parse double after neg and make neg
                    //5-5
                    //0 1 2
                    //5 - 3
                    //after neg 3-2
                    //22-3
                    //1 range end same
                    //beforeneg length-i
                    //length-length-(i)
                    //range end i-1
                    //Function afterneg = ParseDouble(string,rangelength-(i+1),rangeend);
                    //Function beforeneg=new Number(0);
                    //if(0<i){
                        //beforeneg= ParseDouble(string,rangelength-(rangelength-i),i-1);
                    //}
                    //Function neg = new Sub();
                    //neg.left=beforeneg;
                    //neg.right= afterneg;
                    //return neg;
                //}
            }
            //contains a negative before the number
        }
        num=new Number(a);
        return num;
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        g.setColor(Color.GRAY);
        //first point at origin
        g.drawLine((int)(BoardHeight),(int)(((CameraY)*ViewSize)+(BoardHeight/2)),0,(int)(((CameraY)*ViewSize)+(BoardHeight/2)));//horizontal
        g.drawLine((int)((-CameraX*ViewSize)+(BoardWidth/2)),0,(int)((-CameraX*ViewSize)+(BoardHeight/2)),(int)(BoardHeight));//vertical
        if(gridlines){
            g.setColor(new Color(250,250,250,40));//moves with camera as the camera is aligned to the grid of 5 (Make class constant)
            for(int i=0; i<=BoardHeight;i+=5){//starts at 10 and increases by 10 until it reaches the boardheight
                g.drawLine((int)(BoardHeight),(int)((((CameraY))+(BoardHeight/2))+i-CameraY*ViewSize),0,(int)((((CameraY))+(BoardHeight/2))+i-CameraY*ViewSize));//horizontal
            }
            for(int i=-5; -(BoardHeight)<=i;i-=5){
                g.drawLine((int)(BoardHeight),(int)((((CameraY))+(BoardHeight/2))+i-CameraY*ViewSize),0,(int)((((CameraY))+(BoardHeight/2))+i-CameraY*ViewSize));//horizontal
            }
            for(int i=0; i<=BoardWidth;i+=5){
                g.drawLine((int)((-CameraX*ViewSize)+(BoardWidth/2)+i+CameraX*ViewSize),0,(int)((-CameraX*ViewSize)+(BoardHeight/2)+i+CameraX*ViewSize),(int)(BoardHeight));//vertical
            }
            for(int i=-5; -(BoardWidth)<=i;i-=5){
                g.drawLine((int)((-CameraX*ViewSize)+(BoardWidth/2)+i+CameraX*ViewSize),0,(int)((-CameraX*ViewSize)+(BoardHeight/2)+i+CameraX*ViewSize),(int)(BoardHeight));//vertical
            }
        }
        g.setColor(Color.WHITE);
        for (int a = 1; a < BoardWidth; a++) {//goes through each pixel excluding the last to be able to check ahead.
            //check for vertical distance, if it is
            double b=a-(BoardWidth/2);//shifts the pixels from 0- BoardWidth to instead show negative values with 0 in middle
            double i = (double) (b/ViewSize)+CameraX;//(a/ViewSize)+CameraX;//transposes the domain (if zoomed in by 2, divides domain by 2 then adds the camera shift of values)
            double h = (double) ((b-1)/ViewSize)+CameraX;//((a-1)/ViewSize)+CameraX;
            if(1<=Functions.size()) {
                for (Function function : Functions) {
                    double fi = function.PerformOperation(new Input(i));
                    double fh = function.PerformOperation(new Input(h));
                    if (!isNan(fi) && !isNan(fh)) {//to avoid drawing lines in asymptotes, check if it crosses through Nan at any time
                        //if((0<i&&0<h)||(i<0&&h<0)) {
                        //21 22 23
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
                            drawsubsections(g,function,x1,2);
                        }

                        g.drawRect((int) x1, (int) y1, 1, 1);
                        //g.drawLine((int) ((h - CameraX) * ViewSize), (int) ((BoardHeight - test(h) + CameraY) * ViewSize), (int) ((i - CameraX) * ViewSize), (int) ((BoardHeight - test(i) + CameraY) * ViewSize));
                        //}
                        //g.fillRect((int)((i-CameraX)*ViewSize),(int)((BoardHeight-test(i)+CameraY)*ViewSize),1,1);
                    }//g.fillRect((int)((i-CameraX)*ViewSize),(int)((BoardHeight-test2(i)+CameraY)*ViewSize),(int)ViewSize,(int)ViewSize);
                }
            }
        }//
    }//y=x 2 x % -
    public void drawsubsections(Graphics g,Function function,double xstart,double denom){//denom starts at 2 for a 1/2 pixel nad increases by *2 from there
        g.setColor(Color.red);//fence post easier way to improve performance
        for(int n=1;n<denom;n++){
            double b = xstart - (BoardWidth / 2);//shifts the pixels from 0- BoardWidth to instead show negative values with 0 in middle
            double i = (double) (b / ViewSize) + CameraX;//(a/ViewSize)+CameraX;//transposes the domain (if zoomed in by 2, divides domain by 2 then adds the camera shift of values)
            double h = (double) ((b - (n/denom)) / ViewSize) + CameraX;//((a-1)/ViewSize)+CameraX;
            if (1 <= Functions.size()) {
                    double fi = function.PerformOperation(new Input(i));
                    double fh = function.PerformOperation(new Input(h));
                    if (!isNan(fi) && !isNan(fh)) {//to avoid drawing lines in asymptotes, check if it crosses through Nan at any time
                        //if((0<i&&0<h)||(i<0&&h<0)) {
                        //21 22 23
                        double x1;//i & h
                        double y1;//fi & fh
                        double x2;
                        double y2;

                        x1 = doTransformationsX(h);
                        x2 = doTransformationsX(i);
                        y1 = doTransformationsY(fh);
                        y2 = doTransformationsY(fi);
                        //if y1 and y2's difference is greater than one do fractions
                        if (1 < Math.abs(y1 - y2)) {
                            if(denom<=1){
                                try{
                                    drawsubsections(g, function,x1, denom++);
                                }catch (Exception e){
                                    System.out.println(e);
                                    break;
                                }
                            }
                        }

                        g.drawRect((int) x1, (int) y1, 1, 1);
                        //g.drawLine((int) ((h - CameraX) * ViewSize), (int) ((BoardHeight - test(h) + CameraY) * ViewSize), (int) ((i - CameraX) * ViewSize), (int) ((BoardHeight - test(i) + CameraY) * ViewSize));
                        //}
                        //g.fillRect((int)((i-CameraX)*ViewSize),(int)((BoardHeight-test(i)+CameraY)*ViewSize),1,1);
                    }//g.fillRect((int)((i-CameraX)*ViewSize),(int)((BoardHeight-test2(i)+CameraY)*ViewSize),(int)ViewSize,(int)ViewSize);
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
        }if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE){
            Functions.remove(Functions.size()-1);
            //InvFunctions.remove(InvFunctions.size()-1);
        }if(e.getKeyCode()==KeyEvent.VK_G){
            GetAt();
        }
    }
    public void GetAt(){
        Scanner sc = new Scanner(System.in);
        System.out.print("answer to get y value at a certain equation number and what x answer in Equation number,X : ");
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
        System.out.println("\n"+"Eq"+s1+"("+s2+") = "+function.PerformOperation(new Input(Double.parseDouble(s2))));
    }
}
