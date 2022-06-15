package hellojpa.pathexp;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Collection;
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

            // 상태 필드
            String query = "select m.username from Member m";

            List<String> resultList = em.createQuery(query, String.class)
                    .getResultList();

            for (String s : resultList) {
                System.out.println("s = " + s);
            }

            // 단일 값 연관 경로
            String query2 = "select m.team.name from Member m";

            List<String> resultList2 = em.createQuery(query2, String.class)
                    .getResultList();

            for (String s : resultList2) {
                System.out.println("s = " + s);
            }

            // 컬렉션 값 연관 경로
            String query3 = "select t.members from Team t";

            Collection resultList3 = em.createQuery(query3, Collection.class)
                    .getResultList();

            for (Object s : resultList3) {
                System.out.println("s = " + ((Member)s).getUsername());
            }

            // 명시적인 조인
            String query4 = "select m.username from Team t join t.members m";

            List<Collection> resultList4 = em.createQuery(query4, Collection.class)
                    .getResultList();

            System.out.println("resultList4 = " + resultList4);


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

