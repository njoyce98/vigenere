public class cipherToPlain{
  int[] cipherToPlainKeyL(String ciphertext2){
    int[] threePattern = new int[100000];
    int tPCount = 0;
    //Finding gaps between patterns of 3
    for (int x = 0; x < (ciphertext2.length() - 5); x++){
      char uno = ciphertext2.charAt(x);
      for (int y = (x+3); y < (ciphertext2.length() - 2); y++){
        char dos = ciphertext2.charAt(y);
        if ((ciphertext2.charAt(x) == ciphertext2.charAt(y)) && (ciphertext2.charAt(x+1) == ciphertext2.charAt(y+1)) && (ciphertext2.charAt(x+2) == ciphertext2.charAt(y+2))){
          threePattern[tPCount] = (y-x);
          tPCount += 1;
        }
      }
    }
    //Finding factors of the spaces & therefore most likely keylengths
    int[] keyLengths = new int[5];
    int[] factors = new int[1000000];
    int fCount = 0;
    for (int x = 0; x < threePattern.length; x++){
      for (int y = 3; y < threePattern[x]; y++){//Start from 3 as key must be at least 3 chars long
        if(threePattern[x]%y == 0){
          factors[fCount] = y;
          fCount +=1 ;
        }
      }
    }
    int cCount = 0;
    int[] fCheck = new int[9999999];
    int lKCount = 0;
    int lKCountMax = -1;
    for (int x = 0; x < 5; x++){
      for (int y = 0; y < fCount - 1; y++){
        for (int j = y + 1; j < fCount; j++){
          if((factors[y] != keyLengths[0]) && (factors[y] != keyLengths[1]) && (factors[y] != keyLengths[2])){// Checks against previously found keys to make sure it is a new one
            if(factors[y] == factors[j]){
              lKCount += 1;
            }
          }
        }
        if(lKCount > lKCountMax){// If the factor achieves a better score it is the most likely key length
          lKCountMax = lKCount;
          keyLengths[x] = factors[y];
        }
        lKCount = 0;
      }
      lKCountMax = -1;
    }
    return keyLengths;
  }
  String cipherToPlainKey(int[] keyLengths, String ciphertext2){
    // Finds the most likely keylengths for each keylength
    // Declaring vartiables
    String plaintext = "";
    String key = "";
    int maxNo = -1;
    int[] freqAlphabet = new int[26];
    int[] freqNo = {8167,1492,2782,4253,12702,2228,2015,6094,6966,153,772,4025,2406,6749,7507,1929,95,5987,6327,9056,2758,978,2360,150,1974,74};// Standard frequency of the english alphabet
    int[] totalMax = new int[100];
    String[] keyChars1 = new String[keyLengths[0]];
    char ok = 'a';
    String[] returned = new String[2];
    for(int x = 0; x < keyChars1.length; x++){// Clears the array
      keyChars1[x] = "";
    }
    int v = 0;
    for(int count = 0; count < keyLengths[0]; count++){// For each letter of the key length
      for(v = 0; v < 5; v++){ // 5 times
        for(int p = count; p < ciphertext2.length(); p+=(keyLengths[0])){// Cycles through the ciphertext by the keylength
          for(int x = 0; x < 25; x++){// Cycles through the alphabet
            if(ciphertext2.charAt(p) == (char)(97 + x)){// Counts the number of that character in the ciphertext
              freqAlphabet[x] += 1;
            }
          }
        }
        for(int x = 0; x < 25; x++){// Cycles through alphabet
          int maxTemp = 0;
          for(int b = 0; b < 25; b++){// Cycles through the offset of they key
            maxTemp = maxTemp + freqAlphabet[(x+b)%26]*freqNo[b];// Calculates score using theory in
          }
          if(maxNo < maxTemp && (keyChars1[count].indexOf((char)(97+x)) == -1)){// Checks to make sure the letter hasnt already been used and is bigger
            maxNo = maxTemp;
            ok = (char)(97+x);
          }
        }
        keyChars1[count] += ok;
      }
    }
    //Repeat
    String[] keyChars2 = new String[keyLengths[1]];
    ok = 'a';
    for(int x = 0; x < keyChars2.length; x++){
      keyChars2[x] = "";
    }
    v = 0;
    for(int count = 0; count < keyLengths[1]; count++){
      for(v = 0; v < 5; v++){
        for(int p = count; p < ciphertext2.length(); p+=(keyLengths[1])){
          for(int x = 0; x < 25; x++){
            if(ciphertext2.charAt(p) == (char)(97 + x)){
              freqAlphabet[x] += 1;
            }
          }
        }
        for(int x = 0; x < 25; x++){
          int maxTemp = 0;
          for(int b = 0; b < 25; b++){
            maxTemp = maxTemp + freqAlphabet[(x+b)%26]*freqNo[b];
          }
          if(maxNo < maxTemp && (keyChars2[count].indexOf((char)(97+x)) == -1)){
            maxNo = maxTemp;
            ok = (char)(97+x);
          }
        }
        keyChars2[count] += ok;
      }
    }
    //Repeat
    String[] keyChars3 = new String[keyLengths[2]];
    ok = 'a';
    for(int x = 0; x < keyChars3.length; x++){
      keyChars3[x] = "";
    }
    v = 0;
    for(int count = 0; count < keyLengths[2]; count++){
      for(v = 0; v < 5; v++){
        for(int p = count; p < ciphertext2.length(); p+=(keyLengths[2])){
          for(int x = 0; x < 25; x++){
            if(ciphertext2.charAt(p) == (char)(97 + x)){
              freqAlphabet[x] += 1;
            }
          }
        }
        for(int x = 0; x < 25; x++){
          int maxTemp = 0;
          for(int b = 0; b < 25; b++){
            maxTemp = maxTemp + freqAlphabet[(x+b)%26]*freqNo[b];
          }
          if(maxNo < maxTemp && (keyChars3[count].indexOf((char)(x+97)) == -1)){
            maxNo = maxTemp;
            ok = (char)(97+x);
          }
        }
        keyChars3[count] += ok;
      }
    }
    //Repeat
    String[] keyChars4 = new String[keyLengths[3]];
    ok = 'a';
    for(int x = 0; x < keyChars4.length; x++){
      keyChars4[x] = "";
    }
    v = 0;
    for(int count = 0; count < keyLengths[3]; count++){
      for(v = 0; v < 5; v++){
        for(int p = count; p < ciphertext2.length(); p+=(keyLengths[3])){
          for(int x = 0; x < 25; x++){
            if(ciphertext2.charAt(p) == (char)(97 + x)){
              freqAlphabet[x] += 1;
            }
          }
        }
        for(int x = 0; x < 25; x++){
          int maxTemp = 0;
          for(int b = 0; b < 25; b++){
            maxTemp = maxTemp + freqAlphabet[(x+b)%26]*freqNo[b];
          }
          if(maxNo < maxTemp && (keyChars4[count].indexOf((char)(97+x)) == -1)){
            maxNo = maxTemp;
            ok = (char)(97+x);
          }
        }
        keyChars4[count] += ok;
      }
    }
    //Repeat
    String[] keyChars5 = new String[keyLengths[4]];
    ok = 'a';
    for(int x = 0; x < keyChars5.length; x++){
      keyChars5[x] = "";
    }
    v = 0;
    for(int count = 0; count < keyLengths[4]; count++){
      for(v = 0; v < 5; v++){
        for(int p = count; p < ciphertext2.length(); p+=(keyLengths[4])){
          for(int x = 0; x < 25; x++){
            if(ciphertext2.charAt(p) == (char)(97 + x)){
              freqAlphabet[x] += 1;
            }
          }
        }
        for(int x = 0; x < 25; x++){
          int maxTemp = 0;
          for(int b = 0; b < 25; b++){
            maxTemp = maxTemp + freqAlphabet[(x+b)%26]*freqNo[b];
          }
          if(maxNo < maxTemp && (keyChars5[count].indexOf((char)(97+x)) == -1)){
            maxNo = maxTemp;
            ok = (char)(97+x);
          }
        }
        keyChars5[count] += ok;   
      }
    }
    //Make keys up  
    String[] keys = new String[10000000];
    int keyCount = 0;
    for(int x = 0; x < keys.length; x++){
      keys[x] = "";
    }
    for(int x = 0; x < 5; x++){
      for(int y = 0; y < 5; y++){
        for(int z = 0; z < 5; z++){
          if(keyChars1.length > 3){
            for(int a = 0; a < 5; a++){
              if(keyChars1.length > 4){
                for(int b = 0; b < 5; b++){
                  if(keyChars2.length > 5){
                    keys[keyCount] += keyChars1[0].charAt(x);
                    keys[keyCount] += keyChars1[1].charAt(y);
                    keys[keyCount] += keyChars1[2].charAt(z);
                    keys[keyCount] += keyChars1[3].charAt(a);
                    keys[keyCount] += keyChars1[4].charAt(b);
                    keyCount += 1;
                  }
                }
              }
              else{
                keys[keyCount] += keyChars1[0].charAt(x);
                keys[keyCount] += keyChars1[1].charAt(y);
                keys[keyCount] += keyChars1[2].charAt(z);
                keys[keyCount] += keyChars1[3].charAt(a);
                keyCount += 1;
              }
            }
          }
          else{
            keys[keyCount] += keyChars1[0].charAt(x);
            keys[keyCount] += keyChars1[1].charAt(y);
            keys[keyCount] += keyChars1[2].charAt(z);
            keyCount += 1;
          }
        }
      }
    }
    for(int x = 0; x < 5; x++){
      for(int y = 0; y < 5; y++){
        for(int z = 0; z < 5; z++){
          if(keyChars2.length > 3){
            for(int a = 0; a < 5; a++){
              if(keyChars2.length > 4){
                for(int b = 0; b < 5; b++){
                  if(keyChars2.length > 5){
                    keys[keyCount] += keyChars2[0].charAt(x);
                    keys[keyCount] += keyChars2[1].charAt(y);
                    keys[keyCount] += keyChars2[2].charAt(z);
                    keys[keyCount] += keyChars2[3].charAt(a);
                    keys[keyCount] += keyChars2[4].charAt(b);
                    keyCount += 1;
                  }
                }
              }
              else{
                keys[keyCount] += keyChars2[0].charAt(x);
                keys[keyCount] += keyChars2[1].charAt(y);
                keys[keyCount] += keyChars2[2].charAt(z);
                keys[keyCount] += keyChars2[3].charAt(a);
                keyCount += 1;
              }
            }
          }
          else{
            keys[keyCount] += keyChars2[0].charAt(x);
            keys[keyCount] += keyChars2[1].charAt(y);
            keys[keyCount] += keyChars2[2].charAt(z);
            keyCount += 1;
          }
        }
      }
    }
    for(int x = 0; x < 5; x++){
      for(int y = 0; y < 5; y++){
        for(int z = 0; z < 5; z++){
          if(keyChars3.length > 3){
            for(int a = 0; a < 5; a++){
              if(keyChars3.length > 4){
                for(int b = 0; b < 5; b++){
                  if(keyChars3.length > 5){
                    keys[keyCount] += keyChars3[0].charAt(x);
                    keys[keyCount] += keyChars3[1].charAt(y);
                    keys[keyCount] += keyChars3[2].charAt(z);
                    keys[keyCount] += keyChars3[3].charAt(a);
                    keys[keyCount] += keyChars3[4].charAt(b);
                    keyCount += 1;
                  }
                }
              }
              else{
                keys[keyCount] += keyChars3[0].charAt(x);
                keys[keyCount] += keyChars3[1].charAt(y);
                keys[keyCount] += keyChars3[2].charAt(z);
                keys[keyCount] += keyChars3[3].charAt(a);
                keyCount += 1;
              }
            }
          }
          else{
            keys[keyCount] += keyChars3[0].charAt(x);
            keys[keyCount] += keyChars3[1].charAt(y);
            keys[keyCount] += keyChars3[2].charAt(z);
            keyCount += 1;
          }
        }
      }
    }
    for(int x = 0; x < 5; x++){
      for(int y = 0; y < 5; y++){
        for(int z = 0; z < 5; z++){
          if(keyChars4.length > 3){
            for(int a = 0; a < 5; a++){
              if(keyChars4.length > 4){
                for(int b = 0; b < 5; b++){
                  if(keyChars4.length > 5){
                    keys[keyCount] += keyChars4[0].charAt(x);
                    keys[keyCount] += keyChars4[1].charAt(y);
                    keys[keyCount] += keyChars4[2].charAt(z);
                    keys[keyCount] += keyChars4[3].charAt(a);
                    keys[keyCount] += keyChars4[4].charAt(b);
                    keyCount += 1;
                  }
                }
              }
              else{
                keys[keyCount] += keyChars4[0].charAt(x);
                keys[keyCount] += keyChars4[1].charAt(y);
                keys[keyCount] += keyChars4[2].charAt(z);
                keys[keyCount] += keyChars4[3].charAt(a);
                keyCount += 1;
              }
            }
          }
          else{
            keys[keyCount] += keyChars4[0].charAt(x);
            keys[keyCount] += keyChars4[1].charAt(y);
            keys[keyCount] += keyChars4[2].charAt(z);
            keyCount += 1;
          }
        }
      }
    }
    for(int x = 0; x < 5; x++){
      for(int y = 0; y < 5; y++){
        for(int z = 0; z < 5; z++){
          if(keyChars5.length > 3){
            for(int a = 0; a < 5; a++){
              if(keyChars5.length > 4){
                for(int b = 0; b < 5; b++){
                  if(keyChars5.length > 5){
                    keys[keyCount] += keyChars5[0].charAt(x);
                    keys[keyCount] += keyChars5[1].charAt(y);
                    keys[keyCount] += keyChars5[2].charAt(z);
                    keys[keyCount] += keyChars5[3].charAt(a);
                    keys[keyCount] += keyChars5[4].charAt(b);
                    keyCount += 1;
                  }
                }
              }
              else{
                keys[keyCount] += keyChars5[0].charAt(x);
                keys[keyCount] += keyChars5[1].charAt(y);
                keys[keyCount] += keyChars5[2].charAt(z);
                keys[keyCount] += keyChars5[3].charAt(a);
                keyCount += 1;
              }
            }
          }
          else{
            keys[keyCount] += keyChars5[0].charAt(x);
            keys[keyCount] += keyChars5[1].charAt(y);
            keys[keyCount] += keyChars5[2].charAt(z);
            keyCount += 1;
          }
        }
      }
    }
    // Decryption
    // 50 most common words in the english language
    String[] commonWords =  {"the","be","to","of","and","in","that","have","it","for","not","on","with","he","as","you","do","at","this","but","his","by","from","they","we","say","her","she","or","an","will","my","one","all","would","there","their","what","so","up","out","if","about","who","get","which","go","me"};
    int maxTheCount = -1;
    int loop = 0;
    while(!keys[loop].equals("")){// Loop for all the keys
      for(int i = 0, j = 0; i < ciphertext2.length(); i++){// Goes through the ciphertext and decrypts it with the key being currently used
        char c = ciphertext2.charAt(i);
        plaintext += (char)((c - keys[loop].charAt(j) + 26) % 26 + 'a');
        j = ++j % keys[loop].length();
      }
      // Find best key
      String ptTemp = plaintext; // Saved the plaintext to reset it later
      int theCount = 0;
      for(int i = 0; i < commonWords.length; i++){ // Runs through all common words
        int index = plaintext.indexOf(commonWords[i]);
        while(index != -1) {// If the common word is foung in the plaintext the score of the key is increased
          theCount++;
          plaintext = plaintext.substring(index + 1);
          index = plaintext.indexOf(commonWords[i]);
        }
        plaintext = ptTemp;
      }
      if(theCount > maxTheCount){// If the keys score is better than the other keys it is the new most likely key
        key = "";
        key = keys[loop];
        returned[0] = key;
        maxTheCount = theCount;
      }
      theCount = 0;
      plaintext = "";
      loop += 1;
    }
    return key;
  }
  String[] ctpDecrypt(String ciphertext2, String key){
    // Decrypts the ciphertext with the key
    String plaintext = "";
    String[] returned = new String[2];
    for (int i = 0, j = 0; i < ciphertext2.length(); i++) {// Runs through each letter of the ciphertext
      char c = ciphertext2.charAt(i);
      plaintext += (char)((c - key.charAt(j) + 26) % 26 + 'a');// Decryption algorithm
      j = ++j % key.length();
    }
    returned[1] = plaintext;
    returned[0] = key;
    return(returned);
  }
  int z = 0;
  String[] ctpDecrypt(String ciphertext2, String key, String[] returned){
    char ch = ciphertext2.charAt(0);
    StringBuffer output = new StringBuffer();
    ch = (char)((ch - key.charAt(z) + 26) % 26 + 'a');
    z = ++z % key.length();
    output.append(ch);
    System.out.println(output);
    if (ciphertext2.length() > 1) {
      output.append(ctpDecrypt(ciphertext2.substring(1), key, returned));
      System.out.println(output);
    }
    z = 0;
    returned[1] = output.toString();
    returned[0] = key;
    return returned;
  }
}