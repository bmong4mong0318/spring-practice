package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//@Service
@Transactional // jpa 를 쓸려면 데이터를 저장하거나 변경할 때 항상 필요하다
public class MemberService {

    private final MemberRepository memberRepository;

    // repository 를 직접 new 하는 것이 아니라 외부에서 넣어주도록 한다.
//    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // service 는 비즈니스에 가까운 용어를 써야한다.

    /**
     * 회원가입
     */
    public Long join(Member member){
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        // Optional 안에 Member 객체가 있는 형태 (Optional<Member> result)
        // 값이 null 일 가능성이 있는 경우에 Optional 로 한번 감싸서 반환해준다.
        memberRepository.findByName(member.getName())
                // null 이 아니라 어떤 값이 있으면 로직이 동작
                .ifPresent(m->{
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 회원 한명 조회
     */
    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }


}
