package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;

public class JpaMemberRepository implements MemberRepository{

    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        //jpa 가 insert 쿼리 다 만들어서 DB 에 집어넣고 setId 까지 다해준다.
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
            .setParameter("name", name)
            .getResultList();

        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        // 객체를 대상으로 쿼리를 날린다.
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }
}
