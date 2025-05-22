import java.util.ArrayList;

/*
 * Analyzes and processes text
 */
public class TextProcessor {

  private String filename;              // The file containing the text
  private ArrayList<String> textList;   // The list of text from the file

  /*
   * Sets the filename to the specified filename and initializes
   * textList to contain the data from the file
   */
  public TextProcessor(String filename) {
    this.filename = filename;
    textList = FileReader.toStringList(filename);
  }

  /*
   * Constructor to create a TextProcessor with the specified list of words
   */
  public TextProcessor(ArrayList<String> textList) {
    this.textList = textList;
  }

  /*
   * Returns the textList
   */
  public ArrayList<String> getTextList() {
    return textList;
  }

  /*
   * Changes the file to analyze and process and updates the textList
   */
  public void changeFile(String filename) {
    this.filename = filename;
    textList = FileReader.toStringList(filename);
  }

  /*
   * Changes the textList to the newTextList
   */
  public void setTextList(ArrayList<String> newTextList) {
    textList = newTextList;
  }

  /*
   * Returns true if textList contains the specified word
   * or phrase, otherwise returns false
   */
  public boolean hasText(String phrase) {
    boolean found = false;
    
    for (int index = 0; index < textList.size(); index++) {
      String currentLine = textList.get(index);

      if (currentLine.indexOf(phrase) >= 0) {
        found = true;
      }
    }

    return found;
  }

  /*
   * Returns the sentiment value of a word
   */
  //Method that takes an ArrayList of sentiment data and a word to find its sentiment value
  public double getSentiment(ArrayList<String> sentiments, String word) {
    // Default sentiment value is 0.0 in case the word is not found
    double sentimentValue = 0.0;

    // Iterate through the 'sentiments' list
    for (int index = 0; index < sentiments.size(); index++) {
      // Get the current line from the 'sentiments' list
      String currentLine = sentiments.get(index);

       // Find the index of the comma separating the word and its sentiment value
      int commaIndex = currentLine.indexOf(",");
       // If a comma is found (i.e., the line contains both a word and a sentiment score)
      if(commaIndex != -1) {
        // Extract the word before the comma
      String sentimentWord = currentLine.substring(0, commaIndex);
        
       // Check if the extracted word matches the input word
      if (sentimentWord.equals(word)) {
         // Parse and return the sentiment score after the comma
        sentimentValue = Double.parseDouble(currentLine.substring(commaIndex + 1));
        return sentimentValue;
      }
    }
  }
    // If the word was not found, return the default sentiment value (0.0)
      return sentimentValue;
  }
 /*
 * Returns a String containing all text from 'textList', separated by newlines
 */
  public String toString() {
    // Initialize an empty string to build the text output
    String text = "";

    // Iterate through each string in the 'textList'
    for (String value : textList) {
      // Concatenate each string in the list with a newline character
      text = text + value + "\n";
    }
    // Return the complete text string
    return text;
  }
/*
 * Returns the sentiment score for a word from the 'sentiments' list.
 * This version does not require passing the 'sentiments' list as a parameter.
 */
  
}
