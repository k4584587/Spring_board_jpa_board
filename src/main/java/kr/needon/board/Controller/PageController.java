package kr.needon.board.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/info")
public class PageController {

    @GetMapping("/accessDenied")
    public String AccessDenied() {

        return "info/accessDenied";
    }

}
