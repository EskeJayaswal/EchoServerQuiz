import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class EchoServer
{
    private int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public void startServer() throws IOException {
        //TODO lav message-køen
        //TODO lav listen til clientHandlers
        //TODO instantier dispatcheren med de delte resourcer (blockingqueu og copyonwritearraylist)

        BlockingQueue<String> messages = new ArrayBlockingQueue<>(10);

        CopyOnWriteArrayList <ClientHandler> liste = new CopyOnWriteArrayList();

        Dispatcher d = new Dispatcher(messages,liste);

        ServerSocket serverSocket = new ServerSocket(port);
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        while(true)
        {
            Socket client = serverSocket.accept();
            //TODO lav cl med delt resource
            ClientHandler cl = new ClientHandler(client,messages);
            //TODO put cl i listen
            liste.add(cl);
            executorService.execute(cl);
            executorService.execute(d);
        }
    }
}
