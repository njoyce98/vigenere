import java.util.*;
import java.io.*;
import java.io.Reader;
public class plainToCipher{
  String ptc(String plaintext, String key){// Encryption method
    String ciphertext = "";
    for (int i = 0, k = 0; i < plaintext.length(); i++) {// Runs through each letter of the plaintext
      char c = plaintext.charAt(i);
      ciphertext += (char)((c + key.charAt(k) - 2 * 'a') % 26 + 'a');// Encryption algorithm
      k = ++k % key.length();// Moves to next letter of the key
    }
    return ciphertext;
  }
  int j = 0;
  String ptc(String plaintext, String key, String ciphertext){//Recursion encryption method
    char ch = plaintext.charAt(0);
    StringBuffer output = new StringBuffer();
    ch = (char)((ch + key.charAt(j) - 2 * 'a') % 26 + 'a');// Encryption algorithm
    j = ++j % key.length();// Moves to next letter of the key
    output.append(ch);
    if (plaintext.length() > 1) {// Repeat if there is still more characters left in the plaintext remove characters that has just been encrypted
      output.append(ptc(plaintext.substring(1), key, ciphertext));
    }
    j = 0;
    ciphertext = output.toString();
    return ciphertext;
  }
}