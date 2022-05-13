package jpashop.domain;

import javax.persistence.*;

@Entity
public class OrderItem7 {

    @Id
    @GeneratedValue
    @Column(name = "ORDER_ITEM_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private Order7 order;

    @ManyToOne
    @JoinColumn(name = "ITEM_ID")
    private Item7 item;
}
