package webapp.gooutmap;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class GoOutMapApplicationTests {

	private MockMvc mockMvc;
	@Autowired//テスト対象のクラスをDIコンテナに登録。
	RegisterController target;
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(target).build();
	}

	/**
	 * 最初にできることは、アプリケーションコンテキストを開始できない場合に失敗する単純な健全性チェックテストを記述することです
	 *
	 * @SpringBootTest アノテーションは、Spring Boot にメイン構成クラス（たとえば @SpringBootApplicationを持つもの
	 * を探し、それを使用して Spring アプリケーションコンテキストを開始するように指示します。
	 */

	@Test
	void contextLoads() {
		System.out.println("Webアプリ起動成功");
	}

//	@Test
//	public void shouldReturnDefaultMessage() throws Exception {
//		mockMvc.perform(get("/gomap")).andExpect(status().isOk());
//
//	}
}
