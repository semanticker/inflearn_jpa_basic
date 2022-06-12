package hellojpa.jpqltype;


import hellojpa.join.Team;
import hellojpa.projection.Address;
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

            hellojpa.jpqltype.Team team = new hellojpa.jpqltype.Team();
            team.setName("teamA");
            em.persist(team);

            hellojpa.jpqltype.Member member = new hellojpa.jpqltype.Member();
            member.setUsername(null);
            member.setAge(10);
            member.setType(MemberType.ADMIN);

            member.setTeam(team);

            em.persist(member);

            em.flush();
            em.clear();

            String query =
                    "select " +
                            "case when m.age <= 10 then '학생요금' " +
                            "     when m.age >= 60 then '경로요금' " +
                            "     else  '일반요금' " +
                            " end " +
                            "from hellojpa.jpqltype.Member m ";

            List<String> resultList = em.createQuery(query, String.class)
                    .getResultList();

            for (String s : resultList) {
                System.out.println("s = " + s);
            }

            // 이름이 없는 회원을 '이름 없는 회원'으로 표시함.
            String query2 =
                    "select coalesce(m.username, '이름 없는 회원') " +
                            "from hellojpa.jpqltype.Member m ";

            List<String> resultList2 = em.createQuery(query2, String.class)
                    .getResultList();

            for (String s : resultList2) {
                System.out.println("s = " + s);
            }

            // 사용자 이름이 '관리자'면 null을 반환 하고 나머지는 본인의 이름을 반환
            String query3 =
                    "select nullif(m.username, '관리자') " +
                            "from hellojpa.jpqltype.Member m ";

            List<String> resultList3 = em.createQuery(query3, String.class)
                    .getResultList();

            for (String s : resultList2) {
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
