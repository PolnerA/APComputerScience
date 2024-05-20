import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;
import java.util.*;

public class Levenshtein {
    static HashSet<String> a = new HashSet<>();
    static ArrayList<ArrayList<String>> paths = new ArrayList<>();
    static int size=0;//reasonable estimate for shortest path
    static boolean getPaths = false;
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
        //while(sc.hasNext()){
          //  String word = sc.nextLine();
           // a.add(word);
        //}
        sc.close();
        //tests: cat to dog, dog to cat, puppy to dog, dog to smart, dog to quack, monkey to business
        //test 1       24815.761561 ms predicted runtime (about 25 sec)
        //String word1="cat";
        //String word2="dog";
        //test 2       15703.301474 ms predicted runtime (about 16 sec)
        String word1="dog";
        String word2="cat";
        //test 3      185795.471987 ms predicted runtime (about 3 min 6 sec)
        //String word1="puppy";
        //String word2="dog";
        //test 4      189735.666912 ms predicted runtime (about 3 min 10 sec)
        //String word1="dog";
        //String word2="smart";
        //test 5     1732498.366852 ms predicted runtime (about 29 min)
        //String word1="dog";
        //String word2="quack";
        //test 6     2814028.051959 ms predicted runtime (about 47 min)
        //String word1="monkey";
        //String word2="business";
        Long pre = System.currentTimeMillis();
        solve(word1,word2);
        Long post = System.currentTimeMillis();
        System.out.println("Time: "+(post-pre)+" ms");
        System.out.println("end");
    }
    public static void solve(String word1, String word2) throws FileNotFoundException {
        HashSet<String> usedWords = new HashSet<>();
        HashSet<String> neighbors = getNeighborsSet(word1);//gets the neighbors of the first word
        Stack<String> stack = new Stack<>();//stack of words in neighbors set
        stack.addAll(neighbors);//populates
        int levdist=0;
        while (!stack.isEmpty()) {//while there are neighbors
            String wordpath = stack.pop();//current neighbor is assumed
            String word = getValue(wordpath);
            //path.add(word);
            if(usedWords.contains(word)){
                continue;
            }
            HashSet<String> currentNeighbors = getNeighborsSet(word);//the current neighbors in the assumed word
            if (!currentNeighbors.contains(word2)) {//if that neighbor isn't
                Stack<String> temp = new Stack<>();
                temp.addAll(stack);
                stack.clear();
                for(String neighbor:currentNeighbors){
                    stack.add(wordpath+"->"+neighbor);
                }
                //stack.add(word);
                stack.addAll(temp);
            } else {
                wordpath = wordpath+"->"+word2;
                if(levdist==0){
                    levdist=getLevDist(wordpath);
                }
                if(levdist<getLevDist(wordpath)){
                    break;
                }
                //System.out.println(word1+"->"+wordpath);
                if(!getPaths){
                    break;
                }
            }
        }
    }
    public static int getLevDist(String path){
        String[] a=path.split("->");
        return a.length;
    }
    public static String getValue(String path){
        //from the word1 +"->" +neighbors get the last arrow thing
        return path.substring(path.lastIndexOf(">")+1);
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
