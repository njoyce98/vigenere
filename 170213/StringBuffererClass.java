import java.io.*;
public class StringBuffererClass{
  public StringBuffer stringbufferer(String filename) throws IOException {
    BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
    StringBuffer stringBuffer = new StringBuffer();
    String line = null;
    while((line =bufferedReader.readLine())!=null){
      stringBuffer.append(line).append("\n");
    }
    return stringBuffer;
  }
}