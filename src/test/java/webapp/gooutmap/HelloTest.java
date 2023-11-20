package webapp.gooutmap;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class HelloTest {
    Hello hello = new Hello();

    @Test
    void あいさつのテスト(){
        String s = hello.greet("Tanaka");
        assertEquals(s,"Hello Tanaka!!");
    }
}
