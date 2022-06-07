package hellojpa.paging;

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
            hellojpa.paging.Member member = new hellojpa.paging.Member();
            member.setUsername("member1");
            em.persist(member);

            em.flush();
            em.clear();

            String query = "select m from hellojpa.paging.Member m order by m.age desc";
            List<hellojpa.paging.Member> resultList = em.createQuery(query, hellojpa.paging.Member.class)
                    .setFirstResult(1)
                    .setMaxResults(10)
                    .getResultList();

            System.out.println("result.size() = " + resultList.size());

            for (hellojpa.paging.Member mem : resultList) {
                System.out.println("mem.toString() = " + mem.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
            emf.close();
        }
    }
}
