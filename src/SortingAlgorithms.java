/*
Name: Adam Polner
Problem: Create a program with a couple of sorting algorithms and visualize the changes to the lists 
Pseudocode: Pass through a sorting algorithm with a list, after every change to the list add it to an array list,
keep reading off this array list in sequence drawing every list
Notes: big O() notation doesn't show everything it is because of this that other sorts like radix sort and
heap sort although having a good big O() aren't widely used, this is because radix sort only works with integers
and heap sort you have to create a whole new data structure of a heap, so it just isn't worth it.
Since the array list of lists also needs to know where it has indexes it is instead a custom class called draw list
which uses the int array and an array list of integers for indices. So instead it is an Array list of the custom class
known as draw list
Maintenance log:
Date:        Done:
1/4/2024    programmed selection sort, bogo sort
1/9/2024    programmed bubble sort
1/11/2024   programmed insertion and quick sort
1/12/2024   programmed heap sort and counting sort
1/18/2024   programmed merge sort and radix sort
1/19/2024   started programming visualizations
1/22/2024   added draw functionality for arrays
1/23/2024   decided after which changes to print out the array from
1/25/2024   stores the lists to draw and the indices to draw after swaps only
1/28/2024   created the draw list class and used it to visualize merge sort
1/29/2024   visualized counting sort by using indices for the count list
1/30/2024   cleaned up code
*/
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

