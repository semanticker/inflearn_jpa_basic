package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {

        // basic();

        // 1차 캐시 (같은 앤티티)
        // cache();

        // 1차 캐시 (다른 앤티티)
        // cache2();

        // 영속 엔티티의 동일설
        // equalObj();

        // batch write
        //lazyWrite();

        // update
        dirtyCheck();

        flush();

    }

    private static void flush() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member(200L, "member200");
            // 영속성 컨텍스트에 추가됨.
            em.persist(member);

            // INSERT 쿼리가 바로 실행됨.
            // 1차
            em.flush();;

            System.out.println("===============================");
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
            emf.close();
        }
    }

    /**
     * 변경감지
     * 커밋하는 시점에 내부적으로 flush()가 호출됨
     * 엔티티와 스냅샷 비교
     * 스냅샷은 최초에 값을 읽은 시점의 엔티티의 상태를 가지고 있음
     * 엔티티의 값이 변경되었으면 엔티티와 스냅샷을 비교하여 변경사항이 있으면
     * 쓰기 지연 SQL 저장소에 UPDATE 구문을 저장해 놓고
     * 이 부분을 데이터베이스에 실행하고 커밋처리함.
     */
    public static void dirtyCheck() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = em.find(Member.class, 100L);

            // 자바 컬렉션을 다루는 것처럼 이용하는 JPA
            // 데이터 변경은 커밋하는 시점에 자동으로 update 처리 됨
            member.setName("ZZZZ");

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
            emf.close();
        }
    }


    /**
     * 쓰기 지연
     */
    public static void lazyWrite() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member1 = new Member();
            member1.setId(1000L);
            member1.setName("A");

            // 1차 캐시에 저장후
            // 바로 DB에 insert 하지 않고
            // 쓰기 지연 SQL 저장소에 INSERT 문을 만들어 저장하고 있음
            em.persist(member1);

            Member member2 = new Member();
            member2.setId(1001L);
            member2.setName("B");

            // 1차 캐시에 저장후
            // 바로 DB에 insert 하지 않고
            // 쓰기 지연 SQL 저장소에 INSERT 문을 만들어 저장하고 있음
            em.persist(member2);

            // 커밋하는 시점에
            // 쓰기 지연 SQL 저장소에 있는 쿼리들이 실행되어(flush)
            // 데이터 베이스에 저장됨.
            tx.commit();

            // 이 방식은 여러 실행 구문을 하나의 트랜잭션에 처리하므로
            // 배치 프로세싱과 같은 효과가 있다.
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
            emf.close();
        }
    }

    /**
     * 영속 엔티티의 동일성 보장
     */
    public static void equalObj() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member1 = em.find(Member.class, 101L);
            Member member2 = em.find(Member.class, 101L);

            System.out.println(member1==member2);

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
            emf.close();
        }
    }

    /**
     * 영속성 컨텍스에 없는 엔티티를 조회한 이후
     * 다시 재조회를 할때 디비에서 가져오지 않고 영속성 컨텍스트에서 조회하여 반환하는 예제
     */
    public static void cache2() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member1 = em.find(Member.class, 101L);
            Member member2 = em.find(Member.class, 101L);

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
            emf.close();
        }
    }

    public static void cache() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // 비영속
            Member member = new Member();
            member.setId(101L);
            member.setName("HelloJPA");

            // 영속
            System.out.println("== Before ==");
            em.persist(member);
            System.out.println("== After ==");

            Member findMember = em.find(Member.class, 101L);

            System.out.println("findMember.id = " + findMember.getId());
            System.out.println("findMember.name = " + findMember.getName());

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
            emf.close();
        }
    }

    public static void basic() {
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
