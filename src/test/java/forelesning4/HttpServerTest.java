package forelesning4;

import forelesning3.HttpClient;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpServerTest {
    @Test
    void shouldReadResponseCode() throws IOException {
        var server = new HttpServer(80);
        server.start();
        int port = server.getActualPort();
        var client = new HttpClient("localhost", port, "/hello");
        client.executeRequest();
        assertEquals(200, client.getStatusCode());
    }
}
