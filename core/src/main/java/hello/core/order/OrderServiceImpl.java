package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//final이 붙은 필드 변수를 파라미터로 받는 생성자를 만들어준다.
// public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }
//@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

    //`OrderServiceImpl`이 `DiscountPolicy` 인터페이스 뿐만 아니라 `FixDiscountPolicy` 구체 클래스도 함께 의존하고 있다.
    // -> "DIP 위반"

    //그래서 할인 정책을 변경하려면 클라이언트인 `OrderServiceImpl`코드를 고쳐야 한다(변경에 닫혀있지 않다).
    //기능을 확장해서 변경하면, 클라이언트 코드에 영향을 준다 -> "OCP 위반"

    // 해결책 : 누군가가 클라이언트인 `OrderServiceImpl`에 `DiscountPolicy`의 구현 객체를 대신 생성해서 주입해주어야 한다.
    // -> "구현 객체를 생성"하고 "연결"하는 책임을 가지는 별도의 설정 클래스를 만들어야 한다.

    //생성자 주입은 순수한 자바언의 특징을 잘 살리는 방법이다.
    //항상 생성자 주입을 사용하고 필요시에 수정자 주입 방식을 옵션으로 부여한다. 필드 주입은 사용하지 않는게 좋다.

    //`@Autowired` 매칭 정리
    //1.타입 매칭
    //2.타입매칭의 결과가 2개 이상일 때 필드명, 파라미터 명으로 빈 이름 매칭
    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    //테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
