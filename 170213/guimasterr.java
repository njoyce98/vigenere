import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.*;
import java.awt.Container;
import javax.swing.*;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.event.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.util.*;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.JMenuBar;
import java.awt.Checkbox;
import javax.swing.AbstractButton;
import java.awt.datatransfer.*;
import java.awt.Toolkit;
import java.awt.Color;
public class guimasterr extends JFrame implements ActionListener, KeyListener{
  // Declaring and initialising variables
  JTextArea input, output, keyIn, inputCount, outputCount;
  JFrame jf;
  JMenu FileM, OptionsM;
  JMenuItem openM, saveM;
  JCheckBoxMenuItem recursionM = new JCheckBoxMenuItem("Recursion");
  JCheckBoxMenuItem spacesM = new JCheckBoxMenuItem("Add spaces");
  JButton ptcB, ctpB, copyPM, clearM;
  String plaintext = "";
  String ciphertext = "";
  String ciphertext2 = "";
  String key = "";
  String[] returned = {"", ""};
  BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
  plainToCipher ptc = new plainToCipher();
  cipherToPlain ctp = new cipherToPlain();
  dicLookup dL = new dicLookup();
  StringBuffererClass sBuffer = new StringBuffererClass();
  int inCharCount = 0;
  JFileChooser fc = new JFileChooser();
  static private final String newline = "\n";
  public guimaster(){
    //Make the container for the JPanels, in a 1,3 setup
    Container container = getContentPane();
    container.setLayout(new GridLayout(1,3));
    //Leftmost panel of the container which has user inputted text
    JPanel p03 = new JPanel(new GridLayout());;
    container.add(p03);
    input = new JTextArea();
    input.addKeyListener(this);
    input.setBorder(BorderFactory.createTitledBorder("Input"));
    JScrollPane ScrollPaneInput = new JScrollPane(input);
    p03.add(input);
    //Middle panel made up of panels; has character count, buttons and keys
    JPanel p1 = new JPanel(new GridLayout(4,1));
    inputCount = new JTextArea(); //Text area that displays the character count in the input panel
    inputCount.setSize(new Dimension (1,1));
    inputCount.setEditable(false);
    inputCount.setBorder(BorderFactory.createTitledBorder("Character count"));
    inputCount.setToolTipText("The larger the text the more accurate the decryption will be");
    p1.add(inputCount);
    String inputText = input.getText();
    inputCount.setText(String.valueOf(inCharCount));
    keyIn = new JTextArea();
    keyIn.setBorder(BorderFactory.createTitledBorder("Key"));
    keyIn.setToolTipText("3-5 alphabetical characters");
    keyIn.getDocument().putProperty("filterNewLines", Boolean.TRUE);
    p1.add(ptcB = new JButton("Plain to cipher"));
    p1.add(ctpB = new JButton("Cipher to plain"));
    p1.add(keyIn);
    container.add(p1);
    //Leftmost panel which has outputted text
    JPanel outP = new JPanel(new GridLayout(1,2,30,30));;
    container.add(outP);
    outP.add(output = new JTextArea());
    output.setBorder(BorderFactory.createTitledBorder("Output"));
    output.setToolTipText("En/decrypted text");
    JScrollPane ScrollPaneOutput = new JScrollPane(output);
    input.setLineWrap(true);
    input.setWrapStyleWord(true);
    output.setLineWrap(true);
    output.setWrapStyleWord(true);
    output.setEditable(false);
    p03.add(ScrollPaneInput);
    outP.add(ScrollPaneOutput);
    ptcB.addActionListener(this);
    ctpB.addActionListener(this);
    //Initialise the filechooser
    FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt", "text");
    fc.setFileFilter(filter);
    fc.setDialogTitle("Select text file");
    fc.setCurrentDirectory(new java.io.File("."));
    //Setup menubar
    menubarMake();
  }
  public void actionPerformed(ActionEvent e){
    // Actions performed on events
    if(e.getSource() == ptcB){
      // Plaintext to ciphertext
      // Declaring times and other variables for future calculations
      long startTime;
      long endTime;
      long duration;
      output.setText("");
      ciphertext = "";
      // Defaulting key
      String keyTemp = "";
      keyTemp = keyIn.getText();
      key = "";
      keyTemp = keyTemp.toLowerCase();
      for(int x = 0; x < keyTemp.length(); x++){
        char c = keyTemp.charAt(x);
        if(c > 'a' - 1 && c < 'z' + 1){
          key += c;
        }
      }
      key = key.toLowerCase();
      String plaintextTemp = "";plaintextTemp = input.getText();
      plaintextTemp = plaintextTemp.toLowerCase();
      plaintext = "";
      for(int x = 0; x < plaintextTemp.length(); x++){
        char c = plaintextTemp.charAt(x);
        if(c > 'a' - 1 && c < 'z' + 1){
          plaintext += c;
        }
      }
      if(!key.equals("") && key.length() < 6 && key.length() > 2){
        startTime = System.nanoTime();
        ciphertext = (ptc.ptc(plaintext, key));
        output.setText(ciphertext);
        endTime = System.nanoTime();
        duration = (endTime - startTime)/1000000;
        JOptionPane.showMessageDialog(null, "Run time: " + duration + "ms");
      }
      else{
        JOptionPane.showMessageDialog(null, "Enter an alphabetical key between 3-5 characters");
      }
    }
    if(e.getSource() == ctpB){
      // Ciphertext to plaintext
      ciphertext = input.getText();
      ciphertext = ciphertext.toLowerCase();
      String ciphertext2 = "";
      long startTime;
      long endTime;
      long duration;
      for (int x = 0; x < ciphertext.length(); x++){ // For loop goes through the ciphertext and removes all non-alphabetic characters
        char c = ciphertext.charAt(x);
        if (c < 'a' || c > 'z') continue;
        ciphertext2 += c;
      }
      if(keyIn.getText().equals("")){ // If no key is entered the decryption is performed as finding the key length -> most likeley key -> decryption
        startTime = System.nanoTime();
        int[] keyLengths = ctp.cipherToPlainKeyL(ciphertext2);
        key = ctp.cipherToPlainKey(keyLengths, ciphertext2);
        returned = ctp.ctpDecrypt(ciphertext2, key);
      }
      else{
        int n = JOptionPane.showConfirmDialog(null, "Do you want to use this key to solve the cipher?", "Confirm key", JOptionPane.YES_NO_OPTION); // Checks if the inputted key is what they want to decrypt with
        if (n == JOptionPane.YES_OPTION){ // If yes it just decrypts as they key is known
          startTime = System.nanoTime();
          key = keyIn.getText();
          returned = ctp.ctpDecrypt(ciphertext2, key);
        }
        else{ // If no it the decryption is performed as finding the key length -> most likeley key -> decryption
          startTime = System.nanoTime();
          int[] keyLengths = ctp.cipherToPlainKeyL(ciphertext2);
          key = ctp.cipherToPlainKey(keyLengths, ciphertext2);
          returned = ctp.ctpDecrypt(ciphertext2, key);
        }
      }
      plaintext = returned[1];
      key = returned[0];
      keyIn.setText(key);
      if(spacesM.getState() == true){ // Checks if option to add spaces is ticked, if true it performs a dictionary lookup to add spaces based on recognised words
        try{
          plaintext = dL.dicLookup(plaintext);
        } catch (IOException edL){
          //
        }
      }
      output.setText(plaintext);
      endTime = System.nanoTime();
      duration = (endTime - startTime)/1000000; // Calculates time spent on algorithm
      JOptionPane.showMessageDialog(null, "Run time: " + duration + "ms");
    }
    if(e.getSource() == openM){
      // Open file operations 
      String filename = "";
      if(fc.showOpenDialog(guimaster.this) == JFileChooser.APPROVE_OPTION){// Opens the JFileChooser and obtains the path for 
        filename = fc.getSelectedFile().getAbsolutePath();
        File fileCheck = new File(filename);
        if(fileCheck.exists()){
          try{
            plaintext = (sBuffer.stringbufferer(filename)).toString();
          } catch (IOException i){
            System.err.println("Error: " + i);
          }
        }
        else{
          JOptionPane.showMessageDialog(null, "File cannot be found");
        }
      }
      key = keyIn.getText();
      input.setText(plaintext);
    }
    if(e.getSource() == saveM){
      // Save file
      if(fc.showSaveDialog(guimaster.this) == JFileChooser.APPROVE_OPTION){//JFileChooser to save file
        String content = input.getText();
        File fi = fc.getSelectedFile();
        String filename = fc.getSelectedFile().getAbsolutePath();
        File fileCheck = new File(filename);
        if(fileCheck.exists()){// Overwrite options
          Object[] options = {"Yes", "New File", "Cancel"};
          int n = JOptionPane.showOptionDialog(null, "The file already exists, would you like to overwrite it?", "Overwrite?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
          if(n == JOptionPane.YES_OPTION){
            try{// Writes the file
              FileWriter fw = new FileWriter(fi.getPath());
              fw.write(content);
              fw.flush();
              fw.close();
            }catch(Exception e2){
              JOptionPane.showMessageDialog(null, e2.getMessage());
            }
          }
          if(n == JOptionPane.NO_OPTION){
            String newFilename = JOptionPane.showInputDialog(null, "Name the new text file");
            try{
              PrintWriter writer = new PrintWriter(newFilename + ".txt", "UTF-8");
              writer.print(input.getText());
              writer.close();
            } catch (IOException e3) {
              //
            }
          }
        }
      }
    }
    if(e.getSource() == copyPM){
      // Copy and pastes the output box to the input box and clears the rest for ease of access
      if(!output.getText().equals("")){
        String copy = output.getText();
        output.setText("");
        input.setText(copy);
      }
    }
    if(e.getSource() == clearM){
      // Clears all fields
      output.setText("");
      input.setText("");
      keyIn.setText("");
    }
  }
  public static void main(String [] args){
    // Making the JFrame, initialising and setting variables
    guimaster frame = new guimaster();
    frame.setTitle("GUI");
    frame.setSize(1500, 900);
    frame.setVisible(true);
    frame.setResizable(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
  }
  void draw(){
    //
  }
  void menubarMake(){
    // Initialises menubar
    JMenuBar jmb = new JMenuBar();
    // Makes the file menu options, file, open file and save file
    FileM = new JMenu("File");
    jmb.add(FileM);
    openM = new JMenuItem("Open File");
    FileM.add(openM);
    openM.addActionListener(this);
    saveM = new JMenuItem("Save File");
    FileM.add(saveM);
    saveM.addActionListener(this);
    // Makes the options menu drop
    OptionsM = new JMenu("Options");
    jmb.add(OptionsM);
    OptionsM.add(recursionM);
    OptionsM.add(spacesM);
    // Copy and paste button for ease of access
    copyPM = new JButton("Copy & Paste");
    copyPM.setOpaque(false);
    copyPM.setContentAreaFilled(false);
    copyPM.setBorderPainted(false);
    copyPM.setPreferredSize(new Dimension(FileM.getPreferredSize()));
    jmb.add(copyPM);
    copyPM.addActionListener(this);
    // Clear fields button for ease of access
    clearM = new JButton("Clear");
    clearM.setOpaque(false);
    clearM.setContentAreaFilled(false);
    clearM.setBorderPainted(false);
    clearM.setPreferredSize(new Dimension(FileM.getPreferredSize()));
    jmb.add(clearM);
    clearM.addActionListener(this);
    setJMenuBar(jmb);
  }
  public void keyPressed(KeyEvent e){
    //
  }
  public void keyReleased(KeyEvent e){
    //
  }
  public void keyTyped(KeyEvent e){
    // What happens when a key is typed
    int count = 1;
    if(input.getText().equals("")){// Checks if there is characters in the input field
      inputCount.setText(String.valueOf(0));
    }
    else{
      for(int x = 0; x < input.getText().length(); x++){// Calculates the number of alphabetical characters in the alphabetical field
        char c = input.getText().charAt(x);
        if(c > ('a' - 1) && c < ('z' + 1)){
          count += 1;
        }
      }
      inputCount.setText(String.valueOf(count));// Sets the calulated number to the character count field
    }
  }
}
class SampleMenuListener implements MenuListener {
  @Override
  public void menuSelected(MenuEvent e) {
    //
  }
  @Override
  public void menuDeselected(MenuEvent e) {
    //
  }
  @Override
  public void menuCanceled(MenuEvent e) {
    //
  }
}
class MenuActionListener implements ActionListener {
  public void actionPerformed(ActionEvent e) {
    //
  }
}