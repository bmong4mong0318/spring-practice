package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;

//@Repository
public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    // sequence: 0 1 2 처럼 key 값을 생성해주는 녀석
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream() // store 돌면서
            .filter(member -> member.getName().equals(name)) // name 과 같은 이름을 가진 member 를 찾아서
            .findAny(); // 하나 찾으면 바로 반환, 없으면 Optional 에 null 포함해서 반환
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
