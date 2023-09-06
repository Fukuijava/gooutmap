package webapp.gooutmap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;


@Service
public class GoListDao {
    private final static String TABLE_NAME = "golist";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    GoListDao(JdbcTemplate jdbcTemplate) {//フィールド地の初期化
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(RegisterController.GoListItem goListItem) {//タスクリストテーブルに登録するメソッド
        SqlParameterSource param = new BeanPropertySqlParameterSource(goListItem);
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate).withTableName("golist");
        insert.execute(param);
    }

    public List<RegisterController.GoListItem> findAll() {//現在登録されているすべての情報を取得してListオブジェクトに格納して返す
        String query = "SELECT * FROM golist";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(query);
        List<RegisterController.GoListItem> goItems = result.stream()
                .map((Map<String, Object> row) -> new RegisterController.GoListItem(
                        (String) row.get("id"),
                        (String) row.get("pref"),
                        (String) row.get("city"),
                        (String) row.get("genre"),
                        (String) row.get("move_means")
                )).toList();
        return goItems;
    }
}
