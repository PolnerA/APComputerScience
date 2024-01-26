import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class SortingAlgorithms extends JPanel implements ActionListener, KeyListener {
    int iterations =0;
    int boardwidth;
    int boardheight;
    ArrayList<Integer> indexes= new ArrayList<>();
    ArrayList<int[]> lists=new ArrayList<>();
    int[] originallist;
    int[] listtosort;
    int[] drawlist;
    Timer frames;
    static Random rng = new Random();
    public SortingAlgorithms(int boardwidth, int boardheight){//counting, radix
        this.boardwidth=boardwidth;
        this.boardheight=boardheight;
        setPreferredSize(new Dimension(this.boardwidth, this.boardheight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);
        populatelist();
        listtosort=originallist.clone();
        drawlist=originallist.clone();
        SelectionSort(listtosort);
        frames = new Timer(0,this);
        frames.start();

    }
    public void populatelist(){
        int [] templist= new int[boardwidth];
        for(int i=0;i<boardwidth;i++){
            templist[i]=i;
        }
        shuffle(templist,false);
        originallist=templist;
    }
    public void paintComponent(Graphics g) {//overrides other paintComponent in component, uses the graphics in its own
        //draw function and supers the previous paint Component in JComponent.java (javax.swing)
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g) {
        g.setColor(Color.white);
        ArrayList<Integer> templist = new ArrayList<>();
        for(int i=0;i<drawlist.length;i++)
        {
            templist.add(drawlist[i]);
        }
        g.fillRect(boardwidth, templist.get(0), 1, boardheight - templist.get(0));
        for (int i = 1; i < templist.size(); i++) {
            g.fillRect(boardheight - i, templist.get(i), 1, boardheight - templist.get(i));
        }
    }
    public void MergeSort(int[] list){//O(N Log N) worst-case complexity
        if(1<list.length){
            int[] left = Arrays.copyOfRange(list,0,list.length/2);
            int[] right = Arrays.copyOfRange(list,list.length/2, list.length);
            //recursively sort both halves
            MergeSort(left);
            MergeSort(right);
            //merge sorted halves into one sorted array
             merge(list,left,right);
        }
    }
    public void ModifiedMergeSort(int[] list){//O(N Log N) worst-case complexity
        if(1<list.length){
            int[] left = Arrays.copyOfRange(list,0,list.length/2);
            int[] right = Arrays.copyOfRange(list,list.length/2, list.length);
            //recursively sort both halves
            ModifiedMergeSort(right);
            ModifiedMergeSort(left);
            //merge sorted halves into one sorted array
            merge(list,left,right);
        }
    }
    public void SelectionSort(int[] list){//O(N^2) worst-case complexity
        for(int i=0;i<list.length-1;i++){
            int smallest=i;
            for(int j=i+1;j<list.length;j++){
                if(list[j]<list[smallest]){
                    smallest=j;
                }
                lists.add(list);
            }
            Swap(list,i,smallest,false);//action
        }
    }
    public void ModifiedSelectionSort(int[] list){//O(N^2) worst-case complexity runs faster
        for(int i=0;i< list.length-1;i++){
            int largest=i;
            for(int j=i+1;j<list.length;j++){
                if(list[largest]<list[j]){
                    largest=j;
                }
            }
            Swap(list,i,largest,true);
        }
    }
    public void DoubleSelectionSort(int[] list){//in progress
        int bottomindex = 0;
        for(int i=list.length-1;i<list.length;i--){
            int largest=i;
            int smallest=bottomindex;
            for(int j=i-1;list.length<j;j--){
                if(0<=j){
                if(list[j]<list[smallest]){
                    smallest=j;
                } else if (list[largest]<list[j]) {
                    largest=j;
                }
                }
            }
            Swap(list,bottomindex,smallest,true);
            Swap(list,i,largest,true);
            bottomindex++;
        }
    }
    public void BogoSort(int[] list){//O(?) worst-case complexity no upper bound
        while(!isSorted(list)){             //O(N*N!) average-case complexity
            shuffle(list,true);//action
            iterations++;
        }
    }
    public void BubbleSort(int[] list){//O(N^2) worst-case complexity
        for (int i=0;i< list.length-1;i++){
            boolean swapped = false;
            for(int j=0;j< list.length-i-1;j++){
                if(list[j+1]<list[j]){
                    Swap(list,j,j+1,true);//action
                    swapped=true;
                }
            }
            if(!swapped){
                break;
            }
        }
    }
    public void InsertionSort(int[] list){//O(N^2) worst-case complexity
        for(int i=1;i< list.length;i++){
            int j = i-1;
            int key = list[i];//find where to put the indexes

            while(0 <= j && key<list[j]){
                list[j+1]=list[j];//swaps the two values
                j--;
            }
            list[j+1]=key;
        }
    }

    public void QuickSort(int[] list){//method to only input a list, instead of high and low index
        quickSort(list,0, list.length-1);// as high and low index would be the same, but can't keep list.length
        //as the low and list.length would change through the program
    }
    public void quickSort(int[] list, int low, int high){//O(N^2) worst-case complexity
        if(low<high){                                    //Ω(N log(N)) best-case complexity
            int pivot = partition(list,low,high);        //θ(N log(N)) average-case

            quickSort(list, low, pivot - 1);
            quickSort(list, pivot + 1, high);
        }
    }
    public void HeapSort (int[] list){//O(N Log N) worst-case complexity
        for (int i= list.length/2-1;0<=i;i--){
            heapify(list, list.length,i);
        }

        for(int i= list.length-1;0<i;i--){
            Swap(list,i,0,true);//action

            heapify(list,i,0);
        }
    }
    public int[] countingSort (int[] list){//O(N+M) Worst-case complexity where N and M are the size of the input array and the count array
        int m=0;                                  //only works by returning an int[]
        int n= list.length;
        for(int i=0;i<n;i++){
            m=Math.max(m,list[i]);
        }

        int[] countArray = new int[m+1];
        //special (needs to draw count array once populated)
        for(int i=0;i < n; i++){
            countArray[list[i]]++;
        }

        for(int i=1;i<=m;i++){
            countArray[i] += countArray[i-1];
        }
        int[] outputArray = new int[n];
        //output array replaces original array while getting rid of count array
        for(int i= n-1; 0<=i;i--){
            outputArray[countArray[list[i]]-1]= list[i];
            countArray[list[i]]--;
        }
        return outputArray;
    }
    public void countSort(int[] list, int n, int exp){//for radix sort uses the current digit (exp) to sort
        int[] output = new int[n];
        int i;//variation of counting sort, count should be made and list should be replaced by output
        int[] count = new int[10];

        for(i=0;i<n;i++){
            count[(list[i]/exp) % 10]++;
            //action
        }
        for(i=1;i<10;i++){
            count[i] += count[i-1];
        }
        for(i=n-1;0<=i;i--){
            output[count[(list[i]/exp)%10]-1]=list[i];
            count[(list[i]/exp)%10]--;
        }
        for(i=0;i<n;i++){
            list[i]=output[i];
            //action
        }
    }
    public void RadixSort(int[] list){//O(D*(N+B)) time complexity, D is number of digits
        int m = getMax(list);                //N is the number of elements, B is the base of number system used
        for(int exp=1;0<m/exp;exp *=10){
            countSort(list,list.length,exp);
        }
    }
    public int getMax(int[] list){
        int mx=list[0];
        for(int i=1;i< list.length;i++){
            if (mx<list[i]){
                mx=list[i];
            }
        }
        return mx;
    }
    public void heapify(int[] list, int n, int i){
        int largest = i;
        int l = 2*i+1;
        int r = 2*i+2;

        if(l<n && list[largest]<list[l]) {
            largest = l;
        }

        if(r < n && list[largest]<list[r]){
            largest=r;
        }
        if(largest != i){
            Swap(list,i,largest,true);
            heapify(list,n,largest);
        }
    }
    public int partition(int[] list, int low, int high){
        int pivot = list[high];

        int i= (low -1);
        for(int j= low;j<=high;j++){
            if(list[j]< pivot){
                i++;
                Swap(list,i,j,true);
                //action
            }
        }
        Swap(list, i+1,high,true);
        //action
        return(i+1);
    }
    public void shuffle(int[] list,boolean recordindexes){
        for(int i=0;i< list.length;i++){
            Swap(list,list[i+rng.nextInt(list.length-i)],i,recordindexes);
        }
    }
    public void Swap(int[] list,int i, int j,boolean Recordindexes){
        if(Recordindexes) {
            indexes.add(i);
            indexes.add(j);
        }
        int temp = list[i];
        list[i]=list[j];
        list[j]=temp;
    }
    public void merge(int[] result, int[] left, int[] right){
        int i1=0; // left array index
        int i2=0; // right array index
        for(int i=0;i< result.length;i++){
            if(right.length<=i2||(i1< left.length&&left[i1]<=right[i2])){
                result[i]=left[i1]; //take from left
                i1++;
            }
            else{
                result[i]=right[i2]; //take from right
                i2++;
            }
        }
    }
    public boolean isSorted(int[] list){
        for(int i=1;i< list.length;i++){
            int num=list[i-1];
            if(list[i] <num){
                return false;
            }
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(indexes.size() !=0){updatedrawlist();}
        repaint();
    }
    public void updatedrawlist(){
        Swap(drawlist, indexes.get(1),indexes.get(0),false );
        for(int i=0;i<2;i++) {
            indexes.remove(0);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_Q) {
            indexes.clear();
            listtosort = originallist.clone();
            drawlist = originallist.clone();
            QuickSort(listtosort);
        }else if(e.getKeyCode()==KeyEvent.VK_B){
            indexes.clear();
            listtosort = originallist.clone();
            drawlist = originallist.clone();
            BubbleSort(listtosort);
        }else if(e.getKeyCode()==KeyEvent.VK_H){
            indexes.clear();
            listtosort = originallist.clone();
            drawlist = originallist.clone();
            HeapSort(listtosort);
        }else if(e.getKeyCode()==KeyEvent.VK_S){
            indexes.clear();
            listtosort = originallist.clone();
            drawlist = originallist.clone();
            SelectionSort(listtosort);
        }else if(e.getKeyCode()==KeyEvent.VK_F1){
            indexes.clear();
            listtosort = originallist.clone();
            drawlist = originallist.clone();
            BogoSort(listtosort);
        }
    }
}
