import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
/*
Name: Adam Polner
Problem:
Pseudocode:
 */
public class Levenshtein {
    static HashSet<String> Dictionary = new HashSet<>();
    static HashMap<String, HashSet<String>> Neighbors = new HashMap<>();
    public static void preCompute() throws IOException {//precomputation to put all neighbors in dictionaryWithNeighbors
        Scanner sc2 = new Scanner(new File("dictionarySortedLength.txt"));
        File neighbor = new File("dictionaryWithNeighbors");
        neighbor.createNewFile();
        FileWriter writer = new FileWriter(neighbor);
        int count = 0;
        while (sc2.hasNext()) { //_0_1_2_3_4_
            String word = sc2.nextLine();
            count++;
            System.out.println((double) count / (double) Dictionary.size());
            char[] letters = word.toCharArray();
            ArrayList<Character> letter = new ArrayList<>();
            ArrayList<Character> letter2 = new ArrayList<>();
            ArrayList<Character> letter3 = new ArrayList<>();
            for (int i = 0; i < letters.length; i++) {
                letter.add(letters[i]);
                letter2.add(letters[i]);
                letter3.add(letters[i]);
            }
            writer.write(word);
            for (int i = 0; i <= word.length(); i++) {
                if (i < word.length()) {
                    letter3.remove(i);
                    String newWord = "";
                    for (int j = 0; j < letter3.size(); j++) {
                        newWord = newWord + letter3.get(j);
                    }
                    if (Dictionary.contains(newWord)) {
                        writer.write("-" + newWord);
                    }
                    letter3 = new ArrayList<>();
                    for (int k = 0; k < letter.size(); k++) {
                        letter3.add(letter.get(k));
                    }
                }
                for (int j = 0; j < 26; j++) {
                    char g = (char) ('a' + j);
                    if (i < word.length()) {
                        String newWord = "";
                        letter2.set(i, g);
                        for (int k = 0; k < letter2.size(); k++) {
                            newWord = newWord + letter2.get(k);
                        }
                        if (Dictionary.contains(newWord)) {
                            if (!word.equals(newWord)) {
                                writer.write("-" + newWord);
                            }
                        }
                    }
                    letter2 = new ArrayList<>();
                    for (int k = 0; k < letter.size(); k++) {
                        letter2.add(letter.get(k));
                    }
                    String newWord2 = "";
                    letter2.add(i, g);
                    for (int k = 0; k < letter2.size(); k++) {
                        newWord2 = newWord2 + letter2.get(k);
                    }
                    if (Dictionary.contains(newWord2)) {
                        writer.write("-" + newWord2);
                    }
                    letter2 = new ArrayList<>();
                    for (int k = 0; k < letter.size(); k++) {
                        letter2.add(letter.get(k));
                    }
                }
            }
            writer.write("\n");
        }
        writer.close();
        sc2.close();

    }
    public static void main(String[] args) throws IOException {
        Scanner sc2 = new Scanner(new File("dictionaryWithNeighbors"));
        while (sc2.hasNext()) {//goes through with the neighbors
            String Line = sc2.nextLine();
            String[] neighborsLine = Line.split("-");
            HashSet<String> neighborsSet = new HashSet<>();
            for (int i = 1; i < neighborsLine.length; i++) {
                neighborsSet.add(neighborsLine[i]);
            }
            Neighbors.put(neighborsLine[0], neighborsSet);
        }
        sc2.close();
        Scanner sc = new Scanner(new File("dictionarySortedLength.txt"));
        while (sc.hasNext()) {
            String word = sc.nextLine();
            Dictionary.add(word);
        }
        sc.close();
        //preCompute();

        //uses pre-computed neighbors: all below are timed with precomputed neighbors
        //tests: cat to dog, dog to cat, puppy to dog, dog to smart, dog to quack, monkey to business
        //test 1       24815.761561 ms predicted runtime (about 25 sec)
        //              Current time: 2 ms run (10000) times : 0ms
        //              100 times: 0 ms |
        //String word1 = "cat";
        //String word2 = "dog";
        //test 2       15703.301474 ms predicted runtime (about 16 sec)
        //              Current time: 2 ms run (10000) times : 0 ms
        //              100 times: 0 ms |
        //String word1="dog";
        //String word2="cat";
        //test 3      185795.471987 ms predicted runtime (about 3 min 6 sec)
        //                  current time : 13 ms
        //            100 times: 5 ms  |
        //String word1="puppy";
        //String word2="dog";
        //test 4      189735.666912 ms predicted runtime (about 3 min 10 sec)
        //              current time : 12 ms
        //            100 times: 6 ms |
        //String word1="dog";
        //String word2="smart";
        //test 5     1732498.366852 ms predicted runtime (about 29 min)
        //          current time : 50 ms
        //            100 times: 20 ms |
        //String word1="dog";
        //String word2="quack";
        //test 6     2814028.051959 ms predicted runtime (about 47 min)
        //          current time : 95 ms 10000 times: 5 ms
        //            100 times: 7 ms
        String word1="monkey";
        String word2="business";
        Long sum = Long.valueOf(0);
        int num =1;
        ArrayList<HashSet<String>> path =new ArrayList<>();
        for (int i = 0; i < num; i++) {
            Long pre = System.currentTimeMillis();
            path =solve(word1, word2);
            Long post = System.currentTimeMillis();
            sum += (post - pre);
        }
        System.out.println("Time: " + (sum / num) + " ms");
        printsolvesqueue(word1,word2,path);
    }
    public static void PrintPath(ArrayList<HashSet<String>> map){
        generatePaths(map);
    }
    public static void generatePaths(ArrayList<HashSet<String>> map){
        int[] indices = new int[map.size()];
        int totalcombinations=1;
        for(HashSet<String> set: map){
            totalcombinations*= set.size();
        }
        boolean skip=false;
        for(int i=0;i<totalcombinations;i++){
            if(skip){
                skip=false;
            }
            ArrayList<String> path = new ArrayList<>();
            for(int j=0;j<map.size();j++){
                HashSet<String> set = map.get(j);
                int setSize= set.size();
                int index = indices[j];
                String[] setArray = set.toArray(new String[0]);
                if(1<=path.size()){
                    String word=path.get(path.size()-1);
                    if(Neighbors.get(word).contains(setArray[index])){
                        path.add(setArray[index]);
                    }else{
                        skip=true;
                        break;
                    }
                }
                else{
                    path.add(setArray[index]);
                }
                if((i/totalcombinations)%setSize==0){
                    indices[j] = (indices[j]+1) % setSize;
                }
            }
            if(skip){
                continue;
            }
            System.out.println(path);
            return;
        }
    }
    public static boolean isValidPath(ArrayList<String> path){
        for(int i=0;i<path.size()-1;i++){
            String word=path.get(i);
            String nextword = path.get(i+1);
            HashSet<String> neighbors = getNeighborsSet(word);
            if(!neighbors.contains(nextword)){
                return false;
            }
        }
        return true;
    }

