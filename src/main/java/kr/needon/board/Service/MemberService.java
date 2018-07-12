package kr.needon.board.Service;

import kr.needon.board.Model.Member;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MemberService extends CrudRepository<Member, String> {

    @Query("update Member m set m.nb_profile_banner = ?1 where m.nb_username = ?2")
    void ProfileBannerUpdate(String username);

}
