package hellojpa.bulk;


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
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);


            Member member = new Member();
            member.setUsername("관리자1");
            member.setAge(0);
            member.setTeam(team);
            em.persist(member);

            Member member2 = new Member();
            member2.setUsername("관리자2");
            member2.setAge(0);
            em.persist(member2);

     /*       em.flush();
            em.clear();*/

            // 모든 회원의 나이를 20살로
            int resultCount = em.createQuery("update hellojpa.bulk.Member m set m.age = 20")
                    .executeUpdate();
            em.clear();
            System.out.println("resultCount = " + resultCount);

            Member member1 = em.find(Member.class, member.getId());
            System.out.println("member1 = " + member1.getAge());


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
