package hellojpa.strategy.join;

import javax.persistence.Entity;

@Entity
public class Album extends Item{

    private String artist;
}
