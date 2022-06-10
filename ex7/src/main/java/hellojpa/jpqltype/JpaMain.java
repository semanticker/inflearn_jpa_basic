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
            member.setUsername("member1");
            member.setAge(10);
            member.setType(MemberType.ADMIN);

            member.setTeam(team);

            em.persist(member);

            em.flush();
            em.clear();

            // JPQL 타입 표현
            String query = "select m.username, 'HELLO', TRUE from hellojpa.jpqltype.Member m ";
            List<Object[]> resultList = em.createQuery(query)
                    .getResultList();

            for (Object[] objects : resultList) {
                System.out.println("objects[0] = " + objects[0]);
                System.out.println("objects[1] = " + objects[1]);
                System.out.println("objects[2] = " + objects[2]);
            }

            // ENUM 타입 조건 추가
            String query2 = "select m.username, 'HELLO', TRUE from hellojpa.jpqltype.Member m " +
                    "where m.type = hellojpa.jpqltype.MemberType.ADMIN";
            List<Object[]> resultList2 = em.createQuery(query2)
                    .getResultList();

            for (Object[] objects : resultList2) {
                System.out.println("objects[0] = " + objects[0]);
                System.out.println("objects[1] = " + objects[1]);
                System.out.println("objects[2] = " + objects[2]);
            }

            //
            String query3 = "select m.username, 'HELLO', TRUE from hellojpa.jpqltype.Member m " +
                    "where m.type = :userType";

            List<Object[]> resultList3 = em.createQuery(query3)
                    .setParameter("userType", MemberType.ADMIN)
                    .getResultList();

            for (Object[] objects : resultList3) {
                System.out.println("objects[0] = " + objects[0]);
                System.out.println("objects[1] = " + objects[1]);
                System.out.println("objects[2] = " + objects[2]);
            }

            Book book = new Book();
            book.setName("JPA");
            book.setAuthor("김영한");

            em.persist(book);

            String query4 = "select i from hellojpa.jpqltype.Item i where type(i) = hellojpa.jpqltype.Book";
            em.createQuery(query4, Item.class).getResultList();

            String query5 = "select m.username, 'HELLO', TRUE from hellojpa.jpqltype.Member m " +
                    "where m.age between 0 and 10";

            List<Object[]> resultList5 = em.createQuery(query5)
                    .getResultList();

            for (Object[] objects : resultList5) {
                System.out.println("objects[0] = " + objects[0]);
                System.out.println("objects[1] = " + objects[1]);
                System.out.println("objects[2] = " + objects[2]);
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
