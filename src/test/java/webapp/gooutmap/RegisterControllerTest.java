package webapp.gooutmap;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)//Junit上でもSpringのDI機能を動かす必要があるため
@SpringBootTest//主に、Service や Dao（Repository）など DI コンテナを用いた処理をテストする際に使用します。
@AutoConfigureMockMvc//Controller のテストとして、対象のパスにリクエストを送信するために使用します。
public class RegisterControllerTest {
    private MockMvc mockMvc;
    @Autowired//テスト対象のクラスをDIコンテナに登録。
    RegisterController target;
    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(target).build();
    }

    /**
     * レスポンスのHTTPステータスコードは正しいか？
     * 指定のviewを返すか？
     * modelに正しい変数を詰められているか？
     */

    @Test
    public void getIndexTest() throws Exception {
        // when
        mockMvc.perform(get("/gomap/register"))//mockMvcのperformを使ってリクエストを実行します。・・・mockMvc.perform(メソッド名("指定のurl"))
                .andExpect(status().isOk())//レスポンスのHTTPステータスコードは正しいか?・・・ステータスコード200はstatus().isOkでテストできます。
                .andExpect(view().name("register"))//"/gomap/register"でregister.htmlを返すか確認します。
                .andExpect(model().attribute("coordinate", "33.235495193138945, 130.3222849066703"));
    }
}
