import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Levenshtein {
    static HashSet<String> Dictionary = new HashSet<>();
    //type of datatype to use first it has to store a value (the word), and it needs to point to other values (neighbors)
    //Hashmap of a string would store the values with an integer array to store the different neighbors
    //
    static HashMap<String,HashSet<String>> neighbors= new HashMap<>();
    static ArrayList<ArrayList<String>> paths = new ArrayList<>();
    static int pathNumber=0;//reasonable estimate for shortest path
    static final boolean getPaths = false;

    public static void preCompute() throws IOException {
        Scanner sc2 = new Scanner(new File("dictionarySortedLength.txt"));
        File neighbor = new File("dictionaryWithNeighbors");
        neighbor.createNewFile();
        FileWriter writer = new FileWriter(neighbor);
        int count=0;
        while(sc2.hasNext()){ //_0_1_2_3_4_
            String word = sc2.nextLine();
            count++;
            System.out.println((double) count/(double) Dictionary.size());
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
                        if (Dictionary.contains(newWord)) {
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
                    if(Dictionary.contains(newWord2)){
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
        int index=0;
        while(sc.hasNext()){
          String word = sc.nextLine();
          Dictionary.add(word);
          index++;
        }
        sc.close();
        Scanner sc2 = new Scanner(new File("dictionaryWithNeighbors"));
        while (sc2.hasNext()){//goes through with the neighbors
            String Line =sc2.nextLine();
            String[] neighborsLine = Line.split("-");
            HashSet<String> neighborsSet = new HashSet<>();
            for(int i=1;i<neighborsLine.length;i++){
                neighborsSet.add(neighborsLine[i]);
            }
            neighbors.put(neighborsLine[0],neighborsSet);
        }
        System.out.println("");
        //for(int i=0;i<neighborsArray.length;i++){
        //    Neighbors n =neighborsArray[i];
        //    n.OutsideNeighbors=new int[n.neighbors.size()];
        //    for(Neighbors word:n.neighbors){
        //        for(int j=0;j<neighborsArray.length;j++){
        //            //if(neighborsArray[j])
        //        }
        //    }
        //}
        //uses pre-computed neighbors;
        //tests: cat to dog, dog to cat, puppy to dog, dog to smart, dog to quack, monkey to business
        //shortest paths: 6,     6     ,      38     ,      51     ,      107    ,       1
        //test 1       24815.761561 ms predicted runtime (about 25 sec)
        //              Current time: 0 ms run (10000) times : 3ms
        //              100 times: 0 ms | all paths work
        //String word1="cat";
        //String word2="dog";
        //test 2       15703.301474 ms predicted runtime (about 16 sec)
        //              Current time: 0 ms run (10000) times : 4 ms
        //              100 times: 1 ms | all paths work
        //String word1="dog";
        //String word2="cat";
        //test 3      185795.471987 ms predicted runtime (about 3 min 6 sec)
        //                  current time : 167 ms
        //            100 times: 137 ms  | all paths throws out of memory, all past this do too
        //String word1="puppy";
        //String word2="dog";
        //test 4      189735.666912 ms predicted runtime (about 3 min 10 sec)
        //              current time : 17 ms
        //            100 times: 14 ms | all paths work without string1->string2
        //String word1="dog";
        //String word2="smart";
        //test 5     1732498.366852 ms predicted runtime (about 29 min)
        //          current time : 65 ms
        //            100 times: 42 ms | all paths give java.lang.OutOfMemoryError
        //String word1="dog";
        //String word2="quack";
        //test 6     2814028.051959 ms predicted runtime (about 47 min)
        //          current time : 121 ms
        //            100 times: 103 ms
        String word1="monkey";
        String word2="business";
        //to solve out of memory improve the maps to smaller sizes and use vm options: -Xlog:gc to print the garbage collector
        if(!getPaths){
            Long sum = Long.valueOf(0);
            int num = 100;
            for(int i=0;i<num;i++){
                Long pre = System.currentTimeMillis();
                solve(word1,word2);
                Long post = System.currentTimeMillis();
                sum +=(post-pre);
            }
            System.out.println("Time: "+(sum/num)+" ms");
        }else{
            printsolves(word1,word2);
        }
    }
    public static void solve(String word1, String word2)  {
        HashSet<String> usedWords = new HashSet<>();//not repeating a word helps keep the out of memory error away
        Queue<String> queue = new LinkedList<>();
        queue.add(word1);
        while (!queue.isEmpty()) {//while there are neighbors
            String word = queue.remove();//current neighbor is assumed
            HashSet<String> currentNeighbors = neighbors.get(word);//the current neighbors in the assumed word
            usedWords.add(word);
            currentNeighbors.removeAll(usedWords);
            if (!currentNeighbors.contains(word2)) {
                queue.addAll(currentNeighbors);
            } else {
                return;
            }
        }

    }
    /*
    public static void printsolves(String word1, String word2){//implement into this with the different paths being used only in a self-contained "path"
        Queue<String> queue = new LinkedList<>();
        queue.add(word1);
        while (!queue.isEmpty()) {//while there are neighbors
            String word = queue.remove();//current neighbor is assumed
            HashSet<String> currentNeighbors = neighbors.get(word);//the current neighbors in the assumed word
            if (!currentNeighbors.contains(word2)) {
                queue.addAll(currentNeighbors);
            } else {
                return;
            }
        }
    }

     */

    public static void printsolves(String word1, String word2) {//java.lang.OutOfMemoryError as there are
        //visualize 2d list as a binary tree
        //     word1n1 word1n2 word1n3//copy for each neighbor and add them to the list
        //keep count of the traversal i for bottom and j for top

        // list word1
        Queue<String> queue = new LinkedList<String>();//queue created once and used
        Queue<Stack<String>> paths = new LinkedList<>();
        boolean found=false;//found boolean helps with the final line
        queue.add(word1);//populates    //w ""   try to get a sentinel of empty string for each distance
        queue.add("");                  // 1 2 3 4 5 ""
                                        //11 12 13 14 15"" 21 22 23 24 ""
        while (!queue.isEmpty()) {//while there are neighbors goes through the stack
            String wordpath = queue.remove();//current neighbor is assumed
            if(wordpath.equals("")){//if it hits an end of line and shortest path is found it quits as others are longer
                if(found){return;}
                queue.add("");//skips over sentinel otherwise and adds a new one to the end line marking the neighbors end
                continue;
            }
            //String word = getValue(wordpath);
            HashSet<String> currentNeighbors = neighbors.get(wordpath);//the current neighbors in the assumed word
            if (!currentNeighbors.contains(word2)) {//if that neighbor isn't
                if(!found){//only add neighbors if the shortest isn't on this level
                    for(String neighbor:currentNeighbors){
                        //store as a path of strings so you know to eliminate paths that come after the path
                        queue.add(neighbor);
                    }
                }
            } else {
                wordpath = wordpath+"->"+word2;
                pathNumber++;
                System.out.println(word1+"->"+wordpath+" #"+pathNumber);
                found=true;

            }
        }
    }
    public static String getValue(String path){
        //from the word1 +"->" +neighbors get the last arrow thing
        return path.substring(path.lastIndexOf(">")+1);
    }
    /*
    public static HashSet<String> getNeighborsSet(String word) {
        ArrayList<Character> letter = new ArrayList<>();
        ArrayList<Character> letter2 = new ArrayList<>();
        char[] letters = word.toCharArray();
        for(int i=0;i<letters.length;i++){
            letter.add(letters[i]);
            letter2.add(letters[i]);
        }
        HashSet<String> neighbors = new HashSet<>();
        for(int i =0; i<=word.length();i++){//for each space/character
            for(int j=0;j<26;j++){
                char g = (char) ('a' + j);
                if(i<word.length()) {//not the last space as there isn't a char to set
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
                }
                letter2=new ArrayList<>();
                for(int k=0;k<letter.size();k++){letter2.add(letter.get(k));}
                String newWord2="";
                letter2.add(i,g);
                for(int k=0;k<letter2.size();k++){
                    newWord2= newWord2 + letter2.get(k);
                }
                if(Dictionary.contains(newWord2)){
                    neighbors.add(newWord2);
                }
                letter2=new ArrayList<>();
                for(int k=0;k<letter.size();k++){letter2.add(letter.get(k));}
            }
        }
        return neighbors;
    }
     */
}
