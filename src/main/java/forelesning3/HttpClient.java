package forelesning3;

import java.io.IOException;
import java.net.Socket;
import java.net.http.HttpResponse;
import java.util.HashMap;

public class HttpClient {
    private HashMap<String, String> headers;
    private int statusCode;
    private String body;
    private final String path;
    private final int port;
    private final String host;

    public HttpClient(String host, int port, String path) {
        this.host = host;
        this.port = port;
        this.path = path;
    }

    private String readLine(Socket socket) throws IOException {
        StringBuilder line = new StringBuilder();
        int c;
        while ((c = socket.getInputStream().read()) != -1 && c != '\r') {
            line.append((char) c);
        }
        //noinspection ResultOfMethodCallIgnored
        socket.getInputStream().read();
        return line.toString();
    }

    private HashMap<String, String> readInputHeaders(Socket socket) throws IOException {
        HashMap<String, String> headers = new HashMap<>();
        String[] splitted;
        String line = readLine(socket), key, value;

        while (!line.isEmpty()) {
            splitted = line.split(":");
            key = splitted[0];
            value = splitted[1].trim();
            headers.put(key, value);
            line = readLine(socket);
        }
        return headers;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Object getResponseHeader(String header) {
        return headers.get(header);
    }

    private String readBodyContent(Socket socket) throws IOException {
        StringBuilder body = new StringBuilder();

        String line;
        while (!(line = readLine(socket)).isBlank()) {
            body.append(line);
        }

        return body.toString();
    }

    public String getResponseBody() {
        return body;
    }

    public String executeRequest() throws IOException {

        var socket = new Socket(host, port);

        String req = String.format("GET %s HTTP/1.1\r\n" +
                "Connection: close\r\n" +
                "Host: %s\r\n" +
                "\r\n", path, host);

        socket.getOutputStream().write(req.getBytes());

        statusCode = Integer.parseInt(readLine(socket).split(" ")[1]);

        headers = readInputHeaders(socket);

        body = readBodyContent(socket);

        return body;
    }
}
