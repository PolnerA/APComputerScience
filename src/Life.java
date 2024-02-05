import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

/*
rules:
Any live cell with fewer than two live neighbors dies, as if by underpopulation.
Any live cell with two or three live neighbors lives on to the next generation.
Any live cell with more than three live neighbors dies, as if by overpopulation.
Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
 */
public class Life extends JPanel implements ActionListener{
    private static class Tile{
        int x;
        int y;
        Tile(int x, int y){
            this.x=x;
            this.y=y;
        }
    }
    int tilesize=10;
    private static class Cell{
        Tile position;
        int neighbors;
        Cell(Tile position){
            this.position=position;
        }

        public void setNeighbors(int neighbors) {
            this.neighbors = neighbors;
        }
    }
    Random rng = new Random();
    int BoardWidth;
    int BoardHeight;
    Cell[] cells;
    Timer frames;
    public Life(int boardwidth,int boardheight){
        BoardHeight=boardheight;
        BoardWidth=boardwidth;
        setPreferredSize(new Dimension(BoardWidth,BoardHeight));
        setBackground(Color.black);
        setFocusable(true);
        frames = new Timer(10,this);
        frames.start();
    }
    public void GenerateCells(){
        ArrayList<Cell> cellArrayList = new ArrayList<>();
        for(int i=0;i<BoardWidth/(2*tilesize);i++){//half the board width as a tile size as not all the tiles need cells
            Cell cell = new Cell(new Tile(rng.nextInt(BoardWidth/tilesize),rng.nextInt(BoardHeight/tilesize)));//creates tile at random pos
            boolean samepos=false;
            for(int j=0;j<cellArrayList.size();j++){//if there is already one in that position then don't add the cell
                if((cellArrayList.get(i).position.x==cell.position.x)&&(cellArrayList.get(i).position.y==cell.position.y)){
                    samepos=true;
                    break;
                }
            }
            if(!samepos){
                cellArrayList.add(cell);
            }
        }
        cells= new Cell[cellArrayList.size()];
        //as it adds the cell array list that has been populated to the cells array it inserts it in order of x values,
        //so it only needs to check the y values of 3 rows of cells in order,
        for(int i=0;i<cells.length;i++){
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        draw(g);
    }
    public void draw(Graphics g){
        g.setColor(new Color(255,255,255));
    }
    public void getNeighbors(){

    }
    public void updateConditions(){

    }

    @Override
    public void actionPerformed(ActionEvent e) { //called every x milliseconds by gameLoop timer
        getNeighbors();
        //get neighbors
        updateConditions();
        //update the board
        repaint();
        //repaint

    }
}
