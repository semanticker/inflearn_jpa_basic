package hellojpa.strategy.single;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Book2")
public class Book extends Item {

    private String author;
    private String isbn;
}
