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
    @GetMapping("/gomap/map/{id}")
    public String map(@PathParam("id") String id,
                       Model model) {
        List<RegisterController.GoListItem> goListItems = this.dao.map(id);
        model.addAttribute("gomapList", goListItems);
        return "map";
    }
}