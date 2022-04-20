package hellojpa;

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

        // case1 기본적인 저장방법
        /*Member member = new Member();
        member.setId(2L);
        member.setName("HelloB");
        em.persist(member);

        tx.commit();
        em.close();
        emf.close();*/

        // case2 형식을 갖춘 저장방법
        /*try {
            Member member = new Member();
            member.setId(2L);
            member.setName("HelloB");
            em.persist(member);

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
            emf.close();
        }*/

        try {
            // 조회
            Member member = em.find(Member.class, 1L);

            // 수정
            member.setName("HelloJPA");

            // 삭제
            //em.remove(member)

            tx.commit();

            // 조건을 가진 조회
            //// 전체
            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                    .getResultList();

            for (Member mem : result) {
                System.out.println("member.name = " + mem.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
            emf.close();
        }



    }

}
