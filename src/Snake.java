public class Snake {
    static int snakelength =1;
    static int snakex =0;
    static int snakey =0;
    public static void main(String[] args) {
        while (true){
            snakex++;

            DrawSnake();
        }
    }
    public static void DrawSpace(){
        for(int i=0;i<snakex;i++)
    }
    public static void DrawSnake(){
        for(int i=0;i<snakelength;i++){
            System.out.print("*");
        }
        System.out.println();
    }
}
