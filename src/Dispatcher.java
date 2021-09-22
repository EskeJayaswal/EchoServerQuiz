import java.net.Socket;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class Dispatcher implements Runnable
{

    BlockingQueue<String> messages;
    CopyOnWriteArrayList<ClientHandler> liste;

    public Dispatcher(BlockingQueue<String> queue, CopyOnWriteArrayList<ClientHandler> liste) {
        this.messages = queue;
        this.liste = liste;
    }

    @Override
    public void run()
    {
        String outmsg = "";

        while(true)
        {
            try {
                outmsg = messages.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (ClientHandler ch : liste)
            {
                ch.getPw().println(outmsg);
            }
        }
    }
}
