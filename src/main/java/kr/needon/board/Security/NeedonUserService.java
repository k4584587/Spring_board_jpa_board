package kr.needon.board.Security;

import kr.needon.board.Model.Member;
import kr.needon.board.Service.MemberService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Log
public class NeedonUserService implements UserDetailsService {

    @Autowired
    MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        log.info("NeedonUserService ......");
        return memberService.findById(s)
                .filter(member -> member != null)
                .map(member -> new NeedonSecurityUser(member)).get();
    }
}
