package hellojpa.valuetypecollection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Set;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(new Address("home-city", "street", "10000"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("피자");
            member.getFavoriteFoods().add("족발");

            member.getAddressHistory().add(new Address("old-city", "old-street", "200000"));
            member.getAddressHistory().add(new Address("old-city2", "old-street2", "300000"));

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("================= FIND MEMBER ===================");

            Member findMember = em.find(Member.class, member.getId());

            List<Address> addressHistory = findMember.getAddressHistory();
            for (Address address : addressHistory) {
                System.out.println("address = " + address.getCity());
            }

            Set<String> favoriteFoods = findMember.getFavoriteFoods();
            for (String food : favoriteFoods) {
                System.out.println("food = " + food);
            }


            // 수정
            /// findMember.getHomeAddress().setCity("new-home"); // wrong
            Address oldHomeAddress = findMember.getHomeAddress();
            findMember.setHomeAddress(new Address("new-home",oldHomeAddress.getStreet(), oldHomeAddress.getZipcode() ));


            // 치킨을 한식으로
            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("한식");

            findMember.getAddressHistory().remove(new Address("old-city", "old-street", "200000"));
            findMember.getAddressHistory().add(new Address("new-city1", "street", "10000"));

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
