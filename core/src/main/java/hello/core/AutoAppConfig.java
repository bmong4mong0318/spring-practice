package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
//`@ComponentScan`은 `@Component`가 붙은 모든 클래스를 스프링 빈으로 등록한다.
//이때 스프링 빈의 기본 이름은 클래스명을 사용하되 맨 앞글자만 소문자를 사용한다.
//ex) MemberServiceImpl 클래스 -> memberServiceImpl
@ComponentScan(
    //탐색할 패키지의 시작 위치를 지정한다.
    //이 패키지를 포함해서 하위 패키지를 모두 탐색한다.
    //만약 지정하지 않으면 `@ComponentScan`이 붙은 설정 정보 클래스의 패키지가 시작 위치가 된다.
    basePackages = "hello.core",
    //제외 파일 지정
    excludeFilters= @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {

    //이 경우 수동빈 등록이 우선권을 가진다.
    //수동 빈이 자동 빈을 오버라이딩 해버린다.
    //개발자가 우선을 가지기 때문에 수동이 우선권을 가지는 것이 좋지만, 현실에서는 개발자의 의도보다는 여러 설정들이 꼬이는 경우가 많다.
    //최근 스프링 부트에서는 수동 빈 등록과 자동 빈 등록이 충돌나면 오류가 발생하도록 기본 값을 바꾸었다.
    //application.properties에 `spring.main.allow-bean-definition-overriding=true`를 등록하면 오류가 나지 않는다.
    @Bean(name = "memoryMemberRepository")
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }


}
