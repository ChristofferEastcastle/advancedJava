package forelesning4;

import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class HttpWorker implements Runnable {

    private final Socket socket;
    private HashMap<String, String> headers;
    private Path rootPath;


    public HttpWorker(Socket socket, HttpServer server) {
        this.socket = socket;
        this.rootPath = server.getRootPath();
    }

    public void run() {
        try {
            String[] requestLine = HttpMessage.readLine(socket).split(" ", 3);

            headers = HttpMessage.readInputHeaders(socket);

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
                    "Content-Length: " + responseMessage.length() + "\r\n" +
                    "Content-Type: " + contentType + ";charset=utf-8\r\n" +
                    "\r\n" +
                    responseMessage).getBytes());

            socket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}