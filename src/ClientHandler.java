import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class ClientHandler implements Runnable
{
    Socket client;
    BlockingQueue<String> messages;
    PrintWriter pw;
    Scanner sc;
    Quiz quiz;

    public PrintWriter getPw() {
        return pw;
    }

    //TODO lav ny konstruktør med den delte resource
    public ClientHandler(Socket client, BlockingQueue<String> messages) throws IOException {
        this.client = client;
        this.messages = messages;
        this.pw = new PrintWriter(client.getOutputStream(),true);
        this.sc = new Scanner(client.getInputStream());
        quiz = new Quiz(pw, sc);
    }

    public void protocol() throws IOException, InterruptedException {
        String msg  = "";

        pw.println(" Hej fra server");

        while(!msg.equals("CLOSE"))
        {
            msg = sc.nextLine();
            String[] stringArray = msg.split("#");
            String action = "";
            String returnMessage = "";
            action = stringArray[0];
            returnMessage = stringArray[1];

            switch(action.toLowerCase())
            {
                case "quiz":
                    quiz.doQuiz();
                    break;
                case "all":
                    //TODO indsæt besked i delt resource
                    messages.put(returnMessage);
                    break;
                case "upper":
                    pw.println(returnMessage.toUpperCase());
                    break;
                case "lower":
                    pw.println(returnMessage.toLowerCase());
                    break;
                case "reverse":
                    StringBuilder sb = new StringBuilder(returnMessage);
                    pw.println(sb.reverse());
                    break;
                case "translate":
                    pw.println(doTranslate(returnMessage));
                    break;
            }
        }
        client.close();
    }

    private String doTranslate(String returnMessage)
    {
        return returnMessage;
    }

    @Override
    public void run() {
        while(true)
        {
            try {
                this.protocol();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
