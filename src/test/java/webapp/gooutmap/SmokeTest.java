package webapp.gooutmap;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SmokeTest {

    /**
     * Spring は @Autowired アノテーションを解釈し、テストメソッドが実行される前にコントローラーが挿入されます。
     * AssertJ (英語)  （assertThat() およびその他のメソッドを提供）を使用して、テストアサーションを表現します。
     *
     */
    @Autowired
    private RegisterController registerController;
    @Autowired
    private ListController listController;
    @Autowired
    private MapController mapController;

    /**
     * コンテキストがコントローラーを作成していることを確信させるために、アサーションを追加できます。
     * assertThat(実際の値、CoreMatchersクラスのメソッド(期待する値));
     * ↑は実際の値と期待する値を比べて成功か失敗を返す。
     *assertThat(registerController).isNotNull();//
     *↑registerControllerの中身がNullではなかったら成功
     */


    @Test
    public void contextLoads1() throws Exception {
        assertThat(registerController).isNotNull();//
    }

    @Test
    public void contextLoads2() throws Exception {
        assertThat(listController).isNotNull();
    }

    @Test
    public void contextLoads3() throws Exception {
        assertThat(mapController).isNotNull();
    }
}
