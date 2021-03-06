package hellojpa.fetchjoin;


import org.hibernate.annotations.BatchSize;

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
            Team teamA = new Team();
            teamA.setName("팀A");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("팀B");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setTeam(teamB);
            em.persist(member3);

            em.flush();
            em.clear();

            // Member 검색후 필요할때 Team 검색
            /*String query = "select m from hellojpa.fetchjoin.Member m";

            List<Member> resultList = em.createQuery(query, Member.class)
                    .getResultList();

            for (Member member: resultList) {
                System.out.println("member = " + member.getUsername() + ", " + member.getTeam().getName());
            }*/

            // fetch join
            /*String query2 = "select m from hellojpa.fetchjoin.Member m join fetch m.team";

            List<Member> resultList2 = em.createQuery(query2, Member.class)
                    .getResultList();

            for (Member member: resultList2) {
                System.out.println("member = " + member.getUsername() + ", " + member.getTeam().getName());
            }*/

            // fetch join
            /*String query3 = "select t from hellojpa.fetchjoin.Team t join fetch t.members";

            List<Team> resultList3 = em.createQuery(query3, Team.class)
                    .getResultList();

            for (Team team : resultList3) {
                System.out.println("team = " + team.getName() + " | members = " + team.getMembers().size());
                for (Member member : team.getMembers()) {
                    System.out.println("    member = " + member);

                }
            }*/

            // distinct
            /*String query4 = "select distinct t from hellojpa.fetchjoin.Team t join fetch t.members";

            List<Team> resultList4 = em.createQuery(query4, Team.class)
                    .getResultList();

            System.out.println("resultList4 = " + resultList4.size());*/


            // 페이징 : 이렇게 처리하면 안됨.
            /*String query5 = "select  t from hellojpa.fetchjoin.Team t join fetch t.members ㅡ";

            List<Team> resultList5 = em.createQuery(query5, Team.class)
                    .setFirstResult(0)
                    .setMaxResults(1)
                    .getResultList();

            System.out.println("resultList5 = " + resultList5.size());*/

            // 팀 조회 : 멤버를 레이지로 로딩 => 성능이 안나옴
            String query6 = "select  t from hellojpa.fetchjoin.Team t";

            List<Team> resultList6 = em.createQuery(query6, Team.class)
                    .setFirstResult(0)
                    .setMaxResults(2)
                    .getResultList();

            System.out.println("resultList6 = " + resultList6.size());

            for (Team team : resultList6) {
                System.out.println("team = " + team.getName() + "|members=" + team.getMembers().size());
                for (Member member : team.getMembers()) {
                    System.out.println("member = " + member);
                }
            }

            // Team의 @OneToMany 의 members에 @BatchSize(size = 100) 속성 부여하면
            // Members 대상이 되는 팀조건을 members0_.TEAM_ID in (?, ?) 추가하여 가져옴
            // @BatchSize(size = 100) 속성은 persistence.xml 에 hibernate.default_batch_fetch_size 속성을 이용하여
            // 전역으로 설정할수 있음

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
