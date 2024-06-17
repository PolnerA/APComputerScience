/*
Name: Adam Polner
Problem:Create a graphing calculator that can graph a given function given by the user
Pseudo-code: user types in equation in rpn and the program takes it in and creates a tree of operations that happen,
the graphing goes through each pixel running it through the equation. if there is a gap of greater than 1 pixel in the
y direction it splits the current pixel distance in the x into halves then thirds
(etc... increasing by 1 in the denominator) until it drops the gap below 1 pixel then it prints out all the  x,y values
to keep the graph looking smooth and continuous.
Notes: Previous equations wouldn't count as operations instead as an already defined value, so treat it like a number
Maintenance log:
as the project was started back in february the maintenance log is merely a summary of big steps in the program
other smaller work was done in between the given dates
Date:       Done:
2/29/2024   added test functions as java methods and got camera movement
3/04/2024   added zoom to the window and changed the test methods to return doubles
3/05/2024   started work with asymptotes and how other exceptions like sqrt(neg) would work
3/12/2024   centered the camera
3/19/2024   made the tree for the user inputs
3/25/2024   started the user input collection
4/02/2024   started composite functions
4/05/2024   started negative numbers
4/18/2024   remade the way the program gets user input
4/30/2024   started trying to get derivatives to work with the program
5/07/2024   tried to get all equations in a standard form for derivatives
5/21/2024   tried to get all equations in a standard form for inverses
6/06/2024   added trigonometric functions
6/11/2024   simplified the program removing anything incomplete (derivatives & inverses)
6/16/2024   added more comments to document what the program does
 */
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

public class Graphing extends JPanel implements ActionListener, KeyListener {
    //things for the program to know the window size
    int BoardWidth;
    int BoardHeight;
    //timer to keep calling draw to draw the graph
    Timer frames;
    float ViewSize=50f;
    //Function is the current highlighted function, function at is the x coordinate, and the step size is what
    //the x changes by in the highlighted graph
    int Function = 0;
    double FunctionAt=0.0;
    double StepSize=0.1;
    //camera x & y are the camera offsets from panning the camera
    int CameraX=0;
    int CameraY=0;
    public static class Function{//acts as a tree, recursively has smaller functions within it, useful for user input
        Function left;
        Function right;
        //left and right sides to the function help evaluate the two arguments a function has

        //sine and cosine aren't operations as they only take one argument, so they act like wrappers for the function
        //if a number or op has a sine in it, it takes what it would return and returns the sine or cosine of it instead
        boolean Sine =false;
        boolean Cosine = false;
        public void SetSine(){
            Sine=true;
        }public void SetCosine(){
            Cosine=true;
        }
        //check trig is called after every functions return to check if you need to return a trig value of the value
        //you would be returning
        public double CheckTrig(double toReturn){
            if(Sine){
                return Math.sin(toReturn);
            }
            if(Cosine){
                return Math.cos(toReturn);
            }
            return toReturn;
        }
        @Override
        //equals check if each of the classes check out recursively
        //helps distinguish the equality of two trees, useful if I want to continue this project
        public boolean equals(Object o) {
            Function other = (Function) o;
            if(other.getClass()==this.getClass()){
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
            }
            return false;
        }
        //Perform operation performs the operations recursively throughout the tree to get a result
        //Perform operation in the Function class isn't actually used for anything only for overrides
        //so the method below would just recurse infinitely as if you access it you did something wrong.
        public double PerformOperation(double input){
            if(left!=null){
                double l=left.PerformOperation(input);
            }if(right!=null) {
                double r = right.PerformOperation(input);
            }
            return PerformOperation(input);
        }
    }
    public class Operation extends Function {//empty class used to distinguish inheritance relationships between
                                             //an operator and a number
        public double PerformOperation(double input){//if you inherit from operation you override this with the given op.
            return 0.0;
        }
    }
    public class Add extends Operation{
        //returns the addition of the left and right values
        //     +
        //    / \    would return 15
        //   5  10
        public double PerformOperation(double input){
            return CheckTrig(left.PerformOperation(input) + right.PerformOperation(input));
        }
    }
    public class Sub extends Operation{
        //returns the subtraction of the left and right values
        //     -
        //    / \    would return -5
        //   5  10
        public double PerformOperation(double input){
            return CheckTrig(left.PerformOperation(input)-right.PerformOperation(input));
        }
    }
    public class Mult extends Operation{
        //returns the multiplication of the left and right values
        //     *
        //    / \    would return 50
        //   5  10
        public double PerformOperation(double input){
            return CheckTrig(left.PerformOperation(input)*right.PerformOperation(input));
        }
    }
    public class Div extends Operation{
        //returns the division of the left and right values
        //     /
        //    / \    would return 0.5
        //   5  10
        public double PerformOperation(double input) {
            return CheckTrig(left.PerformOperation(input)/right.PerformOperation(input));
        }
    }
    public class Exp extends Operation{
        //returns the exponentiation of the left and right values
        //     ^
        //    / \    would return 9,765,625
        //   5  10
        public double PerformOperation(double input) {
            double numright = right.PerformOperation(input);
            return CheckTrig(Math.pow(left.PerformOperation(input),numright));
        }
    }
    public class Number extends Function{
        //number class has a number that it returns if it is asked to,
        //or it returns double input if it is an entrance (x, variable for whatever input is placed in)
        //numbers value if constant
        double number;
        //boolean entrance to indicate x in the tree
        boolean entrance=false;

