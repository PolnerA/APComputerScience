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
        sc.close();
        Scanner sc2 = new Scanner(System.in);
        System.out.println("First word:");
        String word1 = sc2.nextLine().toLowerCase();
        System.out.println("Second word");
        String word2 = sc2.nextLine().toLowerCase();
        GetPath(word1,word2);
    }
    public static void GetPath(String word1,String word2) throws FileNotFoundException {
        String [] neighbors = getNeighbors(word1);
        if(isOneAway(word1,word2)){
            System.out.println("done");
        }else{
            for(String neighbor:neighbors){
                String[] ns =getNeighbors(neighbor);
                if(isOneAway(neighbor,word2)){
                    System.out.println("done");
                }else{
                    for(String neighbor2:ns){
                        GetPath(neighbor2,word2);
                    }
                }
            }
        }
    }
    public static String[] getNeighbors(String word) throws FileNotFoundException{
        Scanner sc = new Scanner(new File("dictionaryWithNeighbors"));
        while(sc.hasNext()){
            String line =sc.nextLine();
            String [] results =line.split("-");
            if(results[0].equals(word)){
                return results;
            }
        }
        return null;
    }
    public static boolean isOneAway(String word, String neighbor) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("dictionaryWithNeighbors"));
        String [] results = getNeighbors(word);
        for(int i=0;i<results.length;i++){
            if(results[i].equals(neighbor)){
                return true;
            }
        }
        return false;
    }
}
