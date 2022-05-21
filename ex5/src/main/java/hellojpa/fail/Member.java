package hellojpa.fail;

import javax.persistence.*;

@Entity
@Table(name="Member13")
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    // N+1 문제를 발생시키는 원인
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Team team;  // 읽기 전용 처리됨

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
