import java.util.ArrayList;
import java.util.Scanner;

/*
 * Represents a chatbot that interacts with the user
 */
public class Chatbot {
    
  private String name;                   // The name of the chatbot
  private ArrayList<String> questions;   // The list of questions the chatbot can ask
  private ArrayList<String> responses;   // The list of responses for the chatbot
  private boolean hasQuestion;           // Whether or not the chatbot should ask a question
  private String currentQuestion;        // The current question the chatbot has asked

  /*
   * Sets name to the specified name and initializes questionsFile and
   * responsesFile by reading the data from the specified text files
   */
  public Chatbot(String name, String questionsFile, String responsesFile) {
    this.name = name;
    this.questions = FileReader.toStringList(questionsFile);
    this.responses = FileReader.toStringList(responsesFile);
  }

  /*
   * Changes the name of the chatbot to the newName
   */
  public void setName(String newName) {
    name = newName;
  }

  /*
   * Prompts the user to say something
   */
  public void chat() {
    Scanner input = new Scanner(System.in);
    String user = "";
  
    System.out.print("Hello! My name is " + name + ". What is your name? ");
    user = input.nextLine();

    System.out.println("Hi " + user + "!");

    currentQuestion = getQuestion();
    System.out.print(currentQuestion + " ");
    user = input.nextLine();
  
    while (!user.equals("bye")) {
      System.out.println(chooseResponse(user));

      if (hasQuestion) {
        currentQuestion = getQuestion();
        System.out.print(currentQuestion + " ");
      }
      
      user = input.nextLine();
    }
  
    input.close();
  }

  /*
   * Returns a random question to ask the user
   */
  public String getQuestion() {
    int randomIndex = (int)(Math.random() * questions.size());
    return questions.get(randomIndex);
  }

  /*
   * Returns the overall sentiment of the user's response
   */
  public double getSentiment(ArrayList<String> responseWords) {
    TextProcessor processor = new TextProcessor(responseWords);
    double totalSentiment = 0.0;
  
    for (int index = 0; index < responseWords.size(); index++) {
      String currentWord = responseWords.get(index);
      totalSentiment += processor.getSentiment(FileReader.toStringList("sentimentvalues.txt"), currentWord);
    }
  
    return totalSentiment;
  }

  /*
   * Returns an answer to the user's response
   */
  public String chooseResponse(String userResponse) {
    ArrayList<String> responseWords = convertToWords(userResponse);
    TextProcessor processor = new TextProcessor(responseWords);
  
    String answer = "";
    hasQuestion = false;
  
    if (userResponse.length() == 0) {
      answer = "I think you forgot to say something.";
      hasQuestion = true;
    }
    else if (getSentiment(responseWords) < 0) {
      answer = "I'm sorry you feel that way.";
    }
    else if (processor.hasText("mother") || processor.hasText("father")
              || processor.hasText("brother") || processor.hasText("sister")) {
      answer = "Tell me more about your family.";
    }
    else if (processor.hasText("I want to")) {
      answer = getWantTo(userResponse);
    }
    else if (processor.hasText("I want")) {
      answer = getWant(userResponse);
    }
    else if (processor.hasText("yes")) {
      answer = askMore(userResponse);
    }
    else {
      answer = getRandomResponse();
      hasQuestion = true;
    }
  
    return answer;
  }

  /*
   * Returns an ArrayList containing the words in the text
   */
  public ArrayList<String> convertToWords(String text) {
    ArrayList<String> words = new ArrayList<String>();
    int space = text.indexOf(" ");
  
    while (space != -1) {
      String currentWord = text.substring(0, space);
      words.add(currentWord);
      text = text.substring(space + 1);
      space = text.indexOf(" ");
    }
  
    words.add(text);
    return words;
  }

  /*
   * Returns an answer containing the text "What would happen if you"
   * plus the thing the user stated they want to do
   */
  public String getWantTo(String userResponse) {
    userResponse = removePunctuation(userResponse);
    int phrase = userResponse.indexOf("I want to");
    String restOfResponse = userResponse.substring(phrase + 9);
    return "What would happen if you " + restOfResponse + "?";
  }

  /*
   * Returns an answer containing the text "Why do you want"
   * plus the thing the user stated they wanted
   */
  public String getWant(String userResponse) {
    userResponse = removePunctuation(userResponse);
    int phrase = userResponse.indexOf("I want");
    String restOfResponse = userResponse.substring(phrase + 6);
    return "Why do you want " + restOfResponse + "?";
  }

  /*
   * Returns a String containing the user's response without ending punctuation
   */
  public String removePunctuation(String userResponse) {
    String[] punctuation = {".", "?", "!"};
    String lastCharacter = userResponse.substring(userResponse.length() - 1);

    for (int index = 0; index < punctuation.length; index++) {
      if (lastCharacter.equals(punctuation[index])) {
        userResponse.substring(0, userResponse.length() - 1);
      }
    }

    return userResponse;
  }

  /*
   * Returns a String asking the user more about the current topic
   */
  public String askMore(String userResponse) {
    ArrayList<String> text = convertToWords(currentQuestion);
    TextProcessor processor = new TextProcessor(text);
    String answer = "";

    if (processor.hasText("joke")) {
      answer = "Tell me a joke!";
    }
    else if (processor.hasText("Matrix")) {
      answer = "What's it like?";
    }
    else if (processor.hasText("hobby")) {
      answer = "Tell me about your hobby!";
    }
    else if (processor.hasText("old")) {
      answer = "What's it like being " + userResponse + " years old?";
    }
    else if (processor.hasText("day")) {
      answer = "What do you plan to do today?";
    }
    else if (processor.hasText("languages")) {
      answer = "That's cool! Can you say something in " + userResponse + "?";
    }
    else if (processor.hasText("weather")) {
      answer = "I like that kind of weather.";
    }
    else {
      answer = "Tell me more about that!";
    }

    return answer;
  }

  /*
   * Returns a random answer to the user's response
   */
  public String getRandomResponse() {
    int randomIndex = (int)(Math.random() * responses.size());
    return responses.get(randomIndex);
  }

}
