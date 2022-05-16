package hellojpa.strategy.single;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Album2")
public class Album extends Item {

    private String artist;
}
