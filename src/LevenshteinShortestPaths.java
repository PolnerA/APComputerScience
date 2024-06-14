import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
/*
Name: Adam Polner
Problem:
Pseudocode:
 */
public class LevenshteinShortestPaths {
    //dictionary storing all words for either preComputing or neighbors on the fly
    static HashSet<String> Dictionary = new HashSet<>();
    //hashmap to instantly return hashset of neighbors when you input a string
    static HashMap<String,HashSet<String>> Neighbors= new HashMap<>();
    //pathNumber for the number of paths as it prints all paths
    static int pathNumber=1;
    //pre-Computing to add all neighbors to a file
    public static void preCompute() throws IOException {
        //files dictionaryWithNeighbors.txt for putting the neighbors of each word in a file
        //"dictionarySortedLength.txt" is a dictionary for all the words the program uses
        Scanner sc2 = new Scanner(new File("dictionarySortedLength.txt"));
        File neighbor = new File("dictionaryWithNeighbors");
        neighbor.createNewFile();
        FileWriter writer = new FileWriter(neighbor);
        int count=0;
        while(sc2.hasNext()){ //_0_1_2_3_4_
            String word = sc2.nextLine();
            //for each word in the dictionary
            count++;
            //above count and underneath print statement for progress to print as a double with 1 meaning it's complete
            System.out.println((double) count/(double) Dictionary.size());
            char[] letters=word.toCharArray();
            //letters splits the word into characters
            ArrayList<Character> letter = new ArrayList<>();
            ArrayList<Character> letter2 = new ArrayList<>();
            ArrayList<Character> letter3 = new ArrayList<>();
            //letter 1, 2, and 3 act as the different changes to the word to get to the neighbors
            for(int i=0;i< letters.length;i++){
                letter.add(letters[i]);
                letter2.add(letters[i]);
                letter3.add(letters[i]);
            }
            //writes the word to the file
            writer.write(word);
            for(int i =0; i<=word.length();i++){
                //goes through the entire word <= because of the extra letter add-on at the end
                if(i<word.length()){
                    //letter 3 removes the current letter at i
                    letter3.remove(i);
                    String newWord="";
                    for(int j=0;j<letter3.size();j++){
                        newWord = newWord+letter3.get(j);
                    }
                    if (Dictionary.contains(newWord)) {//checks if new word is a neighbor
                        writer.write("-" + newWord);//if it is a word it is added to the neighbors
                    }
                    letter3=new ArrayList<>();//resets letter3 to the original word
                    for(int k=0;k<letter.size();k++){letter3.add(letter.get(k));}
                }
                for(int j=0;j<26;j++){
                    char g = (char) ('a' + j);
                    if(i<word.length()) {//can only replace where there is a character
                        String newWord = "";//for each letter it replaces the character at i
                        //checks if the created word is a word, if it is, it is written to the file
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
                    letter2=new ArrayList<>();//does the same thing except with an addition of a letter
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
        while (sc2.hasNext()){//goes through the neighbors of each word
            String Line =sc2.nextLine();
            String[] neighborsLine = Line.split("-");
            HashSet<String> neighborsSet = new HashSet<>();
            for(int i=1;i<neighborsLine.length;i++){
                neighborsSet.add(neighborsLine[i]);
            }
            Neighbors.put(neighborsLine[0],neighborsSet);//adds all the neighbors to a hashmap
        }
        sc2.close();
        Scanner sc = new Scanner(new File("dictionarySortedLength.txt"));
        while(sc.hasNext()){
            String word = sc.nextLine();
            Dictionary.add(word);//adds all words in the dictionary to the dictionary hashset
        }
        sc.close();
        //uses pre-computed neighbors;
        //tests: cat to dog, dog to cat, puppy to dog, dog to smart, dog to quack, monkey to business
        //shortest paths: 6,     6     ,      38     ,      51     ,      107    ,       1
        //test 1
        //String word1="cat";
        //String word2="dog";
        //test 2
        //String word1="dog";
        //String word2="cat";
        //test 3
        //String word1="puppy";
        //String word2="dog";
        //test 4
        //String word1="dog";
        //String word2="smart";
        //test 5
        //String word1="dog";
        //String word2="quack";
        //test 6
        String word1="monkey";
        String word2="business";
        printsolves(word1,word2);
    }
    public static ArrayList<HashMap<String,ArrayList <String>>> GetMap(String word1,String word2){
        //gets the map of the string and it's parents
        ArrayList<HashMap<String,ArrayList<String>>> map=new ArrayList<>();
        HashMap<String,ArrayList<String>> wordmap =new HashMap<>();
        wordmap.put(word1,new ArrayList<>());
        map.add(wordmap);
        //adds the beginning of the paths to the returned map
        for(int i=0;i<map.size();i++){
            //goes through the map calculating its neighbors until it contains the second word
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
    public static void printsolves(String word1, String word2) {
        //gets the map of word1 to 2 & word2 to 1
        // instead of one map being big on one end (like 1 or 2), get the intersection
        // this way the map will have a lot less possible opitions
        ArrayList<HashMap<String,ArrayList<String>>> map1 = GetMap(word1,word2);
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
        //from this intersection you can get a set
        ArrayList<HashSet<String>> IntersectionSet = new ArrayList<>();
        for (int i=0;i<Intersection.size();i++){
            HashMap<String,ArrayList<String>> PossibleWordsMap = Intersection.get(i);
            HashSet<String> PossibleWordsHashSet = new HashSet<>();
            PossibleWordsHashSet.addAll(PossibleWordsMap.keySet());
            IntersectionSet.add(PossibleWordsHashSet);
        }
        //once you get a set generatePaths would get all possible ways to go from one end to the other
        generatePaths(IntersectionSet);
    }

    public static void generatePaths(ArrayList<HashSet<String>> map){
        ArrayList<String[]> traversablemap = new ArrayList<>();
        for(HashSet<String> set:map){
            traversablemap.add(set.toArray(new String[0]));
        }
        //once you have the map as a more easily traversable String array
        //you can remove the two ends which are always word1 and word2
        String word1=traversablemap.remove(0)[0];
        String word2 =traversablemap.remove(traversablemap.size()-1)[0];
        int[] indices = new int[traversablemap.size()];//gets an int array to show the combinations of strings in a path
        //calculates the number of total combinations
        int totalcombinations=1;
        for(HashSet<String> set: map){
            totalcombinations*= set.size();
        }
        for(int i=1;i<totalcombinations;i++){
            //for each combination, gets the path
            ArrayList<String> path =getPath(indices,traversablemap);
            //checks if the path is possible
            if(isValidPath(path)){
                //adds the sides to it
                path.add(word2);
                path.add(0,word1);
                //prints the path
                System.out.println(path+" #"+pathNumber);
                pathNumber++;
                //counts it as a found path with a pathNumber
            }
            //increments the indices for a new map combination
            indices=Increment(traversablemap,indices);
        }
        //for the final indices it gets one last path and prints it out
        ArrayList<String> path =getPath(indices,traversablemap);
        if(isValidPath(path)){
            path.add(word2);
            path.add(0,word1);
            System.out.println(path+" #"+pathNumber);
        }
    }
    public static int[] Increment(ArrayList<String[]> map,int[]indices){
        //counts up indices like 0,0,0 -> 0,0,1 -> 0,1,0 -> 0,1,1
        int Index=indices.length-1;//max is set to index as that is what changes the most
        indices[Index]++;//ups the last index
        while(indices[Index]%map.get(Index).length==0){//if it overflows the index
            indices[Index]=0;//the current index is set to 0
            indices[Index-1]++;//the more outer index is increased
            Index--;//index is set to one less to check the now increased outer index for if it goes over
        }
        return indices;//returns the new indices
    }
    public static ArrayList<String> getPath(int[] key,ArrayList<String[]> map){
        //returns a given combination of the map with each key
        ArrayList<String> list = new ArrayList<>();
        for(int i=0;i< map.size();i++){
            //for each element in the map it gets the key at the corresponding index
            list.add(map.get(i)[key[i]]);
        }
        return list;
    }
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
    public static boolean isValidPath(ArrayList<String> path){//checks if the given path is possible
        //as the algorithm just looks at all the combinations in the path it will still create illegitimate paths
        //this would return if it is a valid path to check if it works or not
        for(int i=0;i<path.size()-1;i++){
            String word=path.get(i);
            String nextword = path.get(i+1);
            HashSet<String> neighbors = getNeighborsSet(word);
            //for each word, does its neighbors contain the next word
            if(!neighbors.contains(nextword)){//if not:
                return false;//it isn't a valid path
            }
        }//if each of the words are neighbors it is a valid path
        return true;
    }
}
