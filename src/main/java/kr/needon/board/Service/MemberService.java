package kr.needon.board.Service;

import kr.needon.board.Model.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberService extends CrudRepository<Member, String> {
}
