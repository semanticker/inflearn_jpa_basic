package jpabook.jpashop.many2many;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "MEMBER6")
public class Member6 {

    @Id @GeneratedValue
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @ManyToMany
    @JoinTable(name = "MEMBER6_PRODUCT")
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "member") // 다대다 한계 극복
    private List<MemberProduct> memberProducts = new ArrayList<>();

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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