        //if you construct a number with a value it keeps that value
        public Number(double a){
            number=a;
        }
        //if it is a variable it is flagged to return the input instead of the value of nothing to make a variable
        //is a blank Number()
        public Number(){
            entrance=true;
        }
        //if it is x it returns the input, otherwise it returns the number
        public double PerformOperation(double input){
            if(entrance){return CheckTrig(input);}
            return CheckTrig(number);
        }
        @Override
        //on top of checking if the other classes match equals goes through the number values checking if they are equal
        //and if their variables are in the same place
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

    }
    //Functions store all the current functions in the program
    //the corresponding values helps store some of the values in them
    //so it doesn't keep calculating the same thing
    public ArrayList<Function> Functions = new ArrayList<>();
    public ArrayList<HashMap<Double, Double>> values = new ArrayList<>();
    public Graphing(int Boardwidth, int Boardheight){
        BoardWidth=Boardwidth;
        BoardHeight=Boardheight;
        setPreferredSize(new Dimension(BoardWidth, BoardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        //on start sets up the board, makes the background black and starts recording inputs with KeyListener
        Dimension d =getPreferredSize();
        setFocusable(true);
        frames = new Timer(0,this);
        frames.start();
        //frames keeps redrawing the scene as much as it can
    }
    public boolean isOperation(Function input){
        Class<?> a = input.getClass();//checks if a function of a random class is an operation or not
        if(input.left!=null||input.right!=null){
            return false;//if it is a previous equation
            //it would have a left or right, unless it is just a number
            //meaning past equations won't count as operations having their
            //left and right branches removed
            //if it is a number it would return false anyway making a previous equation a set number
        }
        if(a == Mult.class){
            return true;
        }if(a == Div.class){
            return true;
        }if(a == Sub.class){
            return true;
        }if(a == Add.class){
            return true;
        }if(a == Exp.class){
            return true;
        }
        return false;
    }

    public void AddFunctionRPN(){
        //gets a given line of rpn equation and adds it to the functions after getting the function from it
        Scanner sc = new Scanner(System.in);
        System.out.println("Equation "+Functions.size()+" write out equation in rpn, x counts as a variable");
        System.out.println("Separate the operations from the numbers with a space (variable is a number)");
        System.out.print("y=");
        String rule =sc.nextLine();
        Functions.add(parseFunction(rule));
        values.add(new HashMap<>());
    }
    public Function parseFunction(String rule){
        //tree list of the functions within the rpn to later construct the tree
        ArrayList<Function> treelist = new ArrayList<>();
        int NumberFrom = -1;
        int NumberTo = -1;
        //the two identifiers if a number is found it gets from where to where
        //standardises the input to lowercase and adds a space (after each space is when the number is evaluated)
        rule=rule.toLowerCase()+' ';
        for(int i=0;i<rule.length();i++){
            //goes through the string input it got
            if(rule.charAt(i)==' '){
                //if it reaches a space, and it has a number for number from
                if(NumberFrom!=-1){
                    Function num= new Function();
                    //it creates a placeholder num
                    if(NumberTo!=-1){
                        //if it has a number to, it takes the number from the part of the string
                        //and assigns it as a function
                        num = ParseDouble(rule,NumberFrom,NumberTo);
                    }else{
                        //if there isn't a number to that means it is only one character
                        if(rule.charAt(NumberFrom)!='-') {//if it isn't a single negative (subtraction operator)
                            if(rule.charAt(NumberFrom)=='x'){//if it is x it constructs a variable
                                num = new Number();
                            }else if(rule.charAt(NumberFrom)=='p'){//if it is p it takes it as pi
                                num = new Number(Math.PI);
                            }else{//otherwise it finds the single digit number
                                num = ParseDouble(rule,NumberFrom,NumberFrom);
                            }
                        }
                    }
                    if(NumberFrom!=0){
                        //if the number doesn't start at the beginning it checks for a sine and cosine before it
                        if(rule.charAt(NumberFrom-1)=='s'){
                            num.SetSine();
                        }else if(rule.charAt(NumberFrom-1)=='c'){
                            num.SetCosine();
                        }
                    }
                    //it adds the final number to the rpn statement with actual functions
                    treelist.add(num);
                }
                NumberFrom=-1;//resets number if character is a space
                NumberTo=-1;
            }
            //changes NumberFrom if it hasn't seen a number yet, otherwise it assumes it
            //is the last number it sees to put it in NumberTo overridden if it sees another number after it
            if(rule.charAt(i)=='.'||rule.charAt(i)=='-'||isNumber(rule,i)){
                if(NumberFrom==-1){
                    NumberFrom=i;
                }else{
                    NumberTo=i;
                }
            }
            if(isOp(rule,i)){//checks if the current character is an operator
                if(NumberTo==-1){//checks if it isn't in the middle of a number calculation
                    if(!isNumber(rule,i)){//if it isn't also a number
                        // it takes the operator and adds to the rpn statement
                        // number has to be checked because of - being a negative and subtraction
                        treelist.add(GiveOp(rule,i));
                    }
                }
            }
        }
        //constructs the tree from the list and returns the final function
        return CreateTree(treelist);

    }

    public Function CreateTree(ArrayList<Function> treelist){
        //gets an rpn statement with the functions to construct into a recursive tree
        ArrayList<Function> nums = new ArrayList<>();
        Function tree;
        for(int i=0;i<treelist.size();i++){
            //goes through the statement if it hits an operation it takes the previous two numbers and puts it into the
            //left and right otherwise it adds the node to the numbers list
            //example: 5 x + adds 5 and x to nums then as it hits the + it sets the left and right to 5 and x to get
            //    +
            //   / \
            //  5   x
            Function node = treelist.get(i);
            if(isOperation(node)){
                if(2==nums.size()){
                    node.left = nums.remove(0);
                    node.right= nums.remove(0);
                }
                nums.add(node);
            }else{
                nums.add(node);
            }
        }
        //the final tree should be at the first position of nums as it keeps building on itself
        tree=nums.get(0);
        return tree;
    }
    public boolean isOp(String string, int index){
        //is Op checks if a given index in a string is an operator
        //returns true if it is a symbol of an operation
        //note: isOp returns true for -num as neg is the same symbol as subtraction
        //to find if it is an op make sure it isn't a number
        char a = string.charAt(index);
        if(a=='+'||a=='-'||a=='*'||a=='/'||a=='^'){return true;}
        return false;
    }
    public Function GiveOp(String string, int index){
        //gives the operator at a given index
        char character = string.charAt(index);
        //takes the character and creates the corresponding operation
        //if it isn't the first thing in the string it checks the previous space
        //for a trigonometric symbol, if there is one it makes the function return a sin or cos value
        if(character=='+'){
            Operation op = new Add();
            if(index!=0){
                if(string.charAt(index-1)=='s'){
                    op.Sine=true;
                }else if(string.charAt(index-1)=='c'){
                    op.Cosine=true;
                }
            }
            return op;

        }if(character=='*'){
            Operation op = new Mult();
            if(index!=0){
                if(string.charAt(index-1)=='s'){
                    op.Sine=true;
                }else if(string.charAt(index-1)=='c'){
                    op.Cosine=true;
                }
            }
            return op;
        }if(character=='^'){
            Operation op = new Exp();
            if(index!=0){
                if(string.charAt(index-1)=='s'){
                    op.Sine=true;
                }else if(string.charAt(index-1)=='c'){
                    op.Cosine=true;
                }
            }
            return op;
        }if(character=='/') {
            Operation op = new Div();
            if(index!=0){
                if(string.charAt(index-1)=='s'){
                    op.Sine=true;
                }else if(string.charAt(index-1)=='c'){
                    op.Cosine=true;
                }
            }
            return op;
        }
        Operation op = new Sub();
        if(index!=0){
            if(string.charAt(index-1)=='s'){
                op.Sine=true;
            }else if(string.charAt(index-1)=='c'){
                op.Cosine=true;
            }
        }
        return op;

    }
    public boolean isNumber(String string,int index){
        //checks if a character at a given index in a string is a number
        char a = string.charAt(index);
        boolean isNumber ='0'<=string.charAt(index)&&string.charAt(index)<='9';
        //if the number is between 0-9 or a single period it counts as a number
        if(isNumber||string.charAt(index)=='.'){
            return true;
        }else{
            //otherwise it determines some edge cases
            if(string.charAt(index)=='-'){
                //if it is at the end we know it isn't a negative symbol in front of a number
                if(index==string.length()-1){
                    return false;
                }
                //for a negative number if it is before a number it counts as negative
                if(isNumber(string,index+1)){
                    return true;
                }
            }
            //parts of eq count as a number
            //so you can refer to previous equations
            if(string.charAt(index)=='e'){
                return true;
            }
            if(string.charAt(index)=='q'){
                return true;
            }
            //x counts as a number
            if(string.charAt(index)=='x'){
                return true;
            }if(string.charAt(index)=='p'){
                //p counts as pi
                return true;
            }
            //if it isn't an edge case it isn't a number
            return false;
        }
    }
    public Function ParseDouble(String string, int rangestart, int rangeend){
        //shortens the string to the double it wants to get
        String numstring = string.substring(rangestart,rangeend+1);
        Function num = new Number();
        double a= 0.0;
        try {
            a=Double.parseDouble(numstring);
            //it parses the double in the string if it can
        }catch (Exception e){
            //if it can't that means there are special symbols
            if(rangeend-rangestart==2){
                //if there are exactly three characters
                if(string.charAt(rangestart)=='e'&&string.charAt(rangestart+1)=='q'){
                    char eqnum =string.charAt(rangestart+2);
                    if('0'<=eqnum&&eqnum<='9'){
                        //and if the charactars are e then q then a number between 0 and 9
                        //it gets the equation of the number at the end
                        return Functions.get(Integer.parseInt(eqnum+""));
                        //parse int only takes a string so character eqnum needs an empty string concatenation
                    }
                }
            }
        }
        num=new Number(a);//a . (period) by itself would create the number 0.0 as a is automatically 0.0
        return num;
    }
    //draw function for swing paintComponent allows the different elements to be drawn to the window
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        //sets the color for the axis to gray
        g.setColor(Color.GRAY);
        //draws the two axis lines
        g.drawLine((int)(BoardHeight),(int)(((CameraY)*ViewSize)+(BoardHeight/2)),0,(int)(((CameraY)*ViewSize)+(BoardHeight/2)));//horizontal
        g.drawLine((int)((-CameraX*ViewSize)+(BoardWidth/2)),0,(int)((-CameraX*ViewSize)+(BoardHeight/2)),(int)(BoardHeight));//vertical
        for (int a = 1; a <= BoardWidth; a++) {//goes through each pixel excluding the last to be able to check ahead.
            //check for vertical distance, with the ahead one if it is greater than one pixel that means that there is
            //a visible gap so calculate sub-pixels

            //shifts the pixels from 0- BoardWidth to instead show negative values with 0 in middle
            double b=((double) a)-(BoardWidth/2);
            //transposes the domain (if zoomed in by 2, divides domain by 2 then adds the camera shift of values)
            double i = (double) (b/ViewSize)+CameraX;
            double h = (double) ((b-1)/ViewSize)+CameraX;
            //same thing as i except one value behind
            if(1<=Functions.size()) {//if there is at least one function it highlights the given value of it red
                if(Function<Functions.size()) {
                    g.setColor(Color.RED);
                    double displayX = Math.round(FunctionAt * 1000) / (double) 1000;
                    double displayY = Math.round(Functions.get(Function).PerformOperation((FunctionAt)) * 1000) / (double) 1000;
                    g.drawString("eq" + Function + "  (" + displayX + "," + displayY + ")", 0, 10);
                    g.drawRect((int) doTransformationsX(FunctionAt)-3, (int) doTransformationsY(Functions.get(Function).PerformOperation(FunctionAt))-3 , 6, 6);
                }
                //draws the graphs in white
                g.setColor(Color.WHITE);
                for (int k=0;k<Functions.size();k++) {
                    //goes through the functions and gets their corresponding hashmap of x-y pairs
                    HashMap XYPairs = values.get(k);
                    Function function = Functions.get(k);
                    double fi;
                    double fh;
                    //calculates the inputs of i and h with the given function
                    //however if a value is already stored it uses that, otherwise it calculates the value
                    //and stores the value
                    if(!XYPairs.containsKey(i)){
                        fi = function.PerformOperation(i);
                        XYPairs.put(i,fi);
                    }else{
                        fi= (double) XYPairs.get(i);
                    }
                    if (!XYPairs.containsKey(h)) {
                        fh = function.PerformOperation(h);
                        XYPairs.put(h, fh);
                    }else{
                        fh=(double) XYPairs.get(h);
                    }
                    //to avoid drawing lines in asymptotes, check if it crosses through Nan at any time as Nan is what
                    //is returned for a square root of a negative or division by zero
                    if (!isNan(fi) && !isNan(fh)) {
                        double x1;//i & h
                        double y1;//fi & fh
                        double x2;
                        double y2;
                        //goes through the transformations to know where to actually draw the given
                        //xy pairs
                        x1=doTransformationsX(h);
                        x2=doTransformationsX(i);
                        y1=doTransformationsY(fh);
                        y2=doTransformationsY(fi);
                        //if y1 and y2's difference is greater than one do fractions
                        int denom=1;
                        //sets the denominator to one
                        while(1<Math.abs(y1-y2)){
                            //if it has a view difference of more than one pixel vertically it calculates sub pixels
                            denom++;//increases the denominator while it doesn't fill the gap
                            if(200<denom){//at a given point it just accepts that there will be gaps in the function
                                //to draw it with gaps instead of just calculating for a long time
                                break;
                            }
                            //gets the point even closer split into the denominator (starts halves then 3rds etc...)
                            double h2 = ((double) (b-((double)1/denom))/ViewSize) +CameraX;
                            //same calculation of y as before
                            double fh2;
                            if(!XYPairs.containsKey(h2)){
                                fh2= function.PerformOperation(h2);
                                XYPairs.put(h2,fh2);
                            }else{
                                fh2= (double) XYPairs.get(h2);
                            }
                            //it then changes the y value to the one that is now slightly closer
                            y1=doTransformationsY(fh2);
                        }
                        //once it finally finds the
                        //split that it doesn't have a gap
                        //it goes through the entirety of the split
                        //doing the same y calculation to drawing
                        for(int j=0;j<=denom;j++){
                            double h2=(double) ((b-(((double) denom-j)/denom))/ViewSize)+CameraX;
                            double fh2;
                            if(!XYPairs.containsKey(h2)){
                                fh2= function.PerformOperation(h2);
                                XYPairs.put(h2,fh2);
                            }else{
                                fh2= (double) XYPairs.get(h2);
                            }
                            x2=doTransformationsX(h2);
                            y2=doTransformationsY(fh2);
                            g.drawRect((int)x2,(int)y2,1,1);
                        }
                        //if it doesn't have to split it just draws the first (x,y) pair
                        g.drawRect((int) x1, (int) y1, 1, 1);
                    }
                }
            }
        }
    }
    public double doTransformationsX(double X){//takes the x that is put into the function
        //and adjusts it for the camera offset, the ViewSize magnification
        //and the centering of the point
        X-=CameraX;
        X*=ViewSize;
        X+= (double) BoardWidth /2;
        return X;
    }
    public double doTransformationsY(double Y){
        //does the same thing for y as with x except for drawing it changes the y value
        //it changes it to BoardHeight-y as y=0 is at the top of the screen
        Y-=CameraY;
        Y*=ViewSize;
        Y+= (double) BoardHeight /2;
        Y=BoardHeight-Y;
        return Y;
    }
    public boolean isNan(double v){
        return (v!=v);//Nan isn't equal to itself
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();//repaints because of the timer that keeps calling
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //e and q zoom in and out
        if(e.getKeyCode()==KeyEvent.VK_E){
            ViewSize*=1.1f;//multiplies the view-size making things bigger
        }if(e.getKeyCode()==KeyEvent.VK_Q){
            if(0.5f<=ViewSize) {
                ViewSize *= 0.9f;//divides the view-size making things smaller
            }
        }
        //wasd pans the camera around
        if(e.getKeyCode()==KeyEvent.VK_W) {
            CameraY+=1;
        }if(e.getKeyCode()==KeyEvent.VK_A) {
            CameraX-=1;
        }if(e.getKeyCode()==KeyEvent.VK_S) {
            CameraY-=1;
        }if(e.getKeyCode()==KeyEvent.VK_D) {
            CameraX+=1;
        }
        //left and right adjust the ordered pair given by the selected function
        //up changes the selected function by cycling through the current functions
        if(e.getKeyCode()==KeyEvent.VK_RIGHT){
            FunctionAt+=StepSize;
        }if(e.getKeyCode()==KeyEvent.VK_LEFT){
            FunctionAt-=StepSize;
        }if(e.getKeyCode()==KeyEvent.VK_UP){
            Function++;
            if(Functions.size()<=Function){
                Function=0;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //if r is pressed it adds an equation in rpn
        if(e.getKeyCode()==KeyEvent.VK_R) {
            AddFunctionRPN();
        }
        //if backspace is pressed it removes the latest function that was just added
        if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE){
            values.remove(Functions.size()-1);
            Functions.remove(Functions.size()-1);
        }
        //g can be used to get the y for a function at a given x
        if(e.getKeyCode()==KeyEvent.VK_G){
            GetAt();
        }
        //1 can be used to adjust the step size (s already used for wasd)
        if(e.getKeyCode()==KeyEvent.VK_1){
            System.out.println("Set step size to what?");
            Scanner sc = new Scanner(System.in);
            StepSize = Double.parseDouble(sc.nextLine());
        }
    }
    public void GetAt(){
        //gets the y value at a certain equation number at the input x
        Scanner sc = new Scanner(System.in);
        System.out.println("answer to get y value at a certain equation number and what x answer in Equation number,X.");
        System.out.print("another example use equation one, at x=50, would be 1,50\n:");
        String input = sc.next();
        String s1="0";
        String s2="0";
        //gets the input and splits it at the comma
        for(int i=0;i<input.length();i++){
            if(input.charAt(i)==','){
                s1 = input.substring(0,i);
                s2 = input.substring(i+1);
            }
        }
        //gets the function with the integer in the first split
        //and takes the value of a function at a give number found in s2
        Function function = Functions.get(Integer.parseInt(s1));
        double x=0.0;
        try {
            x=Double.parseDouble(s2);
        }catch (Exception e){
            //allows for the input of p as pi as well for trig functions
            if(s2.charAt(0)=='p'){
                x=Math.PI;
            }
        }
        System.out.println("\n"+"Eq"+s1+"("+x+") = "+function.PerformOperation(x));
    }
}
