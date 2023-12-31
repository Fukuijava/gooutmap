package webapp.gooutmap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ListController {
    public GoListDao dao;
    @Autowired
    ListController(GoListDao dao) {
        this.dao = dao;
    }
    @GetMapping("/gomap/list")
    public String list(Model model) {
        List<RegisterController.GoListItem> goListItems = dao.findAll();
        model.addAttribute("gomapList", goListItems);
        return "list";
    }

    @GetMapping("/gomap/delete")
    String delete(@RequestParam("golist_id") String golist_id){
        this.dao.delete(golist_id);
        return "redirect:/gomap/list";
    }

    @PostMapping("/gomap/update")
    public String update(@RequestParam("golist_id") String golist_id,
                         @RequestParam("pref") String pref,
                         @RequestParam("city") String city,
                         @RequestParam("genre") String genre,
                         @RequestParam("move_means") String move_means){
        RegisterController.GoListItem item = new RegisterController.GoListItem(golist_id, pref, city, genre, move_means);
                dao.update(item);
        return "redirect:/gomap/list";
    }
}
