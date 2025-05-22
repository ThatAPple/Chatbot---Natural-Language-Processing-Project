public class ChatRunner {
  public static void main(String[] args) {

    Chatbot bot = new Chatbot("Isabella", "question.txt", "responses.txt");
    bot.chat();
    
  }
}
