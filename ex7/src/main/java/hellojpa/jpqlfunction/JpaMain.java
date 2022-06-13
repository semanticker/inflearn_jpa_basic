package hellojpa.jpqlfunction;


import javax.persistence.*;
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
            member.setUsername("member1");
            member.setAge(10);

            member.setTeam(team);

            em.persist(member);

            em.flush();
            em.clear();

            String query = "select concat('a', 'b') from hellojpa.jpqlfunction.Member m";
            //String query = "select 'a'|| 'b' from hellojpa.jpqlfunction.Member m";

            List<String> resultList = em.createQuery(query, String.class)
                    .getResultList();

            for (String s : resultList) {
                System.out.println("s = " + s);
            }

            String query2 = "select substring(m.username, 2, 3) from hellojpa.jpqlfunction.Member m";

            List<String> resultList2 = em.createQuery(query2, String.class)
                    .getResultList();

            for (String s : resultList2) {
                System.out.println("s = " + s);
            }

            String query3 = "select locate('de', 'abcdefg') from hellojpa.jpqlfunction.Member m";

            List<Integer> resultList3 = em.createQuery(query3, Integer.class)
                    .getResultList();

            for (int s : resultList3) {
                System.out.println("s = " + s);
            }

            String query4 = "select size(t.members) from hellojpa.jpqlfunction.Team t";

            List<Integer> resultList4 = em.createQuery(query4, Integer.class)
                    .getResultList();

            for (int s : resultList4) {
                System.out.println("s = " + s);
            }



            //String query5 = "select function('group_concat', m.username) from hellojpa.jpqlfunction.Member m";
            String query5 = "select group_concat(m.username) from hellojpa.jpqlfunction.Member m";

            List<String> resultList5 = em.createQuery(query5, String.class)
                    .getResultList();

            for (String s : resultList5) {
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
