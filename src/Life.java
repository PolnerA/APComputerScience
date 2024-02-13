import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class Life extends JPanel implements ActionListener,KeyListener{

    private static class Tile{
        int x;
        int y;
        Tile(int x, int y){
            this.x=x;
            this.y=y;
        }
    }
    int tilesize=1;
    float ViewSize=1;
    private static class Cell{
        Tile position;
        Tile RelativePos;
        Boolean alive;
        int neighbors;
        Cell(Tile position){
            this.position=position;
            RelativePos=new Tile(position.x, position.y);
            alive=true;
        }

        public void setNeighbors(int neighbors) {
            this.neighbors = neighbors;
        }
    }
    Random rng = new Random();
    int BoardWidth;
    int BoardHeight;
    int BorderX;
    int BorderY;
    Cell[] cells;
    Timer frames;
    public Life(int boardwidth,int boardheight){
        BoardHeight=boardheight;
        BoardWidth=boardwidth;
        BorderX=0;
        BorderY=0;
        setPreferredSize(new Dimension(BoardWidth,BoardHeight));
        setBackground(Color.black);
        setFocusable(true);
        GenerateCells();
        addKeyListener(this);
        frames = new Timer(0,this);
        frames.start();
    }
    public void GenerateCells(){
        cells= new Cell[(BoardWidth/tilesize)*(BoardHeight/tilesize)];
        int index=0;
        for(int i=0;i<(BoardWidth/tilesize);i++){
            for(int j=0;j<(BoardHeight/tilesize);j++){
                cells[index]=new Cell(new Tile(i,j));
                if(rng.nextInt(2)==1){
                    cells[index].alive=false;
                }
                index++;
            }
        }
        CalculateNeighbors();
        updateConditions();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        draw(g);
    }
    public void draw(Graphics g){
        g.setColor(Color.WHITE);
        g.drawRect((int)(BorderX*ViewSize), (int) (BorderY*ViewSize), (int) (BoardWidth*ViewSize), (int) (BoardHeight*ViewSize));
        for (int i = 0; i < cells.length; i++) {
            Cell cell= cells[i];
            if(cell.alive) {
                g.setColor(new Color(0,Math.max(0,255-(cell.neighbors*60)),0+(Math.min(cell.neighbors*60,255))));
                if(0<=cell.RelativePos.x&&0<=cell.RelativePos.y){
                    g.fillRect((int) (Math.max(cell.RelativePos.x * tilesize*ViewSize,1)), (int) (Math.max(cell.RelativePos.y * tilesize*ViewSize,1)), (int) (Math.max(tilesize*ViewSize,1)), (int) (Math.max(1,tilesize*ViewSize)));
                }

            }
        }
    }
    public void CalculateNeighbors(){//N^2 Cell one and two don't need to be looked entirely through
        for (int i = 0; i < cells.length; i++) {//goes through cells 0-10,000 first 0-99 are the first column
            Cell cell = cells[i];              //100-200 is the second column etc... //above -1, below +1 |left -101,-100,-99|right +101,+100,+99
            cell.setNeighbors(0);
            int HeightOfTiles=BoardHeight/tilesize;
            //subtract the lower bound of the board height along with the upper bound for both x and y
            //if on the top or bottom don't check for up or down respectively
            if(0<=(i-1)&&0<cell.position.y){//up and it isn't on the top
                if(cells[i-1].alive){
                    cell.neighbors++;
                }
            }
            if(0<=(i-HeightOfTiles+1)&&cell.position.y<HeightOfTiles){//bottom left
                if(cells[i-HeightOfTiles+1].alive){
                    cell.neighbors++;
                }
            }if(0<=(i-HeightOfTiles)){//left
                if(cells[i-HeightOfTiles].alive){
                    cell.neighbors++;
                }
            }
            if(0<=(i-HeightOfTiles-1)&&0<cell.position.y){//top left
                if(cells[i-HeightOfTiles-1].alive){
                    cell.neighbors++;
                }
            }
            if(i+1<cells.length&&cell.position.y<HeightOfTiles){//down
                if(cells[i+1].alive) {
                    cell.neighbors++;
                }
            }
            if((i+HeightOfTiles-1)< cells.length&&0<cell.position.y){//up right
                if(cells[i+HeightOfTiles-1].alive) {
                    cell.neighbors++;
                }
            }
            if((i+HeightOfTiles)< cells.length){//right
                if(cells[i+HeightOfTiles].alive){
                    cell.neighbors++;
                }
            }
            if((i+HeightOfTiles+1)< cells.length&&cell.position.y<HeightOfTiles){//down right
                if(cells[i+HeightOfTiles+1].alive){
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
        for (int i=0;i< cells.length;i++){
            Cell cell =cells[i];
            if(cell.neighbors<2){
                cell.alive=false;
            }else if(3<cell.neighbors){
                cell.alive=false;
            } else if (cell.neighbors==3) {
                cell.alive=true;
            }
        }
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
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_SHIFT){
            ViewSize*=1.1f;
            Math.round(ViewSize);
        }if(e.getKeyCode()==KeyEvent.VK_CONTROL){
            ViewSize*=0.9f;
            Math.round(ViewSize);
        }if(e.getKeyCode()==KeyEvent.VK_W) {
            for (Cell cell : cells) {
                cell.RelativePos.y+=10;
            }
            BorderY+=tilesize*10;
        }if(e.getKeyCode()==KeyEvent.VK_A) {
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
