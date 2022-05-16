package hellojpa.strategy.join;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Album")
public class Album extends Item{

    private String artist;
}
