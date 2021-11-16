package API.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class loginController {

        @GetMapping({"/loginForm", "loginForm"})
        public String index() {
            return "loginForm.html";
        }
}

