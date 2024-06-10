import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

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

        //uses pre-computed neighbors;
        //tests: cat to dog, dog to cat, puppy to dog, dog to smart, dog to quack, monkey to business
        //test 1       24815.761561 ms predicted runtime (about 25 sec)
        //              Current time: 2 ms run (10000) times : 0ms
        //              100 times: 0 ms | all paths work
        //String word1 = "cat";
        //String word2 = "dog";
        //test 2       15703.301474 ms predicted runtime (about 16 sec)
        //              Current time: 2 ms run (10000) times : 0 ms
        //              100 times: 0 ms | all paths work
        //String word1="dog";
        //String word2="cat";
        //test 3      185795.471987 ms predicted runtime (about 3 min 6 sec)
        //                  current time : 13 ms
        //            100 times: 5 ms  | all paths throws out of memory, all past this do too
        //String word1="puppy";
        //String word2="dog";
        //test 4      189735.666912 ms predicted runtime (about 3 min 10 sec)
        //              current time : 12 ms
        //            100 times: 6 ms | all paths work without string1->string2
        //String word1="dog";
        //String word2="smart";
        //test 5     1732498.366852 ms predicted runtime (about 29 min)
        //          current time : 50 ms
        //            100 times: 20 ms | all paths give java.lang.OutOfMemoryError
        //String word1="dog";
        //String word2="quack";
        //test 6     2814028.051959 ms predicted runtime (about 47 min)
        //          current time : 95 ms 10000 times: 5 ms
        //            100 times: 7 ms
        String word1="monkey";
        String word2="business";
        ////to solve out of memory improve the maps to smaller sizes and use vm options: -Xlog:gc to print the garbage collector
        Long sum = Long.valueOf(0);
        int num = 10000;
        for (int i = 0; i < num; i++) {
            Long pre = System.currentTimeMillis();
            solve(word1, word2);
            Long post = System.currentTimeMillis();
            sum += (post - pre);
        }
        System.out.println("Time: " + (sum / num) + " ms");
    }

    public static void solve(String word1, String word2) {//solving with the neighbor ends until they meet
        HashSet<String> usedWords = new HashSet<>();//not repeating a word helps keep the out of memory error away
        HashSet<String> end = new HashSet<>();
        HashSet<String> ToEnd = new HashSet<>();
        HashSet<String> ToEnd2 = new HashSet<>();
        HashSet<String> end2 = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        Queue<String> queue2 = new LinkedList<>();
        queue.add(word1);
        end.add(word1);
        queue.add("");
        queue2.add(word2);
        end2.add(word2);
        queue2.add("");

        while (!queue.isEmpty() && !queue2.isEmpty()) {//while there are neighbors
            String word = queue.remove();//current neighbor is assumed
            if (word.equals("")) {
                end = new HashSet<>();
                end.addAll(ToEnd);
                queue.add("");
                word = queue.remove();
            }
            String CurrentWord = queue2.remove();
            if (CurrentWord.equals("")) {
                end2 = new HashSet<>();
                end2.addAll(ToEnd2);
                queue2.add("");
                CurrentWord = queue2.remove();
            }
            HashSet<String> intersection = new HashSet<>(end);
            intersection.retainAll(end2);
            if (!intersection.isEmpty()) {
                return;
            }
            HashSet<String> currentNeighbors = Neighbors.get(word);//the current neighbors in the assumed word
            HashSet<String> currentNeighbors2 = Neighbors.get(CurrentWord);
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
    }
    //method for calculating neighbors instead of pre-populating the Neighbors hashmap
    public static HashSet<String> getNeighborsSet(String word) {
        //if it is already calculated it just stores and returns the value
        if (Neighbors.containsKey(word)) {
            return Neighbors.get(word);
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
                newWord3 = newWord3+letter3.get(i);
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
        return neighbors;
    }
}
