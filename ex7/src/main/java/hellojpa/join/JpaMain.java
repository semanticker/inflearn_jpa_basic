package hellojpa.join;


import hellojpa.projection.Address;
import hellojpa.projection.Member;
import hellojpa.projection.MemberDTO;

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

            hellojpa.join.Member member = new hellojpa.join.Member();
            member.setUsername("member1");
            member.setAge(10);

            member.setTeam(team);

            em.persist(member);

            em.flush();
            em.clear();



            // 내부조인
            String query = "select m from hellojpa.join.Member m inner join m.team t";
            List<hellojpa.join.Member> resultList = em.createQuery(query, hellojpa.join.Member.class)
                    .getResultList();

            // 외부조인
            String query2 = "select m from hellojpa.join.Member m left outer join m.team t";
            List<hellojpa.join.Member> resultList2 = em.createQuery(query2, hellojpa.join.Member.class)
                    .getResultList();

            // 세타조인 : 연관관계가 없는 조인
            String query3 = "select m from hellojpa.join.Member m, hellojpa.join.Team t where m.username = t.name";
            List<hellojpa.join.Member> resultList3 = em.createQuery(query3, hellojpa.join.Member.class)
                    .getResultList();

            System.out.println("resultList3.size() = " + resultList3.size());

            // 조인 대상 필터링
            String query4 = "select m from hellojpa.join.Member m left join m.team t on t.name = 'teamA'";
            List<hellojpa.join.Member> resultList4 = em.createQuery(query4, hellojpa.join.Member.class)
                    .getResultList();

            // 연관관계 없는 엔티티 외부 조인
            String query5 = "select m from hellojpa.join.Member m left join hellojpa.join.Team t on m.username = t.name";
            List<hellojpa.join.Member> resultList5 = em.createQuery(query5, hellojpa.join.Member.class)
                    .getResultList();

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
