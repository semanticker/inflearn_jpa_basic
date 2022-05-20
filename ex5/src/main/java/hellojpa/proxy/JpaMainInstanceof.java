package hellojpa.proxy;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMainInstanceof {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member1 = new Member();
            member1.setUsername("hello");
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("hello");
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("hello");
            em.persist(member3);

            em.flush();
            em.clear();

            Member m1 = em.find(Member.class, member1.getId());
            Member m2 = em.find(Member.class, member2.getId());

            System.out.println("m1 == m2 : " + (m1.getClass() == m2.getClass()));

            // fail
            Member m3 = em.getReference(Member.class, member3.getId());

            System.out.println("m1 == m3 : " + (m1.getClass() == m3.getClass()));
            System.out.println("m1 instance of Member : " + (m1 instanceof Member ));
            System.out.println("m1 instance of Member : " + (m3 instanceof Member ));

            // 영속성 컨텍스트에서 찾은 reference 대상은 실제 엔티티임
            Member m4 = em.getReference(Member.class, member2.getId());
            System.out.println("m2 reference : " + m4.getClass());
            System.out.println("m2 reference : " + (m2 == m4));

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
            emf.close();
        }
    }

    private static void printMember(Member member) {
        System.out.println("member = " + member.getUsername());
    }

    private static void printMemberAndTeam(Member member) {
        String username = member.getUsername();
        System.out.println("username = " + username);

        Team team = member.getTeam();
        System.out.println("team = " + team.getName());
    }
}
