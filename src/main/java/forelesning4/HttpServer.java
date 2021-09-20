package forelesning4;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

public class HttpServer {

    private final ServerSocket serverSocket;
    private Path rootPath;

    public HttpServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        new Thread(this::handleConnections).start();
    }

    public HttpServer() throws IOException {
        serverSocket = new ServerSocket(0);
        new Thread(this::handleConnections).start();

    }

    private void handleConnections() {
        try {
            Socket socket = serverSocket.accept();
            String[] requestLine = HttpMessage.readLine(socket).split(" ", 3);
            String path = requestLine[1], responseMessage, statusCode;


            if (path.equals("/hello")) {
                responseMessage = "Hello World!";
                statusCode = "200 OK";
            } else if (rootPath != null && Files.exists(rootPath.resolve(path.substring(1)))) {
                responseMessage = Files.readString(rootPath.resolve(path.substring(1)));
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

    public void setRoot(Path rootPath) {
        this.rootPath = rootPath;
    }
}