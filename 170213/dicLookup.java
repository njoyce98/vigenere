import java.io.IOException;
import java.util.*;
import java.io.File;
import java.util.Scanner;
class dicLookup{
  public String dicLookup(String plaintext) throws IOException {
    // load the dictionary into a set for fast lookups
    Set<String> dictLook = new HashSet<String>();
    Scanner filescan = new Scanner(new File("dictionary.txt"));
    Scanner scanner = new Scanner(System.in);
    String plaintext2 = "";
    while (filescan.hasNext()) {
      dictLook.add(filescan.nextLine().toLowerCase());
    }
    String input = plaintext;
    List<List<String>> outputs = new ArrayList<List<String>>();
    // Uses the search method to compare the input to a word from the dictionary
    search(input, dictLook, new Stack<String>(), outputs);
    List<String> best = outputs.get(0);
    for (List<String> output : outputs) {
      if(output.size() < best.size()){
        best = output;
      }
    }
    for(String found : best){
      plaintext += found + " ";
    }
    return plaintext2;
  }
  public static void search(String input, Set<String> dictLook, Stack<String> found, List<List<String>> outputs) {
    for (int c = 0; c < input.length(); c++) {
      // Obtains the substring used 
      String substring = input.substring(0, c + 1);
      if (dictLook.contains(substring)) {
        found.push(substring);
        if (c == input.length() - 1) {
          outputs.add(new ArrayList<String>(found));
        } else {
          search(input.substring(c + 1), dictLook, found, outputs);
        }
        found.pop();
      }
    }
  }
}