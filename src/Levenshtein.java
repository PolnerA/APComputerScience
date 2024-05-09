import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Levenshtein {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(new File("dictionarySortedLength.txt"));
        HashSet<String> a = new HashSet<>();
        while(sc.hasNext()){
            String word = sc.nextLine();
            a.add(word);
        }
        sc.close();
        Scanner sc2 = new Scanner(new File("dictionarySortedLength.txt"));
        File neighbor = new File("dictionaryWithNeighbors");
        neighbor.createNewFile();
        FileWriter writer = new FileWriter(neighbor);
        int count=0;
        while(sc2.hasNext()){ //_0_1_2_3_4_
            String word = sc2.nextLine();
            count++;
            System.out.println(count+"/"+a.size());
            char[] letters=word.toCharArray();
            ArrayList<Character> letter = new ArrayList<>();
            for(int i=0;i< letters.length;i++){
                letter.add(letters[i]);
            }
            writer.write(word);
            for(int i =0; i<word.length();i++){
                for(int j=0;j<26;j++){
                    String newWord="";
                    char g = (char) ('a'+j);
                    letter.set(i,g);
                    for(int k=0;k<letter.size();k++){
                        newWord= newWord + letter.get(k);
                    }
                    if(a.contains(newWord)){
                        if(!word.equals(newWord)){
                            writer.write("|"+newWord);
                        }
                    }
                    String newWord2="";
                    char g2 = (char) ('a'+j);
                    letter.add(i,g2);
                    for(int k=0;k<letter.size();k++){
                        newWord2= newWord2 + letter.get(k);
                    }
                    if(a.contains(newWord2)){
                        if(!word.equals(newWord2)){
                            writer.write("|"+newWord2);
                        }
                    }
                }
            }for(int i=0;i<word.length();i++){
                for(int j=0;j<26;j++){

                }
            }
            writer.write("\n");
        }
        writer.close();
        sc2.close();

    }
}
