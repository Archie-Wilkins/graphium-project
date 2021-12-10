package client_project.y2s1.team2.graphium.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class TestController {
    @GetMapping({"/", "index"})
    public String home(Model model) {
        return "test.html";
    }

}
