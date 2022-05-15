package jpabook.jpashop.one2one;

import javax.persistence.*;

@Entity
@Table(name = "MEMBER5")
public class Member {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;


}
