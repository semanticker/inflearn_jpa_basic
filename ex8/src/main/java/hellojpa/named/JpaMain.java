package hellojpa.named;

import hellojpa.named.Member;
import hellojpa.named.Team;

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
            hellojpa.named.Team team = new Team();
            team.setName("teamA");
            em.persist(team);


            hellojpa.named.Member member = new hellojpa.named.Member();
            member.setUsername("관리자1");
            member.setAge(10);
            member.setTeam(team);
            em.persist(member);

            hellojpa.named.Member member2 = new Member();
            member2.setUsername("관리자2");
            member2.setAge(10);
            em.persist(member2);

            em.flush();
            em.clear();

            List<Member> resultList = em.createNamedQuery("Member.findByUsername", Member.class)
                    .setParameter("username", "관리자2")
                    .getResultList();

            for (Member member1 : resultList) {
                System.out.println("member1 = " + member1);
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
