package forelesning4;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    private ServerSocket serverSocket;
    private Thread thread;

    public HttpServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        thread = new Thread(this::handleConnections);
        thread.start();
    }

    private void handleConnections() {
        try {
            Socket socket = serverSocket.accept();
            String[] requestLine = HttpMessage.readLine(socket).split(" ", 3);
            String path = requestLine[1];
            if (path.equals("/hello")) {
                String responseMessage = "Hello World!";
                socket.getOutputStream().write(("HTTP/1.1 200 OK\r\n" +
                        "Connection: close" +
                        "Content-Length: " + responseMessage.length() + "\r\n" +
                        "Content-type: text/plain\r\n" +
                        "\r\n" +
                        responseMessage).getBytes());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getActualPort() {
        return serverSocket.getLocalPort();
    }

    public void start() {
    }
}