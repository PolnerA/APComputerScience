import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

public class SortingAlgorithms extends JPanel implements ActionListener, KeyListener {
    int iterations =0;
    int boardwidth;
    int boardheight;
    int listsize;

    ArrayList<DrawLists> lists= new ArrayList<>();
    private static class DrawLists{
        int[] list;
        boolean indexed;
        ArrayList<Integer> indices;
        DrawLists(int[] list, ArrayList<Integer> indices){
            this.list=list;
            this.indices=indices;
            if(indices.size() == 0){
                indexed=false;
            }else{indexed=true;}
        }
    }
    int[] originallist;
    int[] list;
    int[] list2;
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
        list=originallist.clone();
        list2=originallist.clone();
        countingSort(list);
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
        DrawLists drawarray= lists.get(0);
        int index = -1;
        if(drawarray.indexed){
            ReverseBubbleSort(drawarray.indices);
            if(drawarray.indices.size()!=0){
                index=drawarray.indices.get(0);
            }
        }
        for (int i = 0; i < drawarray.list.length; i++) {
            if(i==index) {
                g.setColor(Color.red);
                g.fillRect(boardwidth - i, 0, 1, boardheight );
                g.setColor(Color.white);
                drawarray.indices.remove(0);
                if(drawarray.indices.size()!=0){
                    index=drawarray.indices.get(0);
                }
            }
            else{
                g.fillRect(boardwidth - i, drawarray.list[i], 1, boardheight - drawarray.list[i]);
            }
        }
        if(1<lists.size()) {
            lists.remove(0);
        }
        if(drawarray.indexed){
            if(0<drawarray.indices.size()) {
                drawarray.indices.remove(0);
            }
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
        for(int i=0;i<list.length;i++){
            for(int j = 0; j< originallist.length; j++) {
                if (list[i] == list2[j]) {
                    //og index of i is j
                    Swap(list2, i, j, true);
                }
            }
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
        for(int i=0;i< list.length-1;i++){
            int smallest=i;
            for(int j=i+1;j<list.length;j++){
                ArrayList<Integer> indices= new ArrayList<>();
                indices.add(smallest);
                indices.add(j);
                lists.add(new DrawLists(list.clone(),indices));
                if(list[j]<list[smallest]){
                    smallest=j;
                }
            }
            Swap(list,i,smallest,true);//action
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
                    Swap(list,j,j+1,false);//action
                    swapped=true;
                }
                ArrayList<Integer> indexes=new ArrayList<>();
                indexes.add(j);
                indexes.add(j+1);
                DrawLists a = new DrawLists(list.clone(),indexes);
                lists.add(a);
            }
            if(!swapped){
                break;
            }
        }
    }
    public void ReverseBubbleSort(ArrayList<Integer> list){//O(N^2) worst-case complexity
        for (int i=0;i< list.size()-1;i++){
            boolean swapped = false;
            for(int j=0;j< list.size()-i-1;j++){
                if(list.get(j)<list.get(j+1)){
                    ListSwap(list,j,j+1);//action
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
            ArrayList<Integer> a = new ArrayList<>();
            a.add(i);
            while(0 <= j && key<list[j]){
                list[j+1]=list[j];//swaps the two values
                a.add(j);
                a.add(j+1);
                lists.add(new DrawLists(list.clone(),a));
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
        if(low<high){                                           //Ω(N log(N)) best-case complexity
            int pivot = partition(list,low,high);               //θ(N log(N)) average-case

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
        int m=0;                                  //only works by returning an int[]//TODO
        int n= list.length;
        for(int i=0;i<n;i++){
            m=Math.max(m,list[i]);
            lists.add(new DrawLists(list.clone(),new ArrayList<>()));
        }
        int[] countArray = new int[m+1];
        //special (needs to draw count array once populated)
        for(int i=0;i < n; i++){
            countArray[list[i]]++;
            lists.add(new DrawLists(countArray.clone(),new ArrayList<>()));
        }

        for(int i=1;i<=m;i++){
            countArray[i] += countArray[i-1];
            lists.add(new DrawLists(countArray.clone(),new ArrayList<>()));
        }
        int[] outputArray = new int[n];
        //output array replaces original array while getting rid of count array
        for(int i= n-1; 0<=i;i--){
            outputArray[countArray[list[i]]-1]= list[i];
            countArray[list[i]]--;
            lists.add(new DrawLists(outputArray.clone(),new ArrayList<>()));
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
    public void RadixSort(int[] list){//O(D*(N+B)) time complexity, D is number of digits//TODO
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
        ArrayList<Integer> a= new ArrayList<>();
        if(l<n && list[largest]<list[l]) {
            largest = l;
        }

        if(r < n && list[largest]<list[r]){
            largest=r;
        }
        a.add(r);
        a.add(largest);
        a.add(l);
        if(largest != i){
            Swap(list,i,largest,false);
            lists.add(new DrawLists(list.clone(),a));
            heapify(list,n,largest);
        }
    }
    public int partition(int[] list, int low, int high){
        int pivot = list[high];
        ArrayList<Integer> indices=new ArrayList<>();
        indices.add(pivot);
        int i= (low -1);
        for(int j= low;j<=high;j++){
            indices.add(j);
            if(list[j]< pivot){
                i++;
                Swap(list,i,j,false);
                lists.add(new DrawLists(list.clone(),indices));

                //action
            }
        }
        Swap(list, i+1,high,false);
        lists.add(new DrawLists(list.clone(),indices));
        //action
        return(i+1);
    }
    public void shuffle(int[] list,boolean recordindexes){
        for(int i=0;i< list.length;i++){
            Swap(list,list[i+rng.nextInt(list.length-i)],i,recordindexes);
        }
    }
    public void Swap(int[] list,int i, int j,boolean RecordSwap){
        int temp = list[i];
        list[i]=list[j];
        list[j]=temp;
        if(RecordSwap) {
            lists.add(new DrawLists(list.clone(),new ArrayList<>()));
        }
    }
    public void ListSwap(ArrayList<Integer> list,int i, int j){
        Collections.swap(list, i, j);
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
        repaint();
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
            lists.clear();
            list = originallist.clone();
            list2 = originallist.clone();
            QuickSort(list);
        }else if(e.getKeyCode()==KeyEvent.VK_B){
            lists.clear();
            list= originallist.clone();
            list2 = originallist.clone();
            BubbleSort(list);
        }else if(e.getKeyCode()==KeyEvent.VK_H){
            lists.clear();
            list = originallist.clone();
            list2 = originallist.clone();
            HeapSort(list);
        }else if(e.getKeyCode()==KeyEvent.VK_S){
            lists.clear();
            list = originallist.clone();
            list2 = originallist.clone();
            SelectionSort(list);
        }else if(e.getKeyCode()==KeyEvent.VK_F1){
            lists.clear();
            list = originallist.clone();
            list2 = originallist.clone();
            BogoSort(list);
        }else if(e.getKeyCode()==KeyEvent.VK_M){
            lists.clear();
            list= originallist.clone();
            list2 = originallist.clone();
            MergeSort(list);
        }else if(e.getKeyCode()==KeyEvent.VK_I){
            lists.clear();
            list= originallist.clone();
            list2 = originallist.clone();
            InsertionSort(list);
        }
    }
}
