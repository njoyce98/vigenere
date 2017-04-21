import java.io.IOException;
import java.util.*;
import java.io.File;
import java.util.Scanner;
class dicLookupNoun{
  public String dicLookup(String plaintext) throws IOException {
    // load the dictionary into a set for fast lookups
    Set<String> dictionary = new HashSet<String>();
    Scanner filescan = new Scanner(new File("dictionary.txt"));
    Scanner scan = new Scanner(System.in);
    String output = "";
    while (filescan.hasNext()) {
      dictionary.add(filescan.nextLine().toLowerCase());
    }
    String input = plaintext;
    List<List<String>> results = new ArrayList<List<String>>();

    long time = System.currentTimeMillis();

    search(input, dictionary, new Stack<String>(), results);

    //time = System.currentTimeMillis() - time;
    List<String> best = results.get(0);
    for (List<String> result : results) {
      if(result.size() < best.size()){
        best = result;
      }
    }
    for(String word : best){
      output += word + " ";
    }
    //System.out.println("Took " + time + "ms");
    return output;
  }
  public static void search(String input, Set<String> dictionary,
        Stack<String> words, List<List<String>> results) {
    boolean flag = true;
    for (int i = 0; i < input.length(); i++) {
      String substring = input.substring(0, i + 1);
      while(flag == true){
        flag = false;
        if (dictionary.contains(substring)) {
          words.push(substring);
          if (i == input.length() - 1) {
            results.add(new ArrayList<String>(words));
          } else {
            search(input.substring(i + 1), dictionary, words, results);
          }
          words.pop();
        }
        else {
          for (int l = i + 1; l < input.length() - 1; l++){
            String substring2 = input.substring(i, l + 1);
            if (dictionary.contains(substring2)){
              words.push(substring2);
              if (l == input.length() - 1) {
                results.add(new ArrayList<String>(words));
              }  else {
                search(input.substring(l + 1), dictionary, words, results);
              }
              flag = true;
            }
          }
        }
      }
    }
  }
}