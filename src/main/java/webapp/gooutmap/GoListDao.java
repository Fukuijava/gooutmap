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
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    GoListDao(JdbcTemplate jdbcTemplate) {//フィールド地の初期化
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add_item(RegisterController.GoListItem goListItem) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(goListItem);
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate).withTableName("golist");
        insert.execute(param);
    }

    public List<RegisterController.GoListItem> findAll() {//現在登録されているすべての情報を取得してListオブジェクトに格納して返す
        String query = "SELECT * FROM golist";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(query);
        List<RegisterController.GoListItem> goItems = result.stream()
                .map((Map<String, Object> row) -> new RegisterController.GoListItem(
                        (String) row.get("golist_id"),
                        (String) row.get("pref"),
                        (String) row.get("city"),
                        (String) row.get("genre"),
                        (String) row.get("move_means")
                )).toList();
        return goItems;
    }

    public int delete(String golist_id) {
        int number = jdbcTemplate.update("DELETE FROM golist WHERE golist_id = ?", golist_id);
        return number;
    }

    public int update(RegisterController.GoListItem goListItem){
        int number = jdbcTemplate.update(
                "UPDATE golist SET pref = ?, city = ?, genre = ?, move_means = ? WHERE golist_id = ?",
                goListItem.pref(),
                goListItem.city(),
                goListItem.genre(),
                goListItem.move_means(),
                goListItem.golist_id());
        return number;
    }

    public void set_myhome(RegisterController.MyHomeItem myHomeItem) {
        jdbcTemplate.update("DELETE FROM my_home");
        SqlParameterSource param = new BeanPropertySqlParameterSource(myHomeItem);
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate).withTableName("my_home");
        insert.execute(param);
    }
    public List<RegisterController.MyHomeItem> myhome(){
        String query = "SELECT * FROM my_home";
        List<Map<String, Object>> result = this.jdbcTemplate.queryForList(query);
        List<RegisterController.MyHomeItem> list = result.stream().map(
                (Map<String, Object> row) -> new RegisterController.MyHomeItem(
                        row.get("my_home_id").toString(),
                        row.get("coordinate").toString()
                )).toList();
        return list;
    }

    public List<RegisterController.GoListItem> gomap(String golist_id) {
        String query = "SELECT * FROM golist WHERE golist_id = '" + golist_id + "'";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(query);
        List<RegisterController.GoListItem> goItems = result.stream()
                .map((Map<String, Object> row) -> new RegisterController.GoListItem(
                        (String) row.get("golist_id"),
                        (String) row.get("pref"),
                        (String) row.get("city"),
                        (String) row.get("genre"),
                        (String) row.get("move_means")
                )).toList();
        return goItems;
    }

    public int delete_myhome(String my_home_id) {
        int number = jdbcTemplate.update("DELETE FROM my_home WHERE my_home_id = ?", my_home_id);
        return number;
    }
}

