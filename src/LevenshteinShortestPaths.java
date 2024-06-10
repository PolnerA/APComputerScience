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
    static int pathNumber=0;//reasonable estimate for shortest path

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
        //preCompute();

        //System.out.println("");
        //uses pre-computed neighbors;
        //tests: cat to dog, dog to cat, puppy to dog, dog to smart, dog to quack, monkey to business
        //shortest paths: 6,     6     ,      38     ,      51     ,      107    ,       1
        //test 1       24815.761561 ms predicted runtime (about 25 sec)
        //              Current time: 2 ms run (10000) times : 0ms
        //              100 times: 0 ms | all paths work
        String word1="cat";
        String word2="dog";
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
        //          current time : 95 ms
        //            100 times: 8 ms
        //String word1="monkey";
        //String word2="business";
        ////to solve out of memory improve the maps to smaller sizes and use vm options: -Xlog:gc to print the garbage collector
        printsolves(word1,word2);
    }
    public static void solve(String word1, String word2)  {//solving with the neighbor ends until they meet
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

        while (!queue.isEmpty()&&!queue2.isEmpty()) {//while there are neighbors
            String word = queue.remove();//current neighbor is assumed
            if(word.equals("")){
                end=new HashSet<>();
                end.addAll(ToEnd);
                queue.add("");
                word =queue.remove();
            }
            String CurrentWord = queue2.remove();
            if(CurrentWord.equals("")){
                end2=new HashSet<>();
                end2.addAll(ToEnd2);
                queue2.add("");
                CurrentWord =queue2.remove();
            }
            HashSet<String> intersection = new HashSet<>(end);
            intersection.retainAll(end2);
            if (!intersection.isEmpty()) {
                return;
            }
            HashSet<String> currentNeighbors = Neighbors.get(word);//the current neighbors in the assumed word
            HashSet<String> currentNeighbors2 = Neighbors.get(CurrentWord);
            if(currentNeighbors2!=null){
                currentNeighbors2.removeAll(usedWords);
                ToEnd2.addAll(currentNeighbors2);
                queue2.addAll(currentNeighbors2);
            }if(currentNeighbors!=null){
                currentNeighbors.removeAll(usedWords);
                ToEnd.addAll(currentNeighbors);
                queue.addAll(currentNeighbors);
            }
            usedWords.add(word);
            usedWords.add(CurrentWord);

        }
    }

    //public static void solve(String word1, String word2)  {//solving by adding to queue until the path is found
    //    HashSet<String> usedWords = new HashSet<>();//not repeating a word helps keep the out of memory error away
    //    Queue<String> queue = new LinkedList<>();
    //    queue.add(word1);
    //    while (!queue.isEmpty()) {//while there are neighbors
    //        String word = queue.remove();//current neighbor is assumed
    //        HashSet<String> currentNeighbors = neighbors.get(word);//the current neighbors in the assumed word
    //        usedWords.add(word);
    //        currentNeighbors.removeAll(usedWords);
    //        if (!currentNeighbors.contains(word2)) {
    //            queue.addAll(currentNeighbors);
    //        } else {
    //            return;
    //        }
    //    }
    //}


    // public static void printsolves(String word1, String word2) {//implement into this with the different paths being used only in a self-contained "path"
    //     HashSet<String> usedWords = new HashSet<>();//not repeating a word helps keep the out of memory error away
    //     HashSet<String> end = new HashSet<>();
    //     HashSet<String> ToEnd = new HashSet<>();
    //     HashSet<String> ToEnd2 = new HashSet<>();
    //     HashSet<String> end2 = new HashSet<>();
    //     Queue<String> queue = new LinkedList<>();
    //     Queue<String> queue2 = new LinkedList<>();
    //     ArrayList<HashSet<String>> map1 = new ArrayList<>();
    //     ArrayList<HashSet<String>> map2 = new ArrayList<>();
    //     queue.add(word1);
    //     end.add(word1);
    //     map1.add(end);
    //     queue.add("");
    //     queue2.add(word2);
    //     end2.add(word2);
    //     map2.add(end2);
    //     queue2.add("");
