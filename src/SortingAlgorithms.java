public class SortingAlgorithms {
    public static void main(String[] args) {//selection, merge, bubble, quick, double selection sort
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
}
