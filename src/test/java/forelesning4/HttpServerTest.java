package forelesning4;

import forelesning3.HttpClient;
import org.junit.jupiter.api.Test;

import java.io.IOException;

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
        var client = new HttpClient("localhost", port, "/");
        assertEquals(404, client.getStatusCode());
    }

    @Test
    void shouldReadResponseMessage() throws IOException {
        var server = new HttpServer();
        int port = server.getActualPort();
        var client = new HttpClient("localhost", port, "/hello");
        assertEquals("Hello World!", client.getResponseBody());
    }


}
