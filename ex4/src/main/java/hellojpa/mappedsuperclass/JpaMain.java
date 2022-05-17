package hellojpa.mappedsuperclass;

import hellojpa.strategy.join.Movie;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member8 member8 = new Member8();
            member8.setName("name");
            member8.setCreatedBy("kim");
            member8.setCreatedDate(LocalDateTime.now());

            em.persist(member8);

            em.flush();
            em.clear();


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
