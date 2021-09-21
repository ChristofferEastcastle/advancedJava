package forelesning4;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.util.HashMap;

public class HttpServer implements Runnable{

    private ServerSocket serverSocket;
    private final int port;
    private Thread thread;
    private Path rootPath = Path.of(".");
    private HashMap<String, String> headers = new HashMap<>();
    private boolean stopped = false;

    public HttpServer(int port) {
        this.port = port;
    }

    public HttpServer() {
        this.port = 0;
    }

    public void run() {
        synchronized (this) {
            this.thread = Thread.currentThread();
        }
        openServerSocket();
        while (!stopped) {
            Socket socket = null;
            try {

                socket = serverSocket.accept();

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            new Thread(new HttpWorker(socket, this)).start();
        }
    }

    private void openServerSocket() {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    public int getActualPort() {
        return serverSocket.getLocalPort();
    }

    public void setRoot(Path rootPath) {
        this.rootPath = rootPath;
    }

    public static void main(String[] args) {
        HttpServer server = new HttpServer(8008);
        server.start();
    }

    public void start() {
        new Thread(this).start();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public Path getRootPath() {
        return rootPath;
    }
}