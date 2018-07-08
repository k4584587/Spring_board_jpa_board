package kr.needon.board.Controller;

import kr.needon.board.Model.Member;
import kr.needon.board.Service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    MemberService memberService;

    @GetMapping("/img/profile")
    public ResponseEntity<byte[]> ProfileImg(@RequestParam("username") String username) { //프로필 사진

        Member member = memberService.findById(username).get();
        byte[] profile = member.getNb_profile();

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);


        System.out.println("사용자 ====> " + member.getNb_username());

        return new ResponseEntity<byte[]>(profile, headers, HttpStatus.OK);
    }

}
