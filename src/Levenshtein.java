import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;
import java.util.*;

public class Levenshtein {
    static HashSet<String> a = new HashSet<>();
    static int paths=0;//reasonable estimate for shortest path
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
    public static class queue implements java.util.Queue {
        public queue() {

        }
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @Override
        public Iterator iterator() {
            return null;
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public Object[] toArray(Object[] a) {
            return new Object[0];
        }

        @Override
        public boolean add(Object o) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean addAll(Collection c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public boolean retainAll(Collection c) {
            return false;
        }

        @Override
        public boolean removeAll(Collection c) {
            return false;
        }

        @Override
        public boolean containsAll(Collection c) {
            return false;
        }

        @Override
        public boolean offer(Object o) {
            return false;
        }

        @Override
        public Object remove() {
            return null;
        }

        @Override
        public Object poll() {
            return null;
        }

        @Override
        public Object element() {
            return null;
        }

        @Override
        public Object peek() {
            return null;
        }
    }
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(new File("dictionarySortedLength.txt"));
        while(sc.hasNext()){
          String word = sc.nextLine();
          a.add(word);
        }
        sc.close();
        //tests: cat to dog, dog to cat, puppy to dog, dog to smart, dog to quack, monkey to business
        //test 1       24815.761561 ms predicted runtime (about 25 sec)
        //              Current time: 6085 ms run (10000) times : 3ms
        //              100 times: 13 ms
        //String word1="cat";
        //String word2="dog";
        //test 2       15703.301474 ms predicted runtime (about 16 sec)
        //              Current time: 1475 ms run (10000) times : 16 ms
        //              100 times: 31 ms
        String word1="dog";
        String word2="cat";
        //test 3      185795.471987 ms predicted runtime (about 3 min 6 sec)
        //                  current time : 14996 ms
        //            100 times:
        //String word1="puppy";
        //String word2="dog";
        //test 4      189735.666912 ms predicted runtime (about 3 min 10 sec)
        //              current time : 1813 ms
        //            100 times: 574 ms
        //String word1="dog";
        //String word2="smart";
        //test 5     1732498.366852 ms predicted runtime (about 29 min)
        //          current time : 4722 ms
        //            100 times: 2461 ms
        //String word1="dog";
        //String word2="quack";
        //test 6     2814028.051959 ms predicted runtime (about 47 min)
        //          current time : 12206 ms
        //            100 times:
        //String word1="monkey";
        //String word2="business";
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
    public static void solve(String word1, String word2) throws FileNotFoundException {
        HashSet<String> usedWords = new HashSet<>();
        HashSet<String> neighbors = getNeighborsSet(word1);//gets the neighbors of the first word
        Queue<String> queue = new LinkedList<>();//queue should replace the stack
        for(String word:neighbors) {
            queue.add(word);//populates
        }
        while (!queue.isEmpty()) {//while there are neighbors
            String word = queue.remove();//current neighbor is assumed
            if(usedWords.contains(word)){
                continue;
            }
            HashSet<String> currentNeighbors = getNeighborsSet(word);//the current neighbors in the assumed word
            usedWords.add(word);
            if (!currentNeighbors.contains(word2)) {//if that neighbor isn't
                    for(String neighbor:currentNeighbors){
                        queue.add(neighbor);
                    }
            } else {
                break;
            }
        }
    }
    public static void printsolves(String word1, String word2) throws FileNotFoundException {
        HashSet<String> neighbors = getNeighborsSet(word1);//gets the neighbors of the first word
        Queue<String> queue = new LinkedList<String>();//queue should replace the stack
        for(String word:neighbors) {
            queue.add(word);//populates
        }
        int levdist=0;//queues read from head and adds to tail, stack would read from head and add to head
        while (!queue.isEmpty()) {//while there are neighbors
            String wordpath = queue.remove();//current neighbor is assumed
            String word;
            word = getValue(wordpath);
            HashSet<String> currentNeighbors = getNeighborsSet(word);//the current neighbors in the assumed word
            if (!currentNeighbors.contains(word2)) {//if that neighbor isn't
                for(String neighbor:currentNeighbors){
                    queue.add(wordpath+"->"+neighbor);
                }
            } else {
                wordpath = wordpath+"->"+word2;
                if(levdist==0){
                    levdist=getLevDist(wordpath);
                }
                if(levdist<getLevDist(wordpath)){
                    break;
                }
                paths++;
                System.out.println(word1+"->"+wordpath+" #"+paths);
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
                if(i<word.length()) {//not the last space as their isn't a char to set
                    String newWord = "";
                    letter2.set(i, g);
                    for (int k = 0; k < letter2.size(); k++) {
                        newWord = newWord + letter2.get(k);
                    }
                    if (a.contains(newWord)) {
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
                if(a.contains(newWord2)){
                    neighbors.add(newWord2);
                }
                letter2=new ArrayList<>();
                for(int k=0;k<letter.size();k++){letter2.add(letter.get(k));}
            }
        }
        return neighbors;
    }
}
