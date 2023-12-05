import java.util.Arrays;

public class SortingAlgorithms {
    public static void main(String[] args) {//bubble, quick, double selection sort

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
}
