package webapp.gooutmap;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.springframework.test.web.servlet.MockMvc;

import java.sql.Connection;
import java.sql.DriverManager;


@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest//主に、Service や Dao（Repository）など DI コンテナを用いた処理をテストする際に使用します。
@AutoConfigureMockMvc//Controller のテストとして、対象のパスにリクエストを送信するために使用します。
public class RegisterControllerTest {

    @Autowired
    MockMvc mockMvc;

    private GoListDao goListDao;

    @Autowired
    public void GoListDaoTest(GoListDao goListDao) {
        this.goListDao = goListDao;
    }

    @DisplayName("/registerのstatus(),view()テスト")
    @Order(1)
    @Test
    public void getIndexTest() throws Exception {
        // when
        mockMvc.perform(get("/gomap/register"))//mockMvcのperformを使ってリクエストを実行します。・・・mockMvc.perform(メソッド名("指定のurl"))
                .andExpect(status().isOk())//レスポンスのHTTPステータスコードは正しいか?・・・ステータスコード200はstatus().isOkでテストできます。
                .andExpect(view().name("register"));//"/gomap/register"でregister.htmlを返すか確認します。
    }
    @DisplayName("/add_itemのstatus(),view()テスト")
    @Order(2)
    @Test
    public void add_itemParamTest() throws Exception {
        mockMvc.perform(
                        post("/gomap/add_item")
                                .param("pref", "佐賀県")         // 　パラメータを付与
                                .param("city", "佐賀市") //   パラメータを付与
                                .param("genre", "図書館")  //   パラメータを付与
                                .param("move_means", "車"))  //   パラメータを付与
                        .andExpect(status().isFound())
                        .andExpect(view().name("redirect:/gomap/list"));//"/gomap/register"でregister.htmlを返すか確認します。
    }

    @DisplayName("/set_myhomeのstatus(),view()テスト")
    @Order(3)
    @Test
    public void set_myhomeParamTest() throws Exception {
        mockMvc.perform(
                        get("/gomap/set_myhome")
                                .param("coordinate", "33.235495193138945, 130.3222849066703"))  //   パラメータを付与
                .andExpect(status().isOk())
                .andExpect(view().name("register"));//"/gomap/register"でregister.htmlを返すか確認します。
    }

    @DisplayName("/delete_myhomeのstatus(),view()テスト")
    @Order(4)
    @Test
    public void delete_myhomeParamTest () throws Exception {
        mockMvc.perform(
                        get("/gomap/delete_myhome")
                                .param("my_home_id", "12345678"))  //   パラメータを付与
                .andExpect(status().isOk())
                .andExpect(view().name("register"));//"/gomap/register"でregister.htmlを返すか確認します。
    }

    @DisplayName("coordinateが保存されているか")
    @Order(5)
    @Test
    @DatabaseSetup("/dbunit/coordinateTest.xml")
    void coordinateTest() {
        String id = goListDao.getmyHomeId();
        String result = goListDao.getCoordinate(id);
        assertThat(result).isEqualTo("33.235495193138945, 130.3222849066703");
    }
}
