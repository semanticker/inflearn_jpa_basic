package hellojpa.jpqltype;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Album23")
public class Album extends Item {

    private String artist;
}
