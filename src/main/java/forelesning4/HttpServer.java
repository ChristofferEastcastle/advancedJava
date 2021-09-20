package forelesning4;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HttpServer {

    private final ServerSocket serverSocket;
    private Path rootPath = Paths.get(".");

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
            String path = requestLine[1], responseMessage, statusCode,
                    contentType = "text/plain";

            if (path.equals("/hello")) {
                responseMessage = "Hello World!";
                statusCode = "200 OK";
            } else if (rootPath != null && Files.exists(rootPath.resolve(path.substring(1)))) {
                if (path.endsWith(".html")) contentType = "text/html";

                responseMessage = Files.readString(rootPath.resolve(path.substring(1)));
                statusCode = "200 OK";
            } else {
                responseMessage = "NOT FOUND!";
                statusCode = "404 NOT FOUND";
            }
            socket.getOutputStream().write(("HTTP/1.1 " + statusCode + "\r\n" +
                    "Connection: keep-alive\r\n" +
                    "Content-Length: " + responseMessage.length() + "\r\n" +
                    "Content-type: " + contentType + ";charset=utf-8\r\n" +
                    "\r\n" +
                    responseMessage).getBytes());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        handleConnections();
    }

    public int getActualPort() {
        return serverSocket.getLocalPort();
    }

    public void setRoot(Path rootPath) {
        this.rootPath = rootPath;
    }

    public static void main(String[] args) throws IOException {
        HttpServer server = new HttpServer(8008);
    }
}