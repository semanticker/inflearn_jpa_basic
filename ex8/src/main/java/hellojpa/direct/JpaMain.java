package hellojpa.direct;


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
            member.setAge(10);
            member.setTeam(team);
            em.persist(member);

            Member member2 = new Member();
            member2.setUsername("관리자2");
            member2.setAge(10);
            em.persist(member2);

            em.flush();
            em.clear();

            // 엔티티 아이디 사용
            String query = "select count(m.id) from hellojpa.direct.Member m";

            Long singleResult = em.createQuery(query, Long.class)
                    .getSingleResult();

            System.out.println("s = " + singleResult);

            // 엔티티 아이디 사용
            String query2 = "select count(m) from hellojpa.direct.Member m";

            Long singleResult2 = em.createQuery(query2, Long.class)
                    .getSingleResult();

            System.out.println("s = " + singleResult2);

            // 엔티티를 파라미터로 전달
            String query3 = "select m from hellojpa.direct.Member m where m = :member";
            List resultList3 = em.createQuery(query3)
                    .setParameter("member", member)
                    .getResultList();

            System.out.println("resultList3 = " + resultList3.size());

            Long memberId = 100L;
            // 값을 파라미터로 전달
            String query4 = "select m from hellojpa.direct.Member m where m.id = :memberId";
            List resultList4 = em.createQuery(query4)
                    .setParameter("memberId", memberId)
                    .getResultList();

            System.out.println("resultList4 = " + resultList4.size());


            // 외래키가 될수 있는 엔티티를 직접 전달
            String query5 = "select m from hellojpa.direct.Member m where m.team = :team";
            List resultList5 = em.createQuery(query5)
                    .setParameter("team", team)
                    .getResultList();

            System.out.println("resultList5 = " + resultList5.size());


            // 외래키 값을 파라미터로 전달
            String query6 = "select m from hellojpa.direct.Member m where m.team.id = :teamId";
            List resultList6 = em.createQuery(query6)
                    .setParameter("teamId", team.getId())
                    .getResultList();

            System.out.println("resultList6 = " + resultList6.size());

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