//
    //     while (!queue.isEmpty() && !queue2.isEmpty()) {//while there are neighbors
    //         String word = queue.remove();//current neighbor is assumed
    //         if (word.equals("")) {
    //             end = new HashSet<>();
    //             end.addAll(ToEnd);
    //             map1.add(end);
    //             queue.add("");
    //             word = queue.remove();
    //         }
    //         String CurrentWord = queue2.remove();
    //         if (CurrentWord.equals("")) {
    //             end2 = new HashSet<>();
    //             end2.addAll(ToEnd2);
    //             map2.add(0,end2);
    //             queue2.add("");
    //             CurrentWord = queue2.remove();
    //         }
    //         HashSet<String> intersection = new HashSet<>(end);
    //         intersection.retainAll(end2);
    //         if (!intersection.isEmpty()) {
    //             CalculatePath(word1,word2,CompleteMap(map1,map2));
    //             return;
    //             //pivot to calculate the paths
    //         }
    //         HashSet<String> currentNeighbors = neighbors.get(word);//the current neighbors in the assumed word
    //         HashSet<String> currentNeighbors2 = neighbors.get(CurrentWord);
    //         if (currentNeighbors2 != null) {
    //             currentNeighbors2.removeAll(usedWords);
    //             ToEnd2.addAll(currentNeighbors2);
    //             queue2.addAll(currentNeighbors2);
    //         }
    //         if (currentNeighbors != null) {
    //             currentNeighbors.removeAll(usedWords);
    //             ToEnd.addAll(currentNeighbors);
    //             queue.addAll(currentNeighbors);
    //         }
    //         usedWords.add(word);
    //         usedWords.add(CurrentWord);
    //     }
    //     System.out.println("no intersect?");
    // }
    public static ArrayList<HashSet<String>> CompleteMap(ArrayList<HashSet<String>> mapPart1,ArrayList<HashSet<String>> mapPart2){
        HashSet<String> Intersection = new HashSet<>(mapPart1.get(mapPart1.size()-1));//clones the ending of the first map part
        Intersection.retainAll(mapPart2.get(0));//keeps the intersection of the two ends
        ArrayList<HashSet<String>> result = new ArrayList<>();
        result.add(Intersection);
        for(int i=2;i<=mapPart1.size();i++){
            HashSet<String> set = mapPart1.get(mapPart1.size()-i);
            for(String word:set){
                HashSet<String> n=Neighbors.get(word);
                n.retainAll(Intersection);
                if(n.isEmpty()){
                    set.remove(word);
                }
            }
            Intersection=set;
            mapPart1.set(mapPart1.size()-i,set);
        }
        result.addAll(0,mapPart1);
        Intersection=result.get(result.size()-1);
        for(int i=1;i<mapPart2.size()-1;i++){
            HashSet<String> set = mapPart1.get(i);
            for(String word:set){
                HashSet<String> n=Neighbors.get(word);
                n.retainAll(Intersection);
                if(n.isEmpty()){
                    set.remove(word);
                }
            }
            Intersection=set;
            mapPart2.set(i,set);
        }
        result.addAll(mapPart2);
        return result;
    }
    public static void CalculatePath(String word1,String word2, ArrayList<HashSet<String>> Map){
        System.out.println("done");
    }

    public static ArrayList<HashSet<String>> GetMap(String word1,String word2){
        Queue<String> queue = new LinkedList<>();
        HashSet<String> End = new HashSet<>();
        HashSet<String> ToEnd = new HashSet<>();
        ArrayList<HashSet<String>> map=new ArrayList<>();
        queue.add(word1);
        boolean solved=false;
        queue.add("");
        End.add(word1);
        map.add(End);
        while (!queue.isEmpty()) {//while there are neighbors
            String word = queue.remove();//current neighbor is assumed
            if(word.equals("")){
                End=new HashSet<>();
                End.addAll(ToEnd);
                map.add(End);
                queue.add("");
                word =queue.remove();
                if(solved){
                    return map;
                }
            }
            HashSet<String> currentNeighbors = Neighbors.get(word);//the current neighbors in the assumed word

            if (!currentNeighbors.contains(word2)) {
                queue.addAll(currentNeighbors);
                ToEnd.addAll(currentNeighbors);
            } else {
                solved=true;
            }
        }
        return map;
    }

    public static void printsolves(String word1, String word2) {//java.lang.OutOfMemoryError as there are
        ArrayList<HashSet<String>> map1 = GetMap(word1,word2);
        ArrayList<HashSet<String>> map2 = GetMap(word2,word1);
        ArrayList<HashSet<String>> Intersection = new ArrayList<>();
        for(int i=0;i<map1.size();i++){
            HashSet<String> SetIntersection = new HashSet<>(map1.get(i));
            SetIntersection.retainAll(map2.get(map2.size()-1-i));
            Intersection.add(SetIntersection);
        }
        System.out.println("done");
    }


    public static String getValue(String path){
        //from the word1 +"->" +neighbors get the last arrow thing
        return path.substring(path.lastIndexOf(">")+1);
    }

    public static HashSet<String> getNeighborsSet(String word) {
        if(Neighbors.containsKey(word)){
            return Neighbors.get(word);
        }
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
        Neighbors.put(word,neighbors);
        return neighbors;
    }

}
