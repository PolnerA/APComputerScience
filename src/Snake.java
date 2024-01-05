
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class Snake extends JPanel implements ActionListener, KeyListener {


    private class Tile{
        int x;
        int y;

        Tile(int x, int y){
            this.x=x;
            this.y=y;
        }
    }
    int boardWidth;
    int boardHeight;
    int tileSize = 25;

    //snake
    Tile snakeHead;
    ArrayList<Tile> snakeBody;
    boolean hasmoved=false;
    //food
    Tile food;
    Random rng=new Random();

    //game logic
    public int velocityX=1;
    public int velocityY=0;
    public int Score=0;
    Timer gameLoop;

    boolean gameOver = false;

    public Snake(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);
        snakeHead = new Tile(5, 5);
        snakeBody = new ArrayList<Tile>();
        food= new Tile(5,5);
        gameLoop = new Timer(100,this);
        gameLoop.start();
    }
    public void Apple(){
        food = new Tile(rng.nextInt(boardWidth / tileSize), rng.nextInt(boardHeight) / tileSize);
    }

    public void Move(){
        if(Intersection(snakeHead,food)) {
            Apple();
            snakeBody.add(new Tile( tileSize, tileSize));
            Score++;
        }
        //move snake body
        for (int i = snakeBody.size() - 1; i >= 0; i--) {
            Tile snakePart = snakeBody.get(i);
            if (i == 0) { //right before the head
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            } else {
                Tile prevSnakePart = snakeBody.get(i - 1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }
        //move snake head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        if(snakeHead.x<0||snakeHead.y<0||boardWidth<snakeHead.x||boardHeight< snakeHead.y){
            gameOver=true;
        }
        hasmoved=false;
        //snake body collision
        for(Tile b:snakeBody){
            if(Intersection(snakeHead,b)){
                gameOver=true;
            }
        }
    }
    public boolean Intersection(Tile a, Tile b){
        return (a.x==b.x&&a.y==b.y);
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        if(gameOver) {
            g.drawString("Game Over",boardWidth/2,boardHeight/2);
            return;
        }
        g.setColor(Color.DARK_GRAY);
        //Grid Lines
        for(int i = 0; i < boardWidth/tileSize; i++) {
            //(x1, y1, x2, y2)
            g.drawLine(i*tileSize, 0, i*tileSize, boardHeight);
            g.drawLine(0, i*tileSize, boardWidth, i*tileSize);
        }

        //Food
        g.setColor(Color.red);
        g.fill3DRect(food.x*tileSize,food.y*tileSize,tileSize,tileSize,true);
        //Snake Head
        g.setColor(Color.green);
        // g.fillRect(snakeHead.x, snakeHead.y, tileSize, tileSize);
        // g.fillRect(snakeHead.x*tileSize, snakeHead.y*tileSize, tileSize, tileSize);
        g.fill3DRect(snakeHead.x*tileSize, snakeHead.y*tileSize, tileSize, tileSize, true);

        //Snake Body
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            g.fillRect(snakePart.x*tileSize, snakePart.y*tileSize, tileSize, tileSize);
            g.fill3DRect(snakePart.x*tileSize, snakePart.y*tileSize, tileSize, tileSize, true);
        }

    }
    @Override
    public void actionPerformed(ActionEvent e) { //called every x milliseconds by gameLoop timer
        Move();
        repaint();
        if(gameOver){
            gameLoop.stop();

        }
    }


    @Override
    public void keyPressed(KeyEvent e) {
        //System.out.println("KeyEvent: " + e.getKeyCode());
        //System.out.println(KeyEvent.VK_W);
        if(!hasmoved) {
            if (e.getKeyCode() == KeyEvent.VK_W && velocityY != 1) {//add boolean has moved
                velocityY = -1;
                velocityX = 0;
                hasmoved = true;
            } else if (e.getKeyCode() == KeyEvent.VK_A && velocityX != 1) {
                velocityY = 0;
                velocityX = -1;
                hasmoved = true;
            } else if (e.getKeyCode() == KeyEvent.VK_S && velocityY != -1) {
                velocityY = 1;
                velocityX = 0;
                hasmoved = true;
            } else if (e.getKeyCode() == KeyEvent.VK_D && velocityX != -1) {
                velocityY = 0;
                velocityX = 1;
                hasmoved = true;
            }
        }
        //87 == w
        //65 == a
        //83 == s
        //68 == d
    }

    //not needed
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
