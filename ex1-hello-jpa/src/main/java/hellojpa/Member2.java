package hellojpa;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 일반적인 경우 name 속성을 사용할 필요는 없지만
 * 다른 패키지에 같은 이름을 사용하는 엔티티가 존재하는 경우
 * name 속성을 지정하여 중복된 이름을 다른 이름으로 설정한다.
 */
@Entity(name="Member")

/**
 * name 속성은 엔티티 이름과 동일하게 하면 되지 않는 경우
 * 설정해서 사용
 */
@Table(name="MBR", schema = "", catalog = "")
public class Member2 {

    @Id
    private Long id;
    private String name;
    private int age;

    public Member2(){}

    public Member2(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
