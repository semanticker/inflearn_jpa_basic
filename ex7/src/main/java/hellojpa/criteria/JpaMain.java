package hellojpa.criteria;


import hellojpa.start.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Member> query = cb.createQuery(Member.class);

            Root<Member> m = query.from(Member.class);

            CriteriaQuery<Member> select = query.select(m)
                            .where(cb.equal(m.get("username"), "kim"));
            List<Member> resultList = em.createQuery(select)
                    .getResultList();


            CriteriaQuery<Member> select2 = query.select(m)
                    .where(cb.equal(m.get("username"), "kim"));

            String username = "";

            // 동적 쿼리 > 조건
            if (username != null) {
                select2 = select2.where(cb.equal(m.get("username"), "kim"));
            }

            List<Member> resultList2 = em.createQuery(select2)
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
