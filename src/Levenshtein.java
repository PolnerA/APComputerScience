import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Levenshtein {
    static HashSet<String> a = new HashSet<>();
    public static void preCompute() throws IOException {
        Scanner sc2 = new Scanner(new File("dictionarySortedLength.txt"));
        File neighbor = new File("dictionaryWithNeighbors");
        neighbor.createNewFile();
        FileWriter writer = new FileWriter(neighbor);
        int count=0;
        while(sc2.hasNext()){ //_0_1_2_3_4_
            String word = sc2.nextLine();
            count++;
            System.out.println((double) count/(double) a.size());
            char[] letters=word.toCharArray();
            ArrayList<Character> letter = new ArrayList<>();
            ArrayList<Character> letter2 = new ArrayList<>();
            for(int i=0;i< letters.length;i++){
                letter.add(letters[i]);
                letter2.add(letters[i]);
            }
            writer.write(word);
            for(int i =0; i<=word.length();i++){
                for(int j=0;j<26;j++){
                    char g = (char) ('a' + j);
                    if(i<word.length()) {
                        String newWord = "";
                        letter2.set(i, g);
                        for (int k = 0; k < letter2.size(); k++) {
                            newWord = newWord + letter2.get(k);
                        }
                        if (a.contains(newWord)) {
                            if (!word.equals(newWord)) {
                                writer.write("-" + newWord);
                            }
                        }
                    }
                    letter2=new ArrayList<>();
                    for(int k=0;k<letter.size();k++){letter2.add(letter.get(k));}
                    String newWord2="";
                    letter2.add(i,g);
                    for(int k=0;k<letter2.size();k++){
                        newWord2= newWord2 + letter2.get(k);
                    }
                    if(a.contains(newWord2)){
                        writer.write("-"+newWord2);
                    }
                    letter2=new ArrayList<>();
                    for(int k=0;k<letter.size();k++){letter2.add(letter.get(k));}
                }
            }
            writer.write("\n");
        }
        writer.close();
        sc2.close();

    }
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(new File("dictionarySortedLength.txt"));
        while(sc.hasNext()){
            String word = sc.nextLine();
            a.add(word);
        }
        //pseudocode: check if the two words have neighbors in common, if no then check if each of their neighbors have neighbors in common
        //repeat with next level of neighbors
        sc.close();
        Scanner sc2 = new Scanner(System.in);
        System.out.println("First word:");
        String word1 = sc2.nextLine().toLowerCase();
        System.out.println("Second word");
        String word2 = sc2.nextLine().toLowerCase();
        getPath(word1,word2,false);
        HashSet<String> b = getNeighborsSet(word1);
        if(!b.contains(word2)){
            for(String n:b){
                HashSet<String> c = getNeighborsSet(n);
                if(!c.contains(word2)){
                    for(String neighbors:c){
                        HashSet<String> d = getNeighborsSet(neighbors);
                        if(!d.contains(word2)){
                            for(String ne:d ) {
                                HashSet<String> e = getNeighborsSet(ne);
                                if (!e.contains(word2)){

                                }else{
                                    System.out.println(word1+"->"+n+"->"+neighbors+"->"+ne+"->"+word2);
                                }
                            }
                        }else {
                            System.out.println(word1+"->"+n+"->"+neighbors+"->"+word2);
                        }
                    }
                }else {
                    System.out.println(word1+"->"+n+"->"+word2);

                }
            }
        }else {
            System.out.println(word1+"->"+word2);
        }
        System.out.println("fin");
    }
    public static boolean getPath(String word1, String word2, boolean goBack) throws FileNotFoundException{
        HashSet<String> neighbors = getNeighborsSet(word1);
        String[] neighborsArr = getNeighborsArr(word1);
        if(neighbors.contains(word2)){
            System.out.println("done");
            return true;
        }else if(!goBack){
            for(String neighbor:neighbors){
                if(getPath(neighbor,word2,true)){
                    return true;
                }else{

                }
            }
        }
        return false;
    }
    public static String[] getNeighborsArr(String word) throws FileNotFoundException{
        Scanner sc = new Scanner(new File("dictionaryWithNeighbors"));
        HashSet<String> Neighbors = new HashSet<>();
        while(sc.hasNext()){
            String line =sc.nextLine();
            String [] results =line.split("-");
            if(results[0].equals(word)){
                return results;
            }
        }
        return null;
    }
    public static HashSet<String> getNeighborsSet(String word) throws FileNotFoundException{
        Scanner sc = new Scanner(new File("dictionaryWithNeighbors"));
        HashSet<String> Neighbors = new HashSet<>();
        while(sc.hasNext()){
            String line =sc.nextLine();
            String [] results =line.split("-");
            if(results[0].equals(word)){
                for(int i=0;i< results.length;i++){
                    Neighbors.add(results[i]);
                }
                return Neighbors;
            }
        }
        return null;
    }
}
