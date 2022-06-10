package hellojpa.subquery;



import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            hellojpa.subquery.Team team = new hellojpa.subquery.Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);

            member.setTeam(team);

            em.persist(member);

            em.flush();
            em.clear();



            // 서브쿼리
            String query = "select (select sum(m1.age) from hellojpa.subquery.Member m1) as avgAge from hellojpa.subquery.Member m inner join m.team t";
            List<Long> resultList = em.createQuery(query, Long.class)
                    .getResultList();

            System.out.println("resultList = " + resultList.size());

            // 서브쿼리 : FROM의 서브쿼리는 동작하지 않음. 조인으로 풀어서 해결할수 있으면 풀어서 처리해야함. Native SQL을 사용하기도 함.
            String query2 = "select mm.age, mm.username from (select m.age, m.username from Member m) as mm";
            List<Member> resultList2 = em.createQuery(query2, Member.class)
                    .getResultList();

            System.out.println("resultList2 = " + resultList2.size());


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
