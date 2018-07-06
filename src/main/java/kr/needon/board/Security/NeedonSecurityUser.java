package kr.needon.board.Security;

import kr.needon.board.Model.Member;
import kr.needon.board.Model.MemberRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;

@Getter
public class NeedonSecurityUser extends User {

    private Member member;

    public NeedonSecurityUser(Member member) {

        super(member.getNb_username(),member.getNb_password(),makeGrantedAuthority(member.getRoles()));

        this.member = member;
    }

    private static List<GrantedAuthority> makeGrantedAuthority(List<MemberRole> roles) {

        List<GrantedAuthority> list = new ArrayList<>();

        roles.forEach(role -> list.add(new SimpleGrantedAuthority(role.getNb_roleName())));

        return list;
    }
}
