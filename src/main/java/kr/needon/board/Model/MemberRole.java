package kr.needon.board.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Create 2018-07-05 작성시작
 * NB_MEMBER_ROLES 회원 권한 테이블
 */
@EqualsAndHashCode(of = "nb_no")
@Entity
@Data
@Table(name = "nb_member_roles")
public class MemberRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nb_no;

    private String nb_roleName;

    @CreationTimestamp
    private Date nb_regdate = new Date();

}
