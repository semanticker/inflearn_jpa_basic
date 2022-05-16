package hellojpa.strategy.tableperclass;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Book3")
public class Book extends Item {

    private String author;
    private String isbn;
}
