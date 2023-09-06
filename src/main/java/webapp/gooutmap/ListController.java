package webapp.gooutmap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ListController {
    public GoListDao dao;
    @Autowired
    ListController(GoListDao dao) {
        this.dao = dao;
    }
    @GetMapping("/golist/list")
    public String list(Model model) {
        List<RegisterController.GoListItem> goListItems = dao.findAll();
        model.addAttribute("gomapList", goListItems);
        return "list";
    }
}
