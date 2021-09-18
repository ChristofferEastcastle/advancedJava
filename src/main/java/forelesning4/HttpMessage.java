package forelesning4;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

public class HttpMessage {

    public static String readLine(Socket socket) throws IOException {
        StringBuilder line = new StringBuilder();
        int c;
        while ((c = socket.getInputStream().read()) != -1 && c != '\r') {
            line.append((char) c);
        }
        //noinspection ResultOfMethodCallIgnored
        socket.getInputStream().read();
        return line.toString();
    }

    public static String readBytes(Socket socket, int contentLength) throws IOException {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < contentLength; i++) {
            buffer.append((char) socket.getInputStream().read());
        }

        return buffer.toString();
    }

    public static HashMap<String, String> readInputHeaders(Socket socket) throws IOException {
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

}
