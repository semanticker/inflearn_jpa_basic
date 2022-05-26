package hellojpa;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="Member14")
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name="USERNAME")
    private String username;

    // Period
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    // Address
    private String city;
    private String stree;
    private String zipcode;





}
