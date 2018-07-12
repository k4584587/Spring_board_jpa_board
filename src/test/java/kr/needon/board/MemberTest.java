package kr.needon.board;

import kr.needon.board.Model.Member;
import kr.needon.board.Model.MemberRole;
import kr.needon.board.Service.MemberRoleService;
import kr.needon.board.Service.MemberService;
import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
@Commit
public class MemberTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MemberRoleService memberRoleService;

    @Test
    public void testInsertMember() { //회원 더비 생성

        for (int i = 0; i <= 100; i++) {

            Member member = new Member();
            member.setNb_username("user" + i);
            member.setNb_password("pw" + i);
            member.setNb_name("사용자" + i);

            MemberRole role = new MemberRole();
            if (i <= 80) {
                role.setNb_roleName("BASIC");
            } else if (i <= 90) {
                role.setNb_roleName("MANAGER");
            } else {
                role.setNb_roleName("ADMIN");
            }
            member.setRoles(Arrays.asList(role));

            memberService.save(member);
        }

    }

    @Test
    public void InsertMemberPoint() {

        List<String> ids = new ArrayList<>();
        ids.add("user99");

       /* for(int i = 0; i <= 100; i++) {
            ids.add("user" + i);
        }
*/

    }

    @Test
    public void MemberFind() { //user85 회원 검색

        Optional<Member> result = memberService.findById("user85");

        result.ifPresent(member -> log.info("member ===> " + member));

    }

    @Test
    public void testUpdateMember() {

        List<String> ids = new ArrayList<>();

        for (int i = 0; i <= 100; i++) {
            ids.add("user" + i);
        }

        memberService.findAllById(ids).forEach(member -> {
            member.setNb_password(passwordEncoder.encode(member.getNb_password()));
            memberService.save(member);
        });

    }

    @Test
    public void testUserID() {

        Optional<Member> reslut = memberService.findById("user99");


        System.out.println("Member ======> " + memberRoleService.findById(1L));

    }

    @Test
    public void UserUpdate() {

        List<String> ids = new ArrayList<>();
        ids.add("test");

        memberService.findAllById(ids).forEach(member -> {

            MemberRole memberRole = new MemberRole();

            member.getRoles().forEach(memberRole1 -> {
                memberRole.setNb_no(memberRole1.getNb_no());
                //System.out.println("권한 ===> " + memberRole1.toString());
            });

            System.out.println("권한 ===> " + memberRole.toString());
            memberRole.setNb_roleName("test");
            member.setRoles(Arrays.asList(memberRole));

            memberService.save(member);

        });

    }

    @Test
    public void testUpdate() {

        Member member = new Member();
        member.setNb_username("test");

        member.setNb_user_info("test");

        memberService.save(member);

    }

}
