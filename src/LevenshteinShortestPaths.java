import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class LevenshteinShortestPaths {
    static HashSet<String> Dictionary = new HashSet<>();
    //type of datatype to use first it has to store a value (the word), and it needs to point to other values (neighbors)
    //Hashmap of a string would store the values with an integer array to store the different neighbors
    //
    static HashMap<String,HashSet<String>> Neighbors= new HashMap<>();
    static int pathNumber=1;//reasonable estimate for shortest path

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
            ArrayList<Character> letter3 = new ArrayList<>();
            for(int i=0;i< letters.length;i++){
                letter.add(letters[i]);
                letter2.add(letters[i]);
                letter3.add(letters[i]);
            }
            writer.write(word);
            for(int i =0; i<=word.length();i++){
                if(i<word.length()){
                    letter3.remove(i);
                    String newWord="";
                    for(int j=0;j<letter3.size();j++){
                        newWord = newWord+letter3.get(j);
                    }
                    if (Dictionary.contains(newWord)) {
                        writer.write("-" + newWord);
                    }
                    letter3=new ArrayList<>();
                    for(int k=0;k<letter.size();k++){letter3.add(letter.get(k));}
                }
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
        Scanner sc2 = new Scanner(new File("dictionaryWithNeighbors"));
        while (sc2.hasNext()){//goes through with the neighbors
            String Line =sc2.nextLine();
            String[] neighborsLine = Line.split("-");
            HashSet<String> neighborsSet = new HashSet<>();
            for(int i=1;i<neighborsLine.length;i++){
                neighborsSet.add(neighborsLine[i]);
            }
            Neighbors.put(neighborsLine[0],neighborsSet);
            //if there is a word that has no neighbors in it's neighbors

        }
        sc2.close();
        Scanner sc = new Scanner(new File("dictionarySortedLength.txt"));
        while(sc.hasNext()){
            String word = sc.nextLine();
            Dictionary.add(word);
        }
        sc.close();
        //uses pre-computed neighbors;
        //tests: cat to dog, dog to cat, puppy to dog, dog to smart, dog to quack, monkey to business
        //shortest paths: 6,     6     ,      38     ,      51     ,      107    ,       1
        //test 1       24815.761561 ms predicted runtime (about 25 sec)
        //              Current time: 2 ms run (10000) times : 0ms
        //              100 times: 0 ms | all paths work
        //String word1="cat";
        //String word2="dog";
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
        String word1="dog";
        String word2="smart";
        //test 5     1732498.366852 ms predicted runtime (about 29 min)
        //          current time : 50 ms
        //            100 times: 20 ms | all paths give java.lang.OutOfMemoryError
        //String word1="dog";
        //String word2="quack";
        //test 6     2814028.051959 ms predicted runtime (about 47 min)
        //          current time : 95 ms
        //            100 times: 8 ms
        //String word1="monkey";
        //String word2="business";
        printsolves(word1,word2);
    }
    public static ArrayList<HashMap<String,ArrayList <String>>> GetMap(String word1,String word2){//find a way to trim down the map, first instance of anything in the second map?
        ArrayList<HashMap<String,ArrayList<String>>> map=new ArrayList<>();
        HashMap<String,ArrayList<String>> wordmap =new HashMap<>();
        wordmap.put(word1,new ArrayList<>());
        map.add(wordmap);
        for(int i=0;i<map.size();i++){
            HashMap<String,ArrayList<String>> currentMap =map.get(i);
            HashMap<String,ArrayList<String>> end = new HashMap<>();
            HashSet<String> n;
            for(String word:currentMap.keySet()){
                n=getNeighborsSet(word);
                if(n!=null){
                    for(String neighbor:n){
                        if(!end.keySet().contains(neighbor)){
                            ArrayList<String> parents = new ArrayList<>();
                            parents.add(word);
                            end.put(neighbor,parents);
                        }else{
                            ArrayList<String> parents =end.get(neighbor);
                            parents.add(word);
                            end.put(neighbor,parents);
                        }
                    }
                }
            }
            map.add(end);
            if(map.get(i+1).keySet().contains(word2)){
                return map;
            }
        }
        return map;
    }
    public static void printsolves(String word1, String word2) {//java.lang.OutOfMemoryError as there are
        ArrayList<HashMap<String,ArrayList<String>>> map1 = GetMap(word1,word2);//
        System.out.println();
        ArrayList<HashMap<String,ArrayList<String>>> map2 = GetMap(word2,word1);
        ArrayList<HashMap<String,ArrayList<String>>> Intersection = new ArrayList<>();
        for(int i=0;i<map1.size();i++){
            HashSet<String> SetIntersection = new HashSet<>(map1.get(i).keySet());
            SetIntersection.retainAll(map2.get(map2.size()-1-i).keySet());
            //get all the things
            HashMap<String,ArrayList<String>> currentmap = new HashMap<>();
            for(String word:SetIntersection){
                currentmap.put(word,map1.get(i).get(word));
                currentmap.put(word,map2.get(i).get(word));
            }
            Intersection.add(currentmap);

        }
        ArrayList<HashSet<String>> IntersectionSet = new ArrayList<>();
        for (int i=0;i<Intersection.size();i++){
            HashMap<String,ArrayList<String>> PossibleWordsMap = Intersection.get(i);
            HashSet<String> PossibleWordsHashSet = new HashSet<>();
            PossibleWordsHashSet.addAll(PossibleWordsMap.keySet());
            IntersectionSet.add(PossibleWordsHashSet);
        }
        generatePaths(IntersectionSet);
    }
    public static void generatePaths(ArrayList<HashSet<String>> map){
        int[] indices = new int[map.size()];
        int totalcombinations=1;
        for(HashSet<String> set: map){
            totalcombinations*= set.size();
        }
        boolean skip=false;
        HashSet<ArrayList<String>> paths = new HashSet<>();
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
                    if(getNeighborsSet(word).contains(setArray[index])){
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
            if(!paths.contains(path)){
                if(isValidPath(path)){
                    System.out.println(path+" #"+pathNumber);
                    paths.add(path);
                    pathNumber++;
                }
            }
        }
    }
    public static HashSet<String> getNeighborsSet(String word) {
        //if it is already calculated it just stores and returns the value
        if (Neighbors.containsKey(word)) {
            return new HashSet<>(getNeighborsSet(word));
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
}
