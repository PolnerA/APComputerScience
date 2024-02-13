import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
/*
Name: Adam Polner
Problem: Code Conway's game of life
Pseudocode: make cells with positions and neighbors, and states of alive or dead, calculate the neighbors based
off of the position, then updates the cells based off of the amount of neighbors
Notes:Relative positions for camera movement along with a view-size multiplier
Maintenance log:
Date:        Done:
2/1/24      Started classes for life
2/5/24      Started on game logic
2/6/24      Finished life (checking each cell against every other)
2/8/24      added more efficient neighbor calculation
2/9/24      added camera movement (Left, Right, Up, Down)
2/12/24     add camera zoom
2/13/24     commented the program
 */

public class Life extends JPanel implements ActionListener,KeyListener{

    private static class Tile{//tile is used for the positioning of tiles, has and x and y
        int x;
        int y;
        Tile(int x, int y){
            this.x=x;
            this.y=y;
        }
    }
    int tilesize=1;//tile size can be changed to be the width and height of tiles minimum of 1
    float ViewSize=1;//view-size starts at 1 and increases or decreases to show the zoom
    private static class Cell{
        Tile position;//position stores the position of the tile/cell
        Tile RelativePos;//relative position is used for drawing, unlike position which is used for calculations
        Boolean alive;//tracks the state of the cell, alive or dead
        int neighbors;//tracks the number of neighbors
        Cell(Tile position){//creates the cell with a position, sets the relative pos to the pos, then brings it to life
            this.position=position;
            RelativePos=new Tile(position.x, position.y);
            alive=true;
        }
    }
    Random rng = new Random();//rng for cell generation
    int BoardWidth;//board width and board height for screen borders
    int BoardHeight;
    int BorderX;//tracks the relative border position
    int BorderY;
    Cell[] cells;//array of cells to hold all the cells
    Timer frames;//calls the action preformed to update the frames
    public Life(int boardwidth,int boardheight){
        BoardHeight=boardheight;
        BoardWidth=boardwidth;
        BorderX=0;
        BorderY=0;
        setPreferredSize(new Dimension(BoardWidth,BoardHeight));
        setBackground(Color.black);
        setFocusable(true);//sets up the size of everything
        GenerateCells();//generates cells, starts key input and starts the generations
        addKeyListener(this);
        frames = new Timer(0,this);
        frames.start();
    }
    public void GenerateCells(){
        cells= new Cell[(BoardWidth/tilesize)*(BoardHeight/tilesize)];//makes a list of width * height but in terms of tiles
        int index=0;
        for(int i=0;i<(BoardWidth/tilesize);i++){
            for(int j=0;j<(BoardHeight/tilesize);j++){//ever-increasing index stores the location of the cells in an organized way
                cells[index]=new Cell(new Tile(i,j));
                if(rng.nextInt(2)==1){//50-50 of alive or dead
                    cells[index].alive=false;
                }
                index++;
            }
        }
        CalculateNeighbors();//calculates the neighbors and updates before the first draw
        updateConditions();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        draw(g);
    }
    public void draw(Graphics g){
        g.setColor(Color.WHITE);//sets the color as white and draws the borderlines typecasts to int as view size is a float
        g.drawRect((int)(BorderX*ViewSize), (int) (BorderY*ViewSize), (int) (BoardWidth*ViewSize), (int) (BoardHeight*ViewSize));
        //changes the border x and y from (0,0) with every movement call
        for (int i = 0; i < cells.length; i++) {
            Cell cell= cells[i];//for each cell
            if(cell.alive) {//if it is alive it draws
                g.setColor(new Color(0,Math.max(0,255-(cell.neighbors*60)),0+(Math.min(cell.neighbors*60,255))));//changes the color based on the amount of neighbors more green = more neighbors
                if(0<=cell.RelativePos.x&&0<=cell.RelativePos.y){//if the cell relative to the view is in frame
                    g.fillRect((int) (Math.max(cell.RelativePos.x * tilesize*ViewSize,1)), (int) (Math.max(cell.RelativePos.y * tilesize*ViewSize,1)), (int) (Math.max(tilesize*ViewSize,1)), (int) (Math.max(1,tilesize*ViewSize)));
                }//draws at the relative position, multiplies by the tile size and view-size with a minimum value of 1

            }
        }
    }
    public void CalculateNeighbors(){
        for (int i = 0; i < cells.length; i++) {
            Cell cell = cells[i];//for each cell in the array it
            cell.neighbors=0;//sets the neighbors to 0
            int HeightOfTiles=BoardHeight/tilesize;//position is in terms of th tile size
            //cells contains a list that goes (0,0) [0], (0,1) [1], (0,2) [2]
            //so it is possible to find neighbors without checking the position, ie: up is one less y value so [i-1]
            //left and right are offset by the height of tiles as that is what separates [0,0] & [1,0]
            if(0<cell.position.y){//up neighbor, check if the cell is at the top (no up neighbor)
                if(cells[i-1].alive){//can access i-1 as the negative call with i=0 would be at the top (position.y==0)
                    cell.neighbors++;//increments the neighbor as it exists above
                }
            }
            if(0<=(i-HeightOfTiles+1)&&cell.position.y<HeightOfTiles){//bottom left neighbor.
                if(cells[i-HeightOfTiles+1].alive){//check if the index is good,
                    cell.neighbors++;//checks bound of the cells position (not at bottom)
                }
            }if(0<=(i-HeightOfTiles)){//checks left
                if(cells[i-HeightOfTiles].alive){//if left is alive increments neighbors
                    cell.neighbors++;
                }
            }
            if(0<=(i-HeightOfTiles-1)&&0<cell.position.y){//top left
                if(cells[i-HeightOfTiles-1].alive){//same as before but checks top left
                    cell.neighbors++;
                }
            }
            if(i+1<cells.length&&cell.position.y<HeightOfTiles){//down
                if(cells[i+1].alive) {//checks down
                    cell.neighbors++;
                }
            }
            if((i+HeightOfTiles-1)< cells.length&&0<cell.position.y){//up right
                if(cells[i+HeightOfTiles-1].alive) {//checks up, right
                    cell.neighbors++;
                }
            }
            if((i+HeightOfTiles)< cells.length){//right
                if(cells[i+HeightOfTiles].alive){//checks right
                    cell.neighbors++;
                }
            }
            if((i+HeightOfTiles+1)< cells.length&&cell.position.y<HeightOfTiles){//down right
                if(cells[i+HeightOfTiles+1].alive){//checks down right
                    cell.neighbors++;
                }
            }
        }
    }
    public void updateConditions(){
        /*
        rules:
        Any live cell with fewer than two live neighbors dies, as if by underpopulation.
        Any live cell with two or three live neighbors lives on to the next generation.
        Any live cell with more than three live neighbors dies, as if by overpopulation.
        Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
         */
        for (int i=0;i< cells.length;i++){//for each cell checks its neighbors
            Cell cell =cells[i];
            if(cell.neighbors<2){
                cell.alive=false;
            }else if(3<cell.neighbors){//if less than 2 it dies, if greater than 3 it dies,
                cell.alive=false;
            } else if (cell.neighbors==3) {//if exactly three it turns from dead to alive or alive to alive,
                cell.alive=true;
            }//2 neighbors leaves it the same as it wouldn't qualify for any of the conditions
        }    //(dead stays dead alive stays alive)
    }

