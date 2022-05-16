package hellojpa.strategy.tableperclass;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Album3")
public class Album extends Item {

    private String artist;
}
