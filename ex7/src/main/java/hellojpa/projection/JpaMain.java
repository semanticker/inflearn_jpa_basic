package hellojpa.projection;


import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setUsername("member1");
            em.persist(member);

            em.flush();
            em.clear();

            String query = "select m from Member m";
            List<Member> resultList = em.createQuery(query, Member.class)
                    .getResultList();

            Member member1 = resultList.get(0);
            member1.setAge(20);

            // Team 엔티티 프로젝션
            String query1 = "select m.team from Member m";
            String query2 = "select t from Member m join  m.team t";
            List<Team> resultList1 = em.createQuery(query1, Team.class)
                    .getResultList();


            // 임베디드 타입 프로젝션
            String query3 = "select o.address from Order o";
            List<Address> resultList3 = em.createQuery(query3, Address.class)
                    .getResultList();

            // 스칼라 타입 프로젝션
            String query4 = "select m.username, m.age from Member m";
            List resultList4 = em.createQuery(query4)
                    .getResultList();

            for (Object o : resultList4) {
                Object [] result  = (Object[]) o;
                System.out.println("username = " + result[0]);
                System.out.println("age = " + result[1]);
            }
            // 스칼라 타입 프로젝션 2
            List<Object[]> resultList5 = em.createQuery(query4)
                    .getResultList();

            for (Object[] objects : resultList5) {
                System.out.println("username = " + objects[0]);
                System.out.println("age = " + objects[1]);
            }

            // 스칼라 타입 프로젝션
            String query6 = "select new hellojpa.projection.MemberDTO(m.username, m.age) from Member m";
            List<MemberDTO> resultList6 = em.createQuery(query6, MemberDTO.class)
                    .getResultList();

            for (MemberDTO memberDTO : resultList6) {
                System.out.println("username = " + memberDTO.getUsername());
                System.out.println("age = " + memberDTO.getAge());
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
