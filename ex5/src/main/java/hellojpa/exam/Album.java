package hellojpa.exam;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Album4")
public class Album extends Item {

    private String artist;
}
