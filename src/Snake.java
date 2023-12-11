import com.sun.tools.jconsole.JConsolePlugin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class Snake extends JPanel implements ActionListener, KeyListener  {
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

    //food
    Tile food;
    Random rng;

    //game logic
    int velocityX;
    int velocityY;
    Timer gameLoop;

    boolean gameOver = false;

    public Snake(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);
        setVisible(true);
        snakeHead = new Tile(5, 5);
        snakeBody = new ArrayList<Tile>();

        velocityX = 1;
        velocityY = 0;

        //Gameloop();
    }
    public void Gameloop(){
        while (true){
            switch(velocityX){
                case-1:
                    snakeHead = new Tile(snakeHead.x--,snakeHead.y);
                    break;
                case 1:
                    snakeHead = new Tile(snakeHead.x++,snakeHead.y);
                    break;
            }
            switch(velocityY){
                case -1:
                    snakeHead = new Tile(snakeHead.x,snakeHead.y--);
                    break;
                case 1:
                    snakeHead = new Tile(snakeHead.x,snakeHead.y++);
                    break;
            }
            //move snake
            //detect intersection with snake head
            for(Tile body:snakeBody){
                if(Intersection(snakeHead,body)){
                    gameOver=true;
                    return;
                }
            }
            //
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
        //Grid Lines
        for(int i = 0; i < boardWidth/tileSize; i++) {
            //(x1, y1, x2, y2)
            g.drawLine(i*tileSize, 0, i*tileSize, boardHeight);
            g.drawLine(0, i*tileSize, boardWidth, i*tileSize);
        }

        //Food

        //Snake Head
        g.setColor(Color.green);
        // g.fillRect(snakeHead.x, snakeHead.y, tileSize, tileSize);
        // g.fillRect(snakeHead.x*tileSize, snakeHead.y*tileSize, tileSize, tileSize);
        g.fill3DRect(snakeHead.x*tileSize, snakeHead.y*tileSize, tileSize, tileSize, true);

        //Snake Body
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            // g.fillRect(snakePart.x*tileSize, snakePart.y*tileSize, tileSize, tileSize);
            g.fill3DRect(snakePart.x*tileSize, snakePart.y*tileSize, tileSize, tileSize, true);
        }

    }
    @Override
    public void actionPerformed(ActionEvent e) { //called every x milliseconds by gameLoop timer
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getID() == KeyEvent.VK_W){
            velocityY=-1;
            velocityX=0;
        } else if (e.getID()== KeyEvent.VK_A) {
            velocityY=0;
            velocityX=-1;
        } else if (e.getID()== KeyEvent.VK_S) {
            velocityY=1;
            velocityX=0;
        } else if (e.getID()== KeyEvent.VK_D) {
            velocityY=0;
            velocityX=1;
        }
    }

    //not needed
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}