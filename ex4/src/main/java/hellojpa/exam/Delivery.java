package hellojpa.exam;

import javax.persistence.*;

@Entity
@Table(name = "Delivery8")
public class Delivery extends BaseEntity{

    @Id
    @GeneratedValue
    private Long id;

    private String city;
    private String street;
    private String zipcode;
    private DeliveryStatus status;

    @OneToOne(mappedBy = "delivery")
    private Order order;

}
