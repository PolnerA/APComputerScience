/*
Name: Adam Polner
Problem: Do BJP Ch 1 Project 3. Write a program with static methods to output 
    the song I Know an Old Lady Who Swallowed a Fly. Use methods for each verse and one for the refrain.
Pseudocode: Use System.out.println to output each line, then use a method for the following things, the methods go back to the final one
Notes:
Maintenance log:
Date:        Done:
9/26/2023  Finished the first half of GS01-04
9/27/2023  Finished GS01-04
 */
public class GS01_04 {
    public static void main(String[] args) {
        System.out.println("There was an old lady who swallowed a fly.");
        IDKY();
        System.out.println("There was an old lady who swallowed a spider,\nThat wriggled and iggled and jiggled inside her.");
        Spider();
        System.out.println("There was an old lady who swallowed a bird,\nHow absurd to swallow a bird");
        Bird();
        System.out.println("There was an old lady who swallowed a cat,\nImagine that to swallow a cat");
        Cat();
        System.out.println("There was an old lady who swallowed a dog,\nWhat a hog to swallow a dog");
        Dog();
        System.out.println("There was an old lady who swallowed a horse,\nShe died of course.");
    }
    public static void IDKY(){
        System.out.println("I don't know why she swallowed that fly,\nPerhaps She'll die.\n");
    }
    public static void Spider(){
        System.out.println("She swallowed the spider to catch the fly,");
        IDKY();
    }
    public static void Bird() {
        System.out.println("She swallowed the bird to catch the spider,");
        Spider();
    }
    public static void Cat() {
        System.out.println("She swallowed the cat to catch the bird,");
        Bird();
    }
    public static void Dog(){
        System.out.println("She swallowed the dog to catch the cat,");
        Cat();
    }

}
