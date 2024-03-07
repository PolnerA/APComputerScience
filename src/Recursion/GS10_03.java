/*
Name: Adam Polner
Problem:Write a modified version of the selection sort algorithm that selects the largest element each time
and moves it to the end of the array
PseudoCode:Goes through the list back to front, each time it does this it checks all the next elements,
moving the largest to the back
Notes:
Maintenance log:
Date:       Done:
3/5/2024    Started and finished modified selection sort

 */
package Recursion;

public class GS10_03 {
    public static void main(String[] args) {
        int[] array = new int[]{5,2,7,9,1,4,3,8,6,0};
        ModifiedSelectionSort(array);
    }
    public static void ModifiedSelectionSort(int[] array){
        for(int i=array.length-1;0<i;i--){
            int largest=i;
            for(int j=i-1;0<=j;j--){
                if(array[largest]<array[j]){
                    largest=j;
                }
            }
            Swap(array,i,largest);
        }
    }
    public static void Swap(int[] array, int i, int j){
        int temp = array[j];
        array[j]=array[i];
        array[i]=temp;
    }
}
