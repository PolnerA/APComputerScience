import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
/*
Name: Adam Polner
Problem: get a path from two words with the least amount of edits (insertions,deletions,replacements)
Pseudocode: go from both from word 1 to word 2 and store the current ends of each computation, if the ends intersect
that means there is a solution so, return the map as it stores the solution, then do a simple one sided queue to print
the path from word one to two only keeping the items that are also in the map
 */
public class Levenshtein {
    static HashSet<String> Dictionary = new HashSet<>();
    static HashMap<String, HashSet<String>> Neighbors = new HashMap<>();
    public static void preCompute() throws IOException {//pre-computation to put all neighbors in dictionaryWithNeighbors
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
        while (sc2.hasNext()) {
            String Line = sc2.nextLine();
            String[] neighborsLine = Line.split("-");
            HashSet<String> neighborsSet = new HashSet<>();
            for (int i = 1; i < neighborsLine.length; i++) {
                neighborsSet.add(neighborsLine[i]);
            }
            Neighbors.put(neighborsLine[0], neighborsSet);
        }//pre-populates the neighbors hashmap to get neighbors of a given word quickly
        sc2.close();
        Scanner sc = new Scanner(new File("dictionarySortedLength.txt"));
        while (sc.hasNext()) {
            String word = sc.nextLine();
            Dictionary.add(word);
        }//populates the dictionary to switch to neighbor calculation on the fly
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
        PrintSolveQueue(word1,word2,path);
    }
    //print solve queue does a breadth first search from neighbor to neighbor, and using a map it can eliminate
    //a lot of redundant neighbors making it faster
    public static void PrintSolveQueue(String word1, String word2,ArrayList<HashSet<String>> map) {
        Queue<String> queue = new LinkedList<String>();
        //queue goes through all the words and adds their neighbors to the queue
        queue.add(word1);
        queue.add("");
        //adds an empty string to the queue which serves as a sentinel showing the current distance from word 1
        //the distance serves as the index for the map to know which predicted values to keep
        int index=1;
        while (!queue.isEmpty()) {
            String wordpath = queue.remove();
            //goes through the queue grabbing each word
            if (wordpath.equals("")) {
                index++;//if it reached the end of a distance
                //increases the index,
                //reaching the end means all neighbors were calculated so the next empty string
                //would reach the end of the next levenshtein distance
                queue.add("");
                continue;
            }
            String word = getValue(wordpath);
            //gets the current word in the total path
            HashSet<String> currentNeighbors = getNeighborsSet(word);
            //gets the words neighbors and only keeps the ones in the map
            currentNeighbors.retainAll(map.get(index));
            if (!currentNeighbors.contains(word2)) {
                //if the neighbors don't end the program keep adding the neighbors to the queue
                for (String neighbor : currentNeighbors) {
                    queue.add(wordpath+"->"+neighbor);
                }
            } else {
                //if the neighbors finally reach the second word it prints out the whole path
                wordpath = wordpath + "->" + word2;
                System.out.println(wordpath);
                return;
            }
        }
    }
    public static ArrayList<HashSet<String>> solve(String word1, String word2) {//solving with the neighbor ends until they meet
        HashSet<String> usedWords = new HashSet<>();//not repeating a word as the optimal path won't repeat a word
        HashSet<String> end = new HashSet<>();
        ArrayList<HashSet<String>> ends1 = new ArrayList<>();
        ArrayList<HashSet<String>> ends2 = new ArrayList<>();
        HashSet<String> ToEnd = new HashSet<>();
        HashSet<String> ToEnd2 = new HashSet<>();
        HashSet<String> end2 = new HashSet<>();
        //keeps track of the ends, with the end having the current end, and ToEnd being added to,
        Queue<String> queue = new LinkedList<>();
        Queue<String> queue2 = new LinkedList<>();
        //the queues go through all the words doing a breadth first search
        queue.add(word1);
        end.add(word1);
        ends1.add(end);
        queue.add("");
        queue2.add(word2);
        end2.add(word2);
        ends2.add(end2);
        queue2.add("");
        //queue one goes from word1 to an intersection with queue 2 coming from word 2
        while (!queue.isEmpty() && !queue2.isEmpty()) {//while there are neighbors
            String word = queue.remove();//current neighbor is assumed
            if (word.equals("")) {//if the current levenshtein distance end is reached it adds all of ToEnd to the end
                //                  & resets the end adding the end to ends to keep a map for a quicker search later
                end = new HashSet<>();
                end.addAll(ToEnd);
                ends1.add(end);
                queue.add("");
                word = queue.remove();
                //continues to the next word
            }
            //does the same thing from the other side
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
            //if there is an intersection between the two ends there is a solution found
            if (!intersection.isEmpty()) {
                ends1.remove(ends1.size()-1);
                ends2.remove(0);//removes the two ends that intersected, and added the intersection,
                //combines ends1&2 to get the total map of the solution
                ends1.add(intersection);
                ends1.addAll(ends2);
                return ends1;
            }
            HashSet<String> currentNeighbors = getNeighborsSet(word);//the current neighbors in the assumed word
            HashSet<String> currentNeighbors2 = getNeighborsSet(CurrentWord);
            if (currentNeighbors2 != null) {//if neighbors exist for each word it adds them to the queue from both sides
                currentNeighbors2.removeAll(usedWords);
                ToEnd2.addAll(currentNeighbors2);
                queue2.addAll(currentNeighbors2);
            }
            if (currentNeighbors != null) {
                currentNeighbors.removeAll(usedWords);
                ToEnd.addAll(currentNeighbors);
                queue.addAll(currentNeighbors);
            }
            //after calculating the neighbors the word counts as used so it doesn't repeat a word
            usedWords.add(word);
            usedWords.add(CurrentWord);

        }
        return ends1;
    }

    public static String getValue(String path){//from the word1 +"->" +neighbors get the last arrow
        return path.substring(path.lastIndexOf(">")+1);//substring returns the path's current word
    }
    //method for calculating neighbors instead of pre-populating the Neighbors hashmap, still in use if pre-populating
    //as it just returns the hashmaps value at word, but returns a new hashset so it won't cause any modifications of
    //words neighbors
    public static HashSet<String> getNeighborsSet(String word) {
        //if it is already calculated it just stores and returns the value
        if (Neighbors.containsKey(word)) {
            return new HashSet<>(Neighbors.get(word));//returns a new hashset so the original neighbors don't get modified
        }//if it isn't already calculated, calculates and stores the value;
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
