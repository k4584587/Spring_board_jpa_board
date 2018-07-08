package kr.needon.board.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * Create 2018-07-05 작성시작
 * NB_Member 회원 테이블
 */
@EqualsAndHashCode(of = "nb_username")
@Data
@Entity
@Table(name = "nb_member")
public class Member {

    @Id
    private String nb_username;

    private String nb_password;

    private String nb_name;

    private String nb_nick;

    private Long nb_point;

    private Long nb_exp;

    @Lob
    @Column(length=1024000)
    private byte[] nb_profile;

    @Lob
    @Column(length=1024000)
    private byte[] nb_profile_banner;

    @Lob
    @Column(length=1024000)
    private byte[] nb_profile_bg;

    private String nb_user_info;

    @CreationTimestamp
    private Date nb_regdate = new Date();

    @UpdateTimestamp
    private LocalDateTime nb_updatedate;

    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name = "member")
    private List<MemberRole> roles;


}
