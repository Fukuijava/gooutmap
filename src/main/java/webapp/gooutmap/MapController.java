package webapp.gooutmap;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MapController {
    public GoListDao dao;
    @Autowired
    MapController(GoListDao dao) {
        this.dao = dao;
    }
    @GetMapping("/gomap/map/{golist_id}")
    public String map(@PathParam("golist_id") String golist_id,
                       Model model) {
        List<RegisterController.GoListItem> goListItems = this.dao.gomap(golist_id);
        List<RegisterController.MyHomeItem> myHomeItems = this.dao.myhome();
        model.addAttribute("gomapList", goListItems);
        model.addAttribute("my_home_List", myHomeItems);
        return "map";
    }
}