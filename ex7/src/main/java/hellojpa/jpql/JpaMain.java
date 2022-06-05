package hellojpa.jpql;


import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member = new Member();
            member.setUsername("member1");
            em.persist(member);

            // 반환타입이 명확한 경우
            TypedQuery<Member> query = em.createQuery("select m from Member as m ", Member.class);
            List<Member> resultList = query.getResultList();

            // 반환타입이 명확하지 않은 경우
            Query query2 = em.createQuery("select m.username, m.age from Member as m ");
            List<Member> resultList2 = query2.getResultList();


            TypedQuery<Member> query3 = em.createQuery("select m.username, m.age from Member as m where m.id = 1", Member.class);
            Member singleResult3 = query3.getSingleResult();
            System.out.println("result =  = " +  singleResult3);

            TypedQuery<Member> query4 = em.createQuery("select m from Member as m where m.username = :username", Member.class);
            query4.setParameter("username", "member1");
            Member singleResult = query4.getSingleResult();

            Member singleResult5 = em.createQuery("select m from Member as m where m.username = ?1", Member.class)
                    .setParameter("username", "member1")
                    .getSingleResult();

            TypedQuery<Member> query6 = em.createQuery("select m from Member as m where m.username = ?1", Member.class);
            query6.setParameter(1, "member1");
            Member singleResult6 = query6.getSingleResult();

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
