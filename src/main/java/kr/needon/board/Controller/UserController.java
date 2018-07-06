package kr.needon.board.Controller;

import kr.needon.board.Model.Member;
import kr.needon.board.Service.MemberService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.transaction.Transactional;

@Log
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/login.info")
    public String Login() {

        return "/user/login";
    }

    @RequestMapping("/join")
    public String Join() {

        return "/user/join";
    }

    @Transactional
    @PostMapping("/join")
    public String JoinPost(Model model, @ModelAttribute("member") Member member, String nb_password_re) {

        log.info("JoinPost =====> " + member.toString());
        log.info("Password Re =====> " + nb_password_re);

        if(member.getNb_password().equals(nb_password_re)) {

            try {

                member.setNb_password(passwordEncoder.encode(nb_password_re));
                member.setNb_point(100L);
                memberService.save(member);

                model.addAttribute("msg", "회원가입이 되었습니다.");
                model.addAttribute("url", "/");

            } catch (Exception e) {

                model.addAttribute("msg", "회원가입 실패!");
                model.addAttribute("url", "/");

            }

        } else {
            model.addAttribute("msg", "패스워드 확인이 일치하지 않습니다.");
            model.addAttribute("url", "/user/join");
        }


        return "/info/systemMsg";
    }

}