public class SortingAlgorithms extends JPanel implements ActionListener, KeyListener {
    int iterations =0;//iterations for bogo sort
    //board dimensions for drawing lists
    int boardwidth;
    int boardheight;
    //lists of drawlists to be able to draw lists along with an array list of indices for indexing
    ArrayList<DrawLists> lists= new ArrayList<>();
    private static class DrawLists{
        int[] list;//class stores the original list whether its indexed as a boolean and the indices it has
        boolean indexed;
        ArrayList<Integer> indices;//stores indices to highlight
        DrawLists(int[] list, ArrayList<Integer> indices){//be able to add indices
            this.list=list;
            this.indices=indices;
            indexed = true;
        }
        DrawLists(int[] list){//just storing the list (for just swaps)
            this.list=list;
            this.indices = new ArrayList<>();
            indexed=false;
        }
    }
    int[] originallist;
    //original list kept to keep sorting the same list when a button is pressed to do another one
    int[] list;
    //list that is sorted with its copies stored in lists
    int[] list2;
    //list2 is for swapping values with the original array for visualizing merge sort
    Timer frames;
    //frames get updated as fast as the timer can call
    static Random rng = new Random();
    public SortingAlgorithms(int boardwidth, int boardheight){
        //initializes everything
        this.boardwidth=boardwidth;
        this.boardheight=boardheight;
        setPreferredSize(new Dimension(this.boardwidth, this.boardheight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);
        populatelist();
        //populates both the list to sort (list) and list 2 as original list
        list=originallist.clone();
        list2=originallist.clone();
        CountingSort(list);//boots up with radix sort but can be changed with button input
        frames = new Timer(0,this);
        //draws a new list with a swap and/or index every frame updates as fast as swing can keep up
        frames.start();

    }
    public void populatelist(){
        int [] templist= new int[boardwidth];
        for(int i=0;i<boardwidth;i++){
            templist[i]=i;
        }
        //populates the list with all board width values
        shuffle(templist,false);
        //shuffles the templist not recording the swaps
        originallist=templist;
        //original list has the domain and range of board width with randomized values at each x
    }
    public void paintComponent(Graphics g) {//overrides other paintComponent in component, uses the graphics in its own
        //draw function and supers the previous paint Component in JComponent.java (javax.swing)
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g) {
        //sets the color to white
        g.setColor(Color.white);
        DrawLists drawarray= lists.get(0);
        int index = -1;//sets index to one as the list starts at 0 so index shouldn't ever be used unless overridden
        if(drawarray.indexed){//if it is indexed then it bubble sorts the array of indices 
            ListBubbleSort(drawarray.indices);// (usually not more than 10 and bubble is easy to implement) and get at 0
            if(drawarray.indices.size()!=0){
                index=drawarray.indices.get(0);
            }
        }
        for (int i = 0; i < drawarray.list.length; i++) {//goes through the entire draw array list
            if(i==index) {//if the index is the same as the one it wants
                g.setColor(Color.red);
                g.fillRect(boardwidth - i, 0, 1, boardheight );//colors the segment red
                g.setColor(Color.white);
                drawarray.indices.remove(0);
                if(drawarray.indices.size()!=0){//removes the current one in the queue and if there is still more it moves on
                    index=drawarray.indices.get(0);//as it was sorted it should be in increasing number meaning as it goes 
                }                        //across it should print all on one array
            }
            else{
                g.fillRect(boardwidth - i, drawarray.list[i], 1, boardheight - drawarray.list[i]);
                //draws a rectangle starting from right for the list to be the smallest on the left (personal preference)
                //draws the y as the value of the current array segment width of one (each pixel shows a spot on the array)
                //then the length subtracts the y value from the board height as y=0 is at the top of the screen
            }
        }//
        if(1<lists.size()) {//only removes if there are 2 or more elements as if there is only one,
                            //and it removes it, it has nothing to draw, otherwise keeps drawing last element
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
        for(int i=0;i<list.length;i++){//slow way to get original spots, but it works, as the list
            for(int j = 0; j< originallist.length; j++) {//is sorted fast, but it stores the indexes 
                if (list[i] == list2[j]) {//and the visualization occurs only after Merge sort has finished 
                    //original index of i is j
                    Swap(list2, i, j, true);
                    //swaps on a secondary array to show what merge sort is doing
                }
            }
        }
    }
    public void ModifiedMergeSort(int[] list){//O(N Log N) worst-case complexity
        if(1<list.length){
            int[] left = Arrays.copyOfRange(list,0,list.length/2);//same thing but does the right first, then left
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
            //goes through the list and selects the smallest element
            for(int j=i+1;j<list.length;j++){
                ArrayList<Integer> indices= new ArrayList<>();
                indices.add(smallest);
                indices.add(j);
                //adds the smallest and j as indices and creates a new draw list at the current state
                lists.add(new DrawLists(list.clone(),indices));
                if(list[j]<list[smallest]){//if j is smaller than the current smallest change the smallest
                    smallest=j;
                }
            }
            Swap(list,i,smallest,true);//swaps the i and smallest records the swap to a draw list;
        }
    }
    public void ModifiedSelectionSort(int[] list){//O(N^2) worst-case complexity runs faster
        for(int i=0;i< list.length-1;i++){
            int largest=i;
            for(int j=i+1;j<list.length;j++){
                if(list[largest]<list[j]){
                    largest=j;
                }//same as regular selection sort, looks for largest instead, and it doesn't get displayed with indices.
            }
            Swap(list,i,largest,true);
        }
    }
    public void BogoSort(int[] list){//O(?) worst-case complexity no upper bound
        while(!isSorted(list)){             //O(N*N!) average-case complexity
            shuffle(list,true);
            //shuffles the list and records each swap
            //tracks iterations for fun
            iterations++;
        }
    }
    public void BubbleSort(int[] list){//O(N^2) worst-case complexity
        for (int i=0;i< list.length-1;i++){
            //goes through the list
            boolean swapped = false;
            //checks if it has swapped it yet
            for(int j=0;j< list.length-i-1;j++){
                //checks all the elements it hasn't sorted yet
                if(list[j+1]<list[j]){
                    Swap(list,j,j+1,false);
                    swapped=true;
                    //swaps but doesn't record it as it doesn't have indices
                }
                ArrayList<Integer> indexes=new ArrayList<>();
                indexes.add(j);
                indexes.add(j+1);
                //added the two it swapped as indices
                DrawLists a = new DrawLists(list.clone(),indexes);
                //records the list post swap but with indices
                lists.add(a);
            }
            if(!swapped){
                break;
            }
        }
    }
    public void ListBubbleSort(ArrayList<Integer> list){//O(N^2) worst-case complexity
        for (int i=0;i< list.size()-1;i++){//fast list bubble sort to sort the indices array list while drawing
            boolean swapped = false;//same as regular bubble sort but with an arraylist<integer> input
            for(int j=0;j< list.size()-i-1;j++){
                if(list.get(j+1)<list.get(j)){
                    ListSwap(list,j,j+1);
                    swapped=true;
                }
            }
            if(!swapped){
                break;
            }
        }
    }
    public void InsertionSort(int[] list){//O(N^2) worst-case complexity
        for(int i=1;i< list.length;i++){//goes through the list
            int j = i-1;
            int key = list[i];
            ArrayList<Integer> a = new ArrayList<>();
            a.add(i);
            while(0 <= j && key<list[j]){
                list[j+1]=list[j];//keeps moving j until it has the correct spot
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
        //as the low and list.length would change through the program, keep theme of void and one input
    }
    public void quickSort(int[] list, int low, int high){//O(N^2) worst-case complexity
        if(low<high){                                           //Ω(N log(N)) best-case complexity
            int pivot = partition(list,low,high);               //θ(N log(N)) average-case
            //creates a partition and quick sorts around the pivot as either the low or high
            quickSort(list, low, pivot - 1);
            quickSort(list, pivot + 1, high);
        }
    }
    public void HeapSort (int[] list){//O(N Log N) worst-case complexity
        for (int i= list.length/2-1;0<=i;i--){//counts down from the middle of the list and turns it to a max heap
            heapify(list, list.length,i);
        }

        for(int i= list.length-1;0<i;i--){//starts from the top and goes down, swaps the i value with 0 and
            Swap(list,i,0,true);//again does the max heap thing

            heapify(list,i,0);
        }
    }
    public void CountingSort (int[] list){//O(N+M) Worst-case complexity where N and M are the size of the input array and the count array
        int m=0;//max element at 0
        int n= list.length;//n number of elements in the list
        for(int i=0;i<n;i++){//gets the max element from the list and makes it m
            m=Math.max(m,list[i]);
        }
        int[] countArray = new int[m+1];
        //creates a count array with a length of the max value + 1 for counting all occurrences of every element
        for(int i=0;i < n; i++){//populates count array for adding one to the index of each value of the original list
            countArray[list[i]]++;
            ArrayList<Integer> a = new ArrayList<>();
            a.add(list[i]);//adds the spots in the count array as an index as showing a secondary array with a value
            lists.add(new DrawLists(list.clone(),a));//of one wouldn't look too good
        }

        for(int i=1;i<=m;i++){//makes the count array have the correct y values adding on the previous ones
            countArray[i] += countArray[i-1];//example instead of 1,1,1,1,1,1 it does 1,2,3,4,5,6
        }
        int[] outputArray = new int[n];
        //output array replaces original array while getting rid of count array
        for(int i= n-1; 0<=i;i--){
            outputArray[countArray[list[i]]-1]= list[i];//moves the list indexed at i to the correct place
                                                        //through the count array
            countArray[list[i]]--;
            //removes the value from the count array, removes one count from the counts of that element
            lists.add(new DrawLists(outputArray.clone()));//records each swap
        }
        for(int i=0;i<n;i++){//adds the output to the list
            list[i]=outputArray[i];
        }
    }
    public void countSort(int[] list, int n, int exp){//for radix sort uses the current digit (exp) to sort
        int[] output = new int[n];
        int i;//variation of counting sort, uses the length of the list and exponent on which digit to count
        int[] count = new int[10];
        ArrayList<Integer> a = new ArrayList<>();
        for(i=0;i<n;i++){//populates the count list by seeing the digit where they are supposed to through exp and % 10
            count[(list[i]/exp) % 10]++;
            a.add(list[i]);
            lists.add(new DrawLists(list.clone(),a));
            //shows current state of list
        }
        for(i=1;i<10;i++){//changes the count to build on previous ones 11111 to 12345
            count[i] += count[i-1];
        }
        for(i=n-1;0<=i;i--){//populates the output list by passing the original list through the count array
            output[count[(list[i]/exp)%10]-1]=list[i];
            count[(list[i]/exp)%10]--;
            //keeps track by decreasing the count by each instance of that count added to output
        }
        for(i=0;i<n;i++){//adds the output to the list
            list[i]=output[i];
            lists.add(new DrawLists(list.clone(),new ArrayList<>()));
        }
    }
    public void RadixSort(int[] list){//O(D*(N+B)) time complexity, D is number of digits//
        int m = getMax(list);//gets max from the list /M is the number of elements, B is the base of number system used
        for(int exp=1;0<m/exp;exp *=10){//for each exponent (multiple of 10) / digit
                                        // and it goes through the count sort that many times
            countSort(list,list.length,exp);
        }
    }
    public int getMax(int[] list){
        int mx=list[0];//goes through the list and if there is a larger element than the current max it makes it the max
        for(int i=1;i< list.length;i++){
            if (mx<list[i]){
                mx=list[i];
            }
        }
        return mx;
    }
    public void heapify(int[] list, int n, int i){
        int largest = i;//creates the largest and left and right integers
        int l = 2*i+1;
        int r = 2*i+2;
        ArrayList<Integer> a= new ArrayList<>();//integer array list for indices
        if(l<n && list[largest]<list[l]) {
            largest = l;
        }

        if(r < n && list[largest]<list[r]){
            largest=r;
        }//sets either the left or the right as the largest
        a.add(r);
        a.add(largest);
        a.add(l);
        if(largest != i){// if the largest isn't i it swaps it to make it the largest then recursively calls itself
            Swap(list,i,largest,false);
            lists.add(new DrawLists(list.clone(),a));
            heapify(list,n,largest);
        }
    }
    public int partition(int[] list, int low, int high){
        int pivot = list[high];//creates the pivot at the last element
        ArrayList<Integer> indices=new ArrayList<>();
        indices.add(pivot);//adds the pivot to the indices
        int i= (low -1);
        for(int j= low;j<=high;j++){//goes from low to high
            indices.add(j);
            if(list[j]< pivot){//if it is less than the pivot increases i
                i++;//and swaps i and j
                Swap(list,i,j,false);//doesn't record swap as it has indices to record with it
                lists.add(new DrawLists(list.clone(),indices));
            }
        }
        Swap(list, i+1,high,false);//doesn't record swap as it has indices to record with it
        lists.add(new DrawLists(list.clone(),indices));
        return(i+1);//returns i +1 as the pivot to then keep recursively sorting it
    }
    public void shuffle(int[] list,boolean recordswap){
        //record swap is for populating the list(false) and BogoSort(true)
        for(int i=0;i< list.length;i++){//goes through each element in the list swapping each
            Swap(list,list[i+rng.nextInt(list.length-i)],i,recordswap);// one with another random one
        }
    }
    public void Swap(int[] list,int i, int j,boolean RecordSwap){//record swap boolean as some need to record
                                                                 // post swap with indices
        int temp = list[i];//swaps the two elements and records the swap if record swap is true
        list[i]=list[j];
        list[j]=temp;
        if(RecordSwap) {
            lists.add(new DrawLists(list.clone()));
        }
    }
    public void ListSwap(ArrayList<Integer> list,int i, int j){
        Collections.swap(list, i, j);//swaps the integer arraylist by using collections.swap()
    }
    public void merge(int[] result, int[] left, int[] right){
        int i1=0; // left array index
        int i2=0; // right array index

        for(int i=0;i< result.length;i++){//goes through the result array
            if(right.length<=i2||(i1< left.length&&left[i1]<=right[i2])){
            //if the right array index is last on the list, or greater than the list it will grab from the left,
            //if the index on the left isn't yet over the left array, 
            //and that the value at the left index is less than the value at the right index, 
            //then take from left, else right
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
        //goes through the list, and if something is out of order, it returns false, if everything goes well, true
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
        //every time the timer fires it repaints the scene which calls the draw function
        //delay of 0 ms means that it draws frames as fast as it can
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
        //event based key detection with java's key listener
        if(e.getKeyCode()==KeyEvent.VK_Q) {//if q is pressed it starts quick sort
            //resets the conditions and takes from the saved original list
            lists.clear();
            list = originallist.clone();
            list2 = originallist.clone();
            QuickSort(list);
        }else if(e.getKeyCode()==KeyEvent.VK_B){//if b is pressed: starts bubble sort
            lists.clear();
            list= originallist.clone();
            list2 = originallist.clone();
            BubbleSort(list);
        }else if(e.getKeyCode()==KeyEvent.VK_H){//if h is pressed: starts heap sort
            lists.clear();
            list = originallist.clone();
            list2 = originallist.clone();
            HeapSort(list);
        }else if(e.getKeyCode()==KeyEvent.VK_S){//if s is pressed: starts selection sort
            lists.clear();
            list = originallist.clone();
            list2 = originallist.clone();
            SelectionSort(list);
        }else if(e.getKeyCode()==KeyEvent.VK_F1){//if f1 is pressed: starts bogo sort
            lists.clear();
            list = originallist.clone();
            list2 = originallist.clone();
            BogoSort(list);
        }else if(e.getKeyCode()==KeyEvent.VK_M){//if m is pressed: starts merge sort
            lists.clear();
            list= originallist.clone();
            list2 = originallist.clone();
            MergeSort(list);
        }else if(e.getKeyCode()==KeyEvent.VK_I){//if i is pressed: starts insertion sort
            lists.clear();
            list= originallist.clone();
            list2 = originallist.clone();
            InsertionSort(list);
        }else if(e.getKeyCode()==KeyEvent.VK_C){//if c is pressed: starts counting sort
            lists.clear();
            list= originallist.clone();
            list2 = originallist.clone();
            CountingSort(list);
        }else if(e.getKeyCode()==KeyEvent.VK_R){//if r is pressed: starts radix sort
            lists.clear();
            list= originallist.clone();
            list2 = originallist.clone();
            RadixSort(list);
        }
    }
}
