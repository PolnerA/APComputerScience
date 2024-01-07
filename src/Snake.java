/*
Name: Adam Polner
Problem:Create a game which lets the user play a game of snake
Pseudocode: creates a snake, snake head moves to the button that is pressed (WASD)
and the apple gets placed randomly, if apple is eaten it adds to snake body which moves where the previous part was,
unless it moves to where the head was, in every move it checks the head against the borders if it goes out game over,
same with the intersection between snake head and the snake body game over
Notes:
Maintenance log:
Date:     Done:
12/7/2023   Started figuring out swing and graphics
12/8/2023   started adding graphics and game logic
12/12/2023  added movement to snake
12/13/2023  added body movement to snake
1/3/2024    game over text and apple spawning
1/5/2024    added has moved to allow for only one move per frame, and score
1/8/2024    added retry option

*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class Snake extends JPanel implements ActionListener, KeyListener {
    int tileSize = 25;

    //tile class allows to program in terms of the snake tiles instead of pixels | just multiply by tilesize for pixels
    private static class Tile{
        int x;
        int y;
        Tile(int x, int y){
            this.x=x;
            this.y=y;
        }
    }
    //keeps board width and height for the window
    int boardWidth;
    int boardHeight;

    //snake head is set to a tile, and an arrayList of tiles for the body
    Tile snakeHead;
    ArrayList<Tile> snakeBody;
    //bool for making sure you only move once per frame
    boolean hasmoved=false;
    //food is kept as a tile
    Tile food;
    //random number generation for the food
    Random rng=new Random();

    //game logic
    //snakes movement
    public int velocityX=1;
    public int velocityY=0;
    //game loop timer calls event every X milliseconds
    Timer gameLoop;
    //game state whether to keep calling the game loop
    boolean gameOver = false;

    public Snake(int boardWidth, int boardHeight) {
        //stores board width and height that it got from App.java
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);
        //adds key listener for inputs and allows the focus on the snake
        addKeyListener(this);
        setFocusable(true);
        //sets the snake head at (5,5) and the snakeBody at a new empty list
        snakeHead = new Tile(5, 5);
        snakeBody = new ArrayList<>();
        food= new Tile(15, 5);//sets the food to (15,5)
        //gameLoop calls event every 100 milliseconds
        gameLoop = new Timer(100,this);
        gameLoop.start();
    }
    public void Apple(){
        //gets a random apple location for what tile it is on
        food.x=rng.nextInt(boardWidth / tileSize);
        //because of the height being different it can go on a tile +1 to where your snake can go, this
        //-1 counteracts it, but it can make it -1, so it is changed to if <0 set to 0
        food.y=rng.nextInt(boardHeight / tileSize)-1;
        if(food.y<0){
            food.y=0;
        }
        for (Tile body: snakeBody) {
            if(Intersection(body,food)){
                Apple();//if it is on the same tile as the body generates at a new random place
                //bad practice as it would happen more and more the larger the body gets, but for snake
                //there aren't too many tiles to make it too bad.
            }
        }
    }

    public void Move(){
        //detects if the head is where the food is if so, changes the food and adds to the body
        if(Intersection(snakeHead,food)) {
            snakeBody.add(new Tile(food.x, food.y));
            Apple();
        }
        //move snake body
        //for each index of snake body starting at the back going to teh front
        for (int i = snakeBody.size() - 1; i >= 0; i--) {
            Tile snakePart = snakeBody.get(i);
            if (i == 0) { //moves to head
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            } else {//moves to the previous indexed snake part (can't be done for 0 (as snake body at -1 is the head))
                Tile prevSnakePart = snakeBody.get(i - 1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }
        //move snake head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;
        //if it moves out of the border
        if(snakeHead.x<0||snakeHead.y<0
                ||boardWidth-tileSize<snakeHead.x*tileSize
                ||boardHeight-tileSize< snakeHead.y*tileSize){
            gameOver=true;
        }
        //as move is completed sets the all clear for the next move
        hasmoved=false;
        //snake body collision
        for(Tile b:snakeBody){//for each of the snake body parts if it intersects with the head, gameOver = true
            if(Intersection(snakeHead,b)){
                gameOver=true;
            }
        }
    }
    public boolean Intersection(Tile a, Tile b){//returns if the two tiles are at the same place
        return (a.x==b.x&&a.y==b.y);
    }
    public void paintComponent(Graphics g) {//overrides other paintComponent in component, uses the graphics in its own
        //draw function and supers the previous paint Component in JComponent.java (javax.swing)
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        //sets the color of the text to white
        g.setColor(Color.WHITE);
        g.drawString("Score: "+snakeBody.size(),20,20);
        if(gameOver) {
            g.drawString("Game Over, press enter to try again",boardWidth/2,boardHeight/2);
            return;//returns as all that needs to be written is the score and game over (snake doesn't move)
        }
        //sets the color to dark gray and draws the grid lines
        g.setColor(Color.DARK_GRAY);
        //Grid Lines
        for(int i = 0; i < boardWidth/tileSize; i++) {
            g.drawLine(i*tileSize, 0, i*tileSize, boardHeight);
            g.drawLine(0, i*tileSize, boardWidth, i*tileSize);
        }

        //Food
        g.setColor(Color.red);
        //3D rect is used to make it raised, so it shades the edge giving it a more distinct look
        g.fill3DRect(food.x*tileSize,food.y*tileSize,tileSize,tileSize,true);

        //Snake Head
        g.setColor(Color.green);
        g.fill3DRect(snakeHead.x*tileSize, snakeHead.y*tileSize, tileSize, tileSize, true);

        //Snake Body
        for (Tile snakePart:snakeBody) {
            g.fill3DRect(snakePart.x*tileSize, snakePart.y*tileSize, tileSize, tileSize, true);
        }

    }
    @Override
    public void actionPerformed(ActionEvent e) { //called every x milliseconds by gameLoop timer
        Move();//moves, repaints (paint component is called again)
        repaint();
        if(gameOver){//if the game is done stops the timer
            gameLoop.stop();

        }
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if(!hasmoved) {//if it hasn't moved already in this frame it allows it to move
            if (e.getKeyCode() == KeyEvent.VK_W && velocityY != 1) {
                velocityY = -1;//up when w is pressed
                velocityX = 0;
                hasmoved = true;
            } else if (e.getKeyCode() == KeyEvent.VK_A && velocityX != 1) {
                velocityY = 0;//left when a is pressed
                velocityX = -1;
                hasmoved = true;
            } else if (e.getKeyCode() == KeyEvent.VK_S && velocityY != -1) {
                velocityY = 1;//down when s is pressed
                velocityX = 0;
                hasmoved = true;
            } else if (e.getKeyCode() == KeyEvent.VK_D && velocityX != -1) {
                velocityY = 0;// right when d is pressed
                velocityX = 1;
                hasmoved = true;
            }
        }
        if(gameOver){
            if(e.getKeyCode()== KeyEvent.VK_ENTER){//if the game is over and enter is pressed it restarts everything
                gameLoop.start();
                snakeHead = new Tile(5, 5);
                snakeBody = new ArrayList<>();
                food= new Tile(15, 5);
                velocityX=1;
                velocityY=0;
                gameOver=false;
            }
        }
    }

    //not needed but still needs to be overridden for implementing key listener
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
