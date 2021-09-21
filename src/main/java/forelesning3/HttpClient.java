package forelesning3;

import forelesning4.HttpMessage;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

public class HttpClient {
    private final String host;
    private final Socket socket;
    private HashMap<String, String> headers;
    private final int statusCode;
    private String body;


    public HttpClient(String host, int port, String path) throws IOException {
        this.socket = new Socket(host, port);
        this.host = host;
        statusCode = executeRequest(path);

    }

    public int executeRequest(String path) throws IOException {
        final int statusCode;
        String req = String.format("GET %s HTTP/1.1\r\n" +
                "Connection: keep-alive\r\n" +
                "Host: %s\r\n" +
                "\r\n", path, host);

        socket.getOutputStream().write(req.getBytes());

        statusCode = Integer.parseInt(HttpMessage.readLine(socket).split(" ")[1]);

        headers = HttpMessage.readInputHeaders(socket);

        body = HttpMessage.readBytes(socket, Integer.parseInt(headers.get("Content-Length")));

        return statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Object getResponseHeader(String header) {
        return headers.get(header);
    }


    public String getResponseBody() {
        return body;
    }
}
