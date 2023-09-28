/*
Name:
Problem:
Pseudocode:
Notes:
Maintenance log:
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
