package webapp.gooutmap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


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
//        SqlParameterSource param = new BeanPropertySqlParameterSource(mapListItem);
//        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate).withTableName("maplist");
//        insert.execute(param);


//        int number = jdbcTemplate.update(
//                " my_home SET latitude = ?, longitude = ? WHERE my_home_id = ?",
//                myHomeItem.latitude(),
//                myHomeItem.longitude(),
//                myHomeItem.my_home_id());
//        return number;
//    }


    SqlParameterSource param = new BeanPropertySqlParameterSource(myHomeItem);
    SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate).withTableName("my_home");
        insert.execute(param);
}

//        String url = "jdbc:mysql://localhost:3306/gooutmapdb";
//        String user = "root";
//        String password = "NIKUnikufy44";
//
//        try (Connection con = DriverManager.getConnection(url, user, password);
//            PreparedStatement preStatement = con.prepareStatement("INSERT INTO maplist(`maplist_id`, `golist_id`, `latitude`, `longitude`) VALUES(?,'',?,?);")) {
//            preStatement.setString(1, mapListItem.maplist_id());
//            preStatement.setString(2, mapListItem.latitude());
//            preStatement.setString(3, mapListItem.longitude());
//            int count = preStatement.executeUpdate();
//        }catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//    INSERT INTO orderinfo(user,isbn,quantity,date) VALUES('kanda','00001',1,'2010-07-01');
//    public List<RegisterController.GoListItem> map(String id) {
//        String query = "SELECT * FROM golist WHERE golist_id ='"+id+"'";
//        String query = "select * from maplist left outer join golist on maplist.golist_id=golist.golist_id";
//        String query = "SELECT * FROM maplist LEFT JOIN golist USING ";
//        String query = "SELECT * FROM maplist m LEFT OUTER JOIN golist g ON m.golist_id=g.golist_id WHERE COLUMN_NAME = 'g.golist_id'";
//        String query = "SELECT * FROM maplist m LEFT OUTER JOIN golist g ON m.golist_id=g.golist_id WHERE m.golist_id='"+id+"'";
//
//        List<Map<String, Object>> result = jdbcTemplate.queryForList(query);
//        List<RegisterController.GoListItem> goListItems = result.stream()
//                .map((Map<String, Object> row) -> new RegisterController.GoListItem(
//                        (String) row.get("id"),
//                        (String) row.get("pref"),
//                        (String) row.get("city"),
//                        (String) row.get("genre"),
//                        (String) row.get("move_means")
//                )).toList();
//        return goListItems;
//        public void map(String golist_id) {
//        String url = "jdbc:mysql://localhost:3306/gooutmapdb";
//        String user = "root";
//        String password = "NIKUnikufy44";
//
//        try (Connection con = DriverManager.getConnection(url, user, password);
//            PreparedStatement preStatement = con.prepareStatement("INSERT INTO maplist(`maplist_id`, `golist_id`, `latitude`, `longitude`) VALUES('', '"+golist_id+"', NULL, NULL);")) {
//            preStatement.setInt(1, id);
//            preStatement.setString(2, name);
//            int count = preStatement.executeUpdate();
//        }catch (SQLException e) {
//            e.printStackTrace();
//        }finally {
//            System.out.println("人間を追加しました。");
//        }
//        return ;
//    }

    public List<RegisterController.MyHomeItem> myhome() {
        String query = "SELECT * FROM my_home";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(query);
        List<RegisterController.MyHomeItem> goItems = result.stream()
                .map((Map<String, Object> row) -> new RegisterController.MyHomeItem(
                        (String) row.get("my_home_id"),
                        (String) row.get("latitude"),
                        (String) row.get("longitude")
                )).toList();
        return goItems;
    }
}

