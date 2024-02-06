import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


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
        Boolean alive;
        int neighbors;
        Cell(Tile position){
            this.position=position;
            alive=true;
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
        GenerateCells();
        frames = new Timer(5,this);
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
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        draw(g);
    }
    public void draw(Graphics g){
        g.setColor(new Color(255,255,255));
        for (int i = 0; i < cells.length; i++) {
            Cell cell= cells[i];
            if(cell.alive) {
                g.fillRect(cell.position.x * tilesize, cell.position.y * tilesize, tilesize, tilesize);
            }
        }
    }
    public void CalculateNeighbors(){//N^2 Cell one and two don't need to be looked entirely through
        for (int i = 0; i < cells.length; i++) {
            Cell cell1 = cells[i];
            cell1.setNeighbors(0);
            int x = cell1.position.x;
            int y = cell1.position.y;
            for (int j = 0; j < cells.length; j++) {
                Cell cell2 = cells[j];
                if(cell2.alive) {
                    int x2 = cell2.position.x;
                    int y2 = cell2.position.y;
                    if (x == x2 + 1) {//cell two is to the left
                        if (y == y2 + 1) {//cell two is to the bottom left
                            cell1.neighbors++;
                        } else if (y == y2) {//cell two is to the left
                            cell1.neighbors++;
                        } else if (y == y2 - 1) {//cell two is to the top left
                            cell1.neighbors++;
                        }
                    } else if (x == x2) {//cell two is in line with cell one
                        if (y == y2 + 1) {//cell two is on the bottom
                            cell1.neighbors++;
                        } else if (y == y2 - 1) {//cell two is above
                            cell1.neighbors++;
                        }
                    } else if (x == x2 - 1) {//cell two is on the right
                        if (y == y2 + 1) {//cell two is to the bottom right
                            cell1.neighbors++;
                        } else if (y == y2) {//cell two is to the right
                            cell1.neighbors++;
                        } else if (y == y2 - 1) {//cell two is to the top right
                            cell1.neighbors++;
                        }
                    }
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
}
