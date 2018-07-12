package kr.needon.board.Model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * Create 2018-07-05 작성시작
 * NB_MEMBER_ROLES 회원 권한 테이블
 */
@EqualsAndHashCode(of = "nb_no")
@Entity
@Getter
@Setter
@ToString
@Table(name = "nb_member_roles")
@DynamicUpdate
public class MemberRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nb_no;

    private String nb_roleName;

    @CreationTimestamp
    private Date nb_regdate = new Date();

}
