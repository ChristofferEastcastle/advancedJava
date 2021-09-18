package forelesning4;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    private final ServerSocket serverSocket;
    private final Thread thread;

    public HttpServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        thread = new Thread(this::handleConnections);
        thread.start();
    }

    public HttpServer() throws IOException {
        serverSocket = new ServerSocket(0);
        thread = new Thread(this::handleConnections);
        thread.start();
    }


    private void handleConnections() {
        try {
            Socket socket = serverSocket.accept();
            String[] requestLine = HttpMessage.readLine(socket).split(" ", 3);
            String path = requestLine[1], responseMessage, statusCode;
            if (path.equals("/hello")) {
                responseMessage = "Hello World!";
                statusCode = "200 OK";
            } else {
                responseMessage = "NOT FOUND!";
                statusCode = "404 NOT FOUND";
            }
            socket.getOutputStream().write(("HTTP/1.1 " + statusCode + "\r\n" +
                    "Connection: close\r\n" +
                    "Content-Length: " + responseMessage.length() + "\r\n" +
                    "Content-type: text/plain\r\n" +
                    "\r\n" +
                    responseMessage).getBytes());

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getActualPort() {
        return serverSocket.getLocalPort();
    }
}