package kr.needon.board.Controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String Home(Model model) {

        model.addAttribute("title", "메인");
        return "index";
    }

    @Secured(value = {"ROLE_ADMIN"})
    @GetMapping("/admin")
    public String Admin() {

        return "admin";
    }

}
