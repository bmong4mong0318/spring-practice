package hello.hellospring.repository;

import static org.assertj.core.api.Assertions.*;

import hello.hellospring.domain.Member;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    // 테스트의 순서는 보장이 안된다, 테스트의 순서에 의존되지 않게 코드를 짜야한다.
    // 따라서 이전 테스트에서의 내용이 이후 테스트에서 영향을 미치지 않도록 `매 테스트마다` 초기화를 해주어야 한다.
    @AfterEach
    public void afterEach(){
        // 테스트가 끝날 때마다 저장소를 한번 씩 지운다.
        repository.clearStore();
    }

    @Test
    public void save(){
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        Member result = repository.findById(member.getId()).get();
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();
        assertThat(result.size()).isEqualTo(2);
    }

}