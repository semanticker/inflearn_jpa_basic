package hellojpa.polymo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Book book = new Book();
            book.setName("JPA");
            book.setAuthor("김영한");

            em.persist(book);

            em.flush();
            em.clear();

            String query = "select i from Item i where type(i) IN (Book, Movie)";

            List<Item> resultList = em.createQuery(query, Item.class)
                    .getResultList();

            for (Item s : resultList) {
                System.out.println("s = " + s);
            }


            String query2 = "select i from Item i where treat(i as Book).author = '김영한'";

            List<Item> resultList2 = em.createQuery(query2, Item.class)
                    .getResultList();

            for (Item s : resultList2) {
                System.out.println("s = " + s);
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
            emf.close();
        }
    }
}
