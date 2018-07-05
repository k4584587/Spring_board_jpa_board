package kr.needon.board.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/login.info")
    public String Login(Model model) {

        model.addAttribute("test","test");
        return "/user/login";
    }

    @PostMapping("/login.info")
    public String Logout(Model model) {

        model.addAttribute("test","test");
        return "/user/login";
    }

}
