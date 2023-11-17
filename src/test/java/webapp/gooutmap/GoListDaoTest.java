package webapp.gooutmap;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(SpringExtension.class)
//
//public class GoListDaoTest {
//
//    @TestConfiguration
//    static class DemoUseCaseTestConfiguration {
//
//        // テスト対象クラスのBean生成
//        @Bean
//        public DemoService demoService() {
//            return new DemoService();
//        }
//    }
//
//    // モックの作成
//    @MockBean
//    DemoRepository mockRepository;
//
//    // テスト対象クラスのインスタンスにモックを注入
//    @Autowired
//    private DemoService target;
//
//    // テストメソッド
//    @Test
//    public void decideTest01() {
//
//        // 渡す引数の準備
//        Goods goods = createGoods();
//
//        // どんなGoods型の引数でもtrueを返すようにmockを定義
//        when(mockRepository.findByName(Mockito.anyString())).thenReturn(createOrdinaryGoods());
//
//        // assert
//        assertTrue(target.decide(goods));
//    }
//
//    // Test用のデータ作成
//    private Goods createGoods() {
//
//        Goods goods = new Goods();
//        goods.setName("avocado");
//        goods.setPrice(180);
//        return goods;
//    }
//
//    // モック用のデータ作成
//    private Goods createOrdinaryGoods() {
//
//        Goods goods = new Goods();
//        goods.setName("avocado");
//        goods.setPrice(200);
//        return goods;
//    }
//
//}
