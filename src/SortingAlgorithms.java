import java.util.Arrays;
import java.util.Random;

public class SortingAlgorithms {
    static int iterations =0;
    static Random rng = new Random();
    public static void main(String[] args) {//bubble, quick, double selection sort
            int[] list = new int[]{5, 3, 2, 1, 4, 7, 6, 0, 9, 8};
            HeapSort(list);
    }
    public static void MergeSort(int[] list){//O(N Log N) worst-case complexity
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
    public static void ModifiedMergeSort(int[] list){//O(N Log N) worst-case complexity
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
    public static void SelectionSort(int[] list){//O(N^2) worst-case complexity
        for(int i=0;i< list.length-1;i++){
            int smallest=i;
            for(int j=i+1;j<list.length;j++){
                if(list[j]<list[smallest]){
                    smallest=j;
                }
            }
            Swap(list,i,smallest);
        }
    }
    public static void ModifiedSelectionSort(int[] list){//O(N^2) worst-case complexity runs faster
        for(int i=0;i< list.length-1;i++){
            int largest=i;
            for(int j=i+1;j<list.length;j++){
                if(list[largest]<list[j]){
                    largest=j;
                }
            }
            Swap(list,i,largest);
        }
    }
    public static void DoubleSelectionSort(int[] list){//in progress
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
            Swap(list,bottomindex,smallest);
            Swap(list,i,largest);
            bottomindex++;
        }
    }
    public static void BogoSort(int[] list){//O(?) worst-case complexity no upper bound
        while(!isSorted(list)){
            shuffle(list);
            iterations++;
        }
    }
    public static void BubbleSort(int[] list){//O(N^2) worst-case complexity
        for (int i=0;i< list.length-1;i++){
            boolean swapped = false;
            for(int j=0;j< list.length-i-1;j++){
                if(list[j+1]<list[j]){
                    Swap(list,j,j+1);
                    swapped=true;
                }
            }
            if(!swapped){
                break;
            }
        }
    }
    public static void InsertionSort(int[] list){//O(N^2) worst-case complexity
        for(int i=1;i< list.length;i++){
            int j = i-1;
            int key = list[i];

            while(0 <= j && key<list[j]){
                list[j+1]=list[j];//swaps the two values
                j--;
            }
            list[j+1]=key;
        }
    }

    public static void QuickSort(int[] list){//method to only input a list, instead of high and low index
        quickSort(list,0, list.length);// as high and low index would be the same, but can't keep list.length
        //as the low and list.length would change through the program
    }
    public static void quickSort(int[] list, int low, int high){//O(N^2) worst-case complexity
        if(low<high){                                           //Ω(N log(N)) best-case complexity
            int pivot = partition(list,low,high);               //θ(N log(N)) average-case

            quickSort(list, low, pivot - 1);
            quickSort(list, pivot + 1, high);
        }
    }
    public static void HeapSort (int[] list){//O(N Log N) worst-case complexity
        for (int i= list.length/2-1;0<=i;i--){
            heapify(list, list.length,i);
        }

        for(int i= list.length-1;0<i;i--){
            Swap(list,i,0);

            heapify(list,i,0);
        }
    }
    public static void heapify(int[] list, int n, int i){
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
        Swap(list,i,largest);
        heapify(list,n,largest);
    }
    }
    public static int partition(int[] list, int low, int high){
        int pivot = list[high];

        int i= (low -1);
        for(int j= low;j<=high;j++){
            if(list[j]< pivot){
                i++;
                Swap(list,i,j);
            }
        }
        Swap(list, i+1,high);
        return(i+1);
    }
    public static void shuffle(int[] list){
        for(int i=0;i< list.length;i++){
            Swap(list,list[i+rng.nextInt(list.length-i)],i);
        }
    }
    public static void Swap(int[] list,int i, int j){
        int temp = list[i];
        list[i]=list[j];
        list[j]=temp;
    }
    public static void merge(int[] result, int[] left, int[] right){
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
    public static boolean isSorted(int[] list){
        for(int i=1;i< list.length;i++){
            int num=list[i-1];
            if(list[i] <num){
                return false;
            }
        }
        return true;
    }
}
