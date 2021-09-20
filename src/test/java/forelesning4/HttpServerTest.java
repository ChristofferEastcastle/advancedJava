package forelesning4;

import forelesning3.HttpClient;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpServerTest {
    @Test
    void shouldReadResponseCode200() throws IOException {
        var server = new HttpServer(80);
        int port = server.getActualPort();
        var client = new HttpClient("localhost", port, "/hello");
        assertEquals(200, client.getStatusCode());
    }

    @Test
    void shouldReadResponseCode404() throws IOException {
        var server = new HttpServer();
        int port = server.getActualPort();
        var client = new HttpClient("localhost", port, "/nothing-here");
        assertEquals(404, client.getStatusCode());
    }

    @Test
    void shouldReadResponseMessage() throws IOException {
        var server = new HttpServer();
        int port = server.getActualPort();
        var client = new HttpClient("localhost", port, "/hello");
        assertEquals("Hello World!", client.getResponseBody());
    }

    @Test
    void shouldReadFileFromDisk() throws IOException {
        HttpServer server = new HttpServer();
        int port = server.getActualPort();
        String fileContent = "A file created at: " + LocalTime.now();
        Files.write(Paths.get("target/test-classes/example-file.txt"), fileContent.getBytes());
        server.setRoot(Paths.get("target/test-classes"));
        HttpClient client = new HttpClient("localhost", port, "/example-file.txt");
        assertEquals(fileContent, client.getResponseBody());
    }

    @Test
    void shouldReadHelloWorldFromFile() throws IOException {
        HttpServer server = new HttpServer();
        int port = server.getActualPort();
        String fileContent = Files.readString(Paths.get("/index.html".substring(1)));
        server.setRoot(Paths.get("."));
        HttpClient client = new HttpClient("localhost", port, "/index.html");
        assertEquals(fileContent, client.getResponseBody());
    }
}
