package webapp.gooutmap;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.GetMapping;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)//↓のランダムのポートに@SpringBootTestを指定します。
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {
//    @Value(value="${local.server.port}")
//    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    /**
     * assertThat(実際の値、CoreMatchersクラスのメソッド(期待する値));
     *
     */
    @Test
    public void greetingShouldReturnDefaultMessage() throws Exception {
        assertThat(testRestTemplate.getForObject("http://localhost:" + port + "/gomap/register",
        String.class)).contains("Hello, World");
    }
}
