package webapp.gooutmap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class RegisterController {

    public record GoListItem(
            String golist_id, String pref, String city, String genre, String move_means){
    }
    public record MyHomeItem(
            String my_home_id, String latitude, String longitude){
    }

    private List<GoListItem> goListItems = new ArrayList<>();

    private GoListDao dao;

    @Autowired
    RegisterController(GoListDao dao) {
        this.dao = dao;
    }


    @GetMapping("/gomap/register")
    public String register(Model model) {
        List<MyHomeItem> my_home_Items = dao.myhome();
        model.addAttribute("my_home_List", my_home_Items);
        return "register";
    }
    @PostMapping("/gomap/add_item")
    public String add_item(@RequestParam("pref") String pref,
                           @RequestParam("city") String city,
                           @RequestParam("genre") String genre,
                           @RequestParam("move_means") String move_means) {
        String golist_id = UUID.randomUUID().toString().substring(0, 8);
        GoListItem item = new GoListItem(golist_id, pref, city, genre, move_means);
        dao.add_item(item);
        return "redirect:/gomap/list";
    }

    @GetMapping("/gomap/set_myhome")
    public String add_item(@RequestParam("latitude") String latitude,
                           @RequestParam("longitude") String longitude,
                           Model model) {
        String my_home_id = UUID.randomUUID().toString().substring(0, 8);
        MyHomeItem item = new MyHomeItem(my_home_id, latitude, longitude);
        dao.set_myhome(item);
        List<MyHomeItem> my_home_Items = dao.myhome();
        model.addAttribute("my_home_List", my_home_Items);
        return "register";
    }

    @GetMapping("/gomap/test3")
    public String test3() {
        return "test3";
    }
    @GetMapping("/gomap/test4")
    public String test4() {
        return "test4";
    }
}