    public static ArrayList<HashSet<String>> solve(String word1, String word2) {//solving with the neighbor ends until they meet
        HashSet<String> usedWords = new HashSet<>();//not repeating a word helps keep the out of memory error away
        HashSet<String> end = new HashSet<>();
        ArrayList<HashSet<String>> ends1 = new ArrayList<>();
        ArrayList<HashSet<String>> ends2 = new ArrayList<>();
        HashSet<String> ToEnd = new HashSet<>();
        HashSet<String> ToEnd2 = new HashSet<>();
        HashSet<String> end2 = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        Queue<String> queue2 = new LinkedList<>();
        queue.add(word1);
        end.add(word1);
        ends1.add(end);
        queue.add("");
        queue2.add(word2);
        end2.add(word2);
        ends2.add(end2);
        queue2.add("");

        while (!queue.isEmpty() && !queue2.isEmpty()) {//while there are neighbors
            String word = queue.remove();//current neighbor is assumed
            if (word.equals("")) {
                end = new HashSet<>();
                end.addAll(ToEnd);
                ends1.add(end);
                queue.add("");
                word = queue.remove();
            }
            String CurrentWord = queue2.remove();
            if (CurrentWord.equals("")) {
                end2 = new HashSet<>();
                end2.addAll(ToEnd2);
                ends2.add(0,end2);
                queue2.add("");
                CurrentWord = queue2.remove();
            }
            HashSet<String> intersection = new HashSet<>(end);
            intersection.retainAll(end2);
            if (!intersection.isEmpty()) {
                ends1.remove(ends1.size()-1);
                ends2.remove(0);
                ends1.add(intersection);
                ends1.addAll(ends2);
                return ends1;
            }
            HashSet<String> currentNeighbors = getNeighborsSet(word);//the current neighbors in the assumed word
            HashSet<String> currentNeighbors2 = getNeighborsSet(CurrentWord);
            if (currentNeighbors2 != null) {
                currentNeighbors2.removeAll(usedWords);
                ToEnd2.addAll(currentNeighbors2);
                queue2.addAll(currentNeighbors2);
            }
            if (currentNeighbors != null) {
                currentNeighbors.removeAll(usedWords);
                ToEnd.addAll(currentNeighbors);
                queue.addAll(currentNeighbors);
            }
            usedWords.add(word);
            usedWords.add(CurrentWord);

        }
        return ends1;
    }
    public static void printsolvesqueue(String word1, String word2,ArrayList<HashSet<String>> map) {//java.lang.OutOfMemoryError as there are
        //visualize 2d list as a binary tree
        //     word1n1 word1n2 word1n3//copy for each neighbor and add them to the list
        //keep count of the traversal i for bottom and j for top
        // list word1
        Queue<String> queue = new LinkedList<String>();//queue created once and used
        queue.add(word1);//populates    //w ""   try to get a sentinel of empty string for each distance
        queue.add("");                  // 1 2 3 4 5 ""
        int index=1;
        //11 12 13 14 15"" 21 22 23 24 ""
        while (!queue.isEmpty()) {//while there are neighbors goes through the stack
            String wordpath = queue.remove();//current neighbor is assumed
            if (wordpath.equals("")) {//if it hits an end of line and shortest path is found it quits as others are longer
                index++;
                queue.add("");//skips over sentinel otherwise and adds a new one to the end line marking the neighbors end
                continue;
            }
            String word = getValue(wordpath);
            HashSet<String> currentNeighbors = getNeighborsSet(word);//the current neighbors in the assumed word
            currentNeighbors.retainAll(map.get(index));
            if (!currentNeighbors.contains(word2)) {//if that neighbor isn't
                for (String neighbor : currentNeighbors) {
                    //store as a path of strings so you know to eliminate paths that come after the path
                    queue.add(wordpath+"->"+neighbor);
                }
            } else {
                wordpath = wordpath + "->" + word2;
                System.out.println(wordpath);
                return;
            }
        }
    }
    public static String getValue(String path){//from the word1 +"->" +neighbors get the last arrow thing
        return path.substring(path.lastIndexOf(">")+1);
    }
    //method for calculating neighbors instead of pre-populating the Neighbors hashmap
    public static HashSet<String> getNeighborsSet(String word) {
        //if it is already calculated it just stores and returns the value
        if (Neighbors.containsKey(word)) {
            return new HashSet<>(Neighbors.get(word));
        }
        ArrayList<Character> letter = new ArrayList<>();//sets up new ArrayLists for the new generated possible neighbors
        //one for additions, subtractions and replacements
        ArrayList<Character> letter2 = new ArrayList<>();
        ArrayList<Character> letter3 = new ArrayList<>();
        char[] letters = word.toCharArray();
        for (int i = 0; i < letters.length; i++) {
            letter.add(letters[i]);
            letter2.add(letters[i]);
            letter3.add(letters[i]);
        }
        HashSet<String> neighbors = new HashSet<>();
        for (int i = 0; i < word.length(); i++) {//for each space/character

            for (int j = 0; j < 26; j++) {
                char g = (char) ('a' + j);
                String newWord = "";
                letter2.set(i, g);
                for (int k = 0; k < letter2.size(); k++) {
                    newWord = newWord + letter2.get(k);
                }
                if (Dictionary.contains(newWord)) {
                    if (!word.equals(newWord)) {
                        neighbors.add(newWord);
                    }
                }

                letter2 = new ArrayList<>();
                for (int k = 0; k < letter.size(); k++) {
                    letter2.add(letter.get(k));
                }
                String newWord2 = "";
                letter2.add(i, g);
                for (int k = 0; k < letter2.size(); k++) {
                    newWord2 = newWord2 + letter2.get(k);
                }
                if (Dictionary.contains(newWord2)) {
                    neighbors.add(newWord2);
                }
                letter2 = new ArrayList<>();
                for (int k = 0; k < letter.size(); k++) {
                    letter2.add(letter.get(k));
                }
            }
            String newWord3 ="";
            letter3.remove(i);
            for(int k=0;k< letter3.size();k++){
                newWord3 = newWord3+letter3.get(k);
            }
            if(Dictionary.contains(newWord3)){
                neighbors.add(newWord3);
            }
            letter3=new ArrayList<>();
            for(int k=0;k< letter.size();k++){
                letter3.add(letter.get(k));
            }
        }
        for(int j=0;j<26;j++){
            char g = (char) ('a'+ j);
            String newWord2 = "";
            letter2.add(word.length(), g);
            for (int k = 0; k < letter2.size(); k++) {
                newWord2 = newWord2 + letter2.get(k);
            }
            if (Dictionary.contains(newWord2)) {
                neighbors.add(newWord2);
            }
            letter2 = new ArrayList<>();
            for (int k = 0; k < letter.size(); k++) {
                letter2.add(letter.get(k));
            }
        }
        Neighbors.put(word, neighbors);
        return new HashSet<>(neighbors);
    }
}