    @Override
    public void actionPerformed(ActionEvent e) { //called every x milliseconds by gameLoop timer
        CalculateNeighbors();
        //get neighbors
        updateConditions();
        //update the board
        repaint();
        //repaint

    }
    @Override
    public void keyTyped(KeyEvent e) {
    }//nothing happens if a key is typed or released, action is on press

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_E){
            ViewSize*=1.1f;//multiplies the view-size making things bigger
        }if(e.getKeyCode()==KeyEvent.VK_Q){
            ViewSize*=0.9f;//divides the view-size making things smaller
        }if(e.getKeyCode()==KeyEvent.VK_W) {
            for (Cell cell : cells) {//if w is pressed every cell's relative y position is increased by 10
                cell.RelativePos.y+=10;
            }
            BorderY+=tilesize*10;//the border is also multiplied by the corresponding number (10) and the tile size
        }if(e.getKeyCode()==KeyEvent.VK_A) {//does the same for all WASD keys
            for (Cell cell : cells) {
                cell.RelativePos.x+=10;
            }
            BorderX+=tilesize*10;
        }if(e.getKeyCode()==KeyEvent.VK_S) {
            for (Cell cell : cells) {
                cell.RelativePos.y-=10;
            }
            BorderY-=tilesize*10;
        }if(e.getKeyCode()==KeyEvent.VK_D) {
            for (Cell cell : cells) {
                cell.RelativePos.x-=10;
            }
            BorderX-=tilesize*10;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
