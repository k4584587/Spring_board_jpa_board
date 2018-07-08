package kr.needon.board.Controller;

import kr.needon.board.Model.Member;
import kr.needon.board.Model.MemberRole;
import kr.needon.board.Service.MemberService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

@Log
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/@{nb_username}")
    public String UserPage(@PathVariable("nb_username") String nb_username, Model model) {

        model.addAttribute("user_info", memberService.findById(nb_username).get());

        return "/user/info";
    }

    @GetMapping("/login.info")
    public String Login() {

        return "/user/login";
    }

    @RequestMapping("/join")
    public String Join(Model model, @RequestParam(value = "error", defaultValue = "0") int error) {

        model.addAttribute("title", "니드온 회원가입");

        if (error == (1)) {

            model.addAttribute("error", "패스워드 확인이 일치하지 않습니다.");

        }

        return "/user/join-update";
    }

    @PostMapping("/update")
    public String UserUpdate(@RequestParam("nb_username") String username, Model model, Member member) {

        Member update = memberService.findById(username).get();

        model.addAttribute("user_info", update);

        return "/user/join-update";
    }

    @Transactional
    @PostMapping("/update.submit")
    public String UserUpdatePost(@RequestParam("username") String username, @ModelAttribute("member") Member member, @RequestParam("nb_profile_") MultipartFile nb_profile_, Model model) throws IOException {


        System.out.println("권한 ===> " + member.getRoles());

        System.out.println("파일이름 =====> " + nb_profile_.getOriginalFilename());
        System.out.println("파일 업로드 상태 ====> " + nb_profile_.isEmpty());

        try {

            Member update = memberService.findById(username).get();

            member.setNb_password(passwordEncoder.encode(member.getNb_password()));

            System.out.println("포인트 ===> " + update.getNb_point());

            //기본정보
            member.setNb_regdate(update.getNb_regdate()); //가입일
            member.setNb_point(update.getNb_point()); //포인트

            if(nb_profile_.isEmpty()) {
                System.out.println("파일상태 ==== > 1");

            } else {
                member.setNb_profile(nb_profile_.getBytes());
                System.out.println("파일상태 ==== > 2");
            }

            memberService.save(member);

            model.addAttribute("msg", "정보수정 되었습니다.");
            model.addAttribute("url", "/");

        } catch (Exception e) {

            e.printStackTrace();
            model.addAttribute("msg", "정보수정 실패");
            model.addAttribute("url", "/");

        }


        return "/info/systemMsg";
    }

    @Transactional
    @PostMapping("/join")
    public String JoinPost(Model model, @ModelAttribute("member") Member member, @RequestParam("nb_profile_") MultipartFile nb_profile_, String nb_password_re) throws IOException {

        log.info("JoinPost =====> " + member.toString());
        log.info("Password Re =====> " + nb_password_re);

        if (member.getNb_password().equals(nb_password_re)) { //패스워드 확인 체크

            try {

                if (nb_profile_.isEmpty()) { //파일업로드 비였을시

                    member.setNb_profile(null);

                } else {

                    member.setNb_profile(nb_profile_.getBytes());

                }

                member.setNb_password(passwordEncoder.encode(nb_password_re));
                member.setNb_point(100L); //회원가입시 기본포인트
                member.setNb_exp(0L); //회원가입시 경험치 0으로 초기화
                memberService.save(member);

                model.addAttribute("msg", "회원가입이 되었습니다.");
                model.addAttribute("url", "/");

            } catch (Exception e) {

                model.addAttribute("msg", "회원가입 실패!");
                model.addAttribute("url", "/");

            }

        } else {

            return "redirect:/user/join?error=1";

        }

        return "/info/systemMsg";
    }

}
