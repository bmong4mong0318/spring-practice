package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {

    public static void main(String[] args) {
        //이전 코드는 필요한 객체를 `AppConfig`를 사용해서 직접 조회했지만 이제부터는 스프링 컨테이너를 통해서 필요한 스프링 빈(객체)를 찾아야한다.
//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();

        //스프링 컨테이너는 `@Configuration`이 붙은 AppConfig를 설정(구성)정보로 사용한다.
        //여기서 `@Bean`이라 적힌 메서드를 모두 호출해서 반환된 객체를 스프링 컨테이너에 등록한다.
        //AppConfig 안에 있는 환경설정 정보들(@Bean)을 모두 스프링 컨테이너(`ApplicationContext`) 안에 넣어서 관리해준다.
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        //Bean의 메서드명과 반환 타입을 적어주면 된다.
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);

        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("new member = " + member.getName());
        System.out.println("findMember = " + findMember.getName());
    }

}
