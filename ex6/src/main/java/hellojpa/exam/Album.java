package hellojpa.exam;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Album17")
public class Album extends Item {

    private String artist;
}
