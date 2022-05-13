package jpashop.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ITEM7")
public class Item7 {

    @Id @GeneratedValue
    @Column(name="ITEM_ID")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categoryList = new ArrayList<>();

}
