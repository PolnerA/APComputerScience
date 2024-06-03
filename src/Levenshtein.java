import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Levenshtein {
    static HashSet<String> Dictionary = new HashSet<>();
    public static class Neighbors{
        public HashSet<Neighbors> neighbors;
        public int[] OutsideNeighbors;
        public String word;
        public Neighbors(String word){
            this.word=word;
        }
    }
    HashMap<String,Integer> a ;//same as dictionary arr
    //type of datatype to use first it has to store a value (the word), and it needs to point to other values (neighbors)
    //Hashmap of a string would store the values with an integer array to store the different neighbors
    static String[] DictionaryArr;//these two arrays are what is looked at try to convert to a single hash thing
    //
    static ArrayList<String>[] neighborsArray;
    static int paths=0;//reasonable estimate for shortest path
    static final boolean getPaths = false;
    ArrayList<String> from = new ArrayList<>();

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
        DictionaryArr=new String[370099];
        while(sc.hasNext()){
          String word = sc.nextLine();
          Dictionary.add(word);
          DictionaryArr[index]=word;
          index++;
        }
        sc.close();
        neighborsArray = new ArrayList[Dictionary.size()];
        Scanner sc2 = new Scanner(new File("dictionaryWithNeighbors"));
        int count=0;
        while (sc2.hasNext()){//goes through with the neighbors
            String word = sc2.nextLine();
            String[] neighborsarr =word.split("-");
            Neighbors nOfWord = new Neighbors(neighborsarr[0]);
            ArrayList<String> neighbors = new ArrayList<>();
            for(int i=1;i<neighborsarr.length;i++){
                neighbors.add(neighborsarr[i]);
            }
            neighborsArray[count]=neighbors;
            count++;
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
        //              Current time: 67 ms run (10000) times : 3ms
        //              100 times: 12 ms | all paths work
        //String word1="cat";
        //String word2="dog";
        //test 2       15703.301474 ms predicted runtime (about 16 sec)
        //              Current time: 284 ms run (10000) times : 4 ms
        //              100 times: 31 ms | all paths work
        //String word1="dog";
        //String word2="cat";
        //test 3      185795.471987 ms predicted runtime (about 3 min 6 sec)
        //                  current time : 14996 ms
        //            100 times:   | all paths
        //String word1="puppy";
        //String word2="dog";
        //test 4      189735.666912 ms predicted runtime (about 3 min 10 sec)
        //              current time : 1813 ms
        //            100 times: 574 ms | all paths work without string1->string2 throws out of memory pre finishing
        String word1="dog";
        String word2="smart";
        //test 5     1732498.366852 ms predicted runtime (about 29 min)
        //          current time : 4722 ms
        //            100 times: 2461 ms | all paths give java.lang.OutOfMemoryError
        //String word1="dog";
        //String word2="quack";
        //test 6     2814028.051959 ms predicted runtime (about 47 min)
        //          current time : 12206 ms
        //            100 times:
        //String word1="monkey";
        //String word2="business";
        //to solve out of memory improve the maps to smaller sizes and use vm options: -Xlog:gc to print the garbage collector
        if(!getPaths){
            Long sum = Long.valueOf(0);
            int num = 1;
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
        HashSet<String> usedWords = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(word1);
        while (!queue.isEmpty()) {//while there are neighbors
            String word = queue.remove();//current neighbor is assumed
            HashSet<String> currentNeighbors = getNeighborsSet(word);//the current neighbors in the assumed word
            usedWords.add(word);
            currentNeighbors.removeAll(usedWords);
            if (!currentNeighbors.contains(word2)) {
                queue.addAll(currentNeighbors);
            } else {
                return;
            }
        }
    }
    public static void printsolves(String word1, String word2) {//java.lang.OutOfMemoryError
        Queue<String> queue = new LinkedList<String>();//queue created once and used
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
            String word = getValue(wordpath);
            HashSet<String> currentNeighbors = getNeighborsSet(word);//the current neighbors in the assumed word
            if (!currentNeighbors.contains(word2)) {//if that neighbor isn't
                for(String neighbor:currentNeighbors){
                    queue.add(wordpath+"->"+neighbor);
                }
            } else {
                wordpath = wordpath+"->"+word2;
                paths++;
                System.out.println(word1+"->"+wordpath+" #"+paths);
                found=true;

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
}
