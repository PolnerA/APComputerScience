import java.util.Arrays;
import java.util.Random;

public class SortingAlgorithms {
    static int iterations =0;
    static Random rng = new Random();
    public static void main(String[] args) {//bubble, quick, double selection sort
        for(;;) {
            int[] list = new int[]{5, 3, 2, 1, 4, 7, 6, 0, 9, 8};
            BogoSort(list);
            System.out.println("tries: " + iterations);
            if(iterations ==0){
                break;
            }
            iterations=0;
        }
    }
    public static void MergeSort(int[] list){//O(N Log N) performance
        if(1<list.length){
            int[] left = Arrays.copyOfRange(list,0,list.length/2);
            int[] right = Arrays.copyOfRange(list,list.length/2, list.length);
            //recursively sort both halves
            MergeSort(left);
            MergeSort(right);
            //merge sorted halves into one sorted array
             Merge(list,left,right);
        }
    }
    public static void ModifiedMergeSort(int[] list){//O(N Log N) performance
        if(1<list.length){
            int[] left = Arrays.copyOfRange(list,0,list.length/2);
            int[] right = Arrays.copyOfRange(list,list.length/2, list.length);
            //recursively sort both halves
            ModifiedMergeSort(right);
            ModifiedMergeSort(left);
            //merge sorted halves into one sorted array
            Merge(list,left,right);
        }
    }
    public static void SelectionSort(int[] list){//O(N^2) performance
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
    public static void ModifiedSelectionSort(int[] list){//O(N^2) performance runs faster
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
    /*public static void DoubleSelectionSort(int[] list){
        for(int k=list.length;k<= list.length/2;k-=2){//length of list reduces the length by 2 (largest and smallest each iteration)
            int smallest=i;
            for(int j=i+1;j<list.length;j++){
                if(list[j]<list[smallest]){
                    smallest=j;
                }
            }
            Swap(list,i,smallest);
        }
    }*/
    public static void BogoSort(int[] list){//O(2^N)
        while(!isSorted(list)){
            Shuffle(list);
            iterations++;
        }
    }
    public static void Shuffle(int[] list){
        for(int i=0;i< list.length;i++){
            Swap(list,list[i+rng.nextInt(list.length-i)],i);
        }
    }
    public static void Swap(int[] list,int i, int j){
        int temp = list[i];
        list[i]=list[j];
        list[j]=temp;
    }
    public static void Merge(int[] result, int[] left, int[] right){
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
