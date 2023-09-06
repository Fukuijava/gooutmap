package webapp.gooutmap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class RegisterController {

    public record GoListItem(
            String id, String pref, String city, String genre, String move_means){
    }

    private List<GoListItem> goListItems = new ArrayList<>();

    private GoListDao dao;

    @Autowired
    RegisterController(GoListDao dao) {
        this.dao = dao;
    }

    @GetMapping("/gomap/register")
    public String register() {
        return "register";
    }
    @PostMapping("/gomap/add_item")
    public String add_item(@RequestParam("pref") String pref,
                           @RequestParam("city") String city,
                           @RequestParam("genre") String genre,
                           @RequestParam("move_means") String move_means) {
        String id = UUID.randomUUID().toString().substring(0, 8);
        GoListItem item = new GoListItem(id, pref, city, genre, move_means);
        dao.add(item);
        return "redirect:/golist/list";
    }
}
