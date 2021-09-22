import java.io.PrintWriter;
import java.util.Scanner;

public class Quiz
{
    boolean isRunning;
    PrintWriter pw;
    Scanner sc;
    int score;

    public Quiz(PrintWriter pw, Scanner sc)

    {
        this.pw = pw;
        this.sc = sc;
        this.score = 0;
    }

    public void correctAnswer(int i)
    {
        score += i;
        pw.println("Correct! Your score is: " + score);
    }

    public void doQuiz()
    {
        pw.println("Welcome to the quiz!");
        pw.println("Choose a question: 100, 200, 300, 400");
        isRunning = true;

        while(isRunning)
        {
            switch (sc.nextLine())
            {
                case "100":
                    //TODO getQuestion istedet for hardcodet
                    pw.println("what is the capitol of Denmark");
                    String answer = sc.nextLine().toLowerCase();
                    if(answer.equals("copenhagen"))
                    {
                        correctAnswer(100);
                    }
                    break;

                case "stop":
                    isRunning = false;
                    pw.println("Goodbye..");
                    break;
            }
        }
    }
}
