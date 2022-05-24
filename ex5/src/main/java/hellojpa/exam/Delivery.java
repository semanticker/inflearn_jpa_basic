package hellojpa.exam;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

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

    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Order order;

}
