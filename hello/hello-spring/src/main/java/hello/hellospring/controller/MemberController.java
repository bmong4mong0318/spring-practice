package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

// `@Component` 어노테이션이 있으면 스프링 빈으로 자동 등록 된다.
// `@Controller` 컨트롤러가 스프링 빈으로 자동 등록된 이유도 컴포넌트 스캔 때문이다.
// HelloSpringApplication 이 실행되는 하위폴더에서만 컴포넌트 스캔이 이루어진다.

// 스프링이 처음 시작 될 때 스프링 커네이너가 하나 생기는데 그곳에 Controller 객체를 하나 만들어두고 관리한다.
// -> 스프링 빈이 관리된다고 한다.
// 스프링은 스프링 컨테이너에 스프링 빈을 등록할 때, 기본으로 싱글톤으로 등록한다. (유일하게 하나만 등록해서 공유한다.)
// 따라서 같은 스프링 빈이면 모두 같은 인스턴스이다.
@Controller
public class MemberController {

    private final MemberService memberService;
//    @Autowired private  MemberService memberService; (필드 주입)

    // 생성자에 @Autowired 가 있으면 스프링 컨테이너에 있는 memberService 를 연결 시켜준다. (Dependency Injection)
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    //Get 은 보통 데이터를 조회할 때 사용
    @GetMapping("/members/new")
    public String createForm(){
        return "members/createMemberForm";
    }

    //Post 는 보통 데이터를 어떤 폼에 넣어서 전달할 때 사용
    @PostMapping("/members/new")
    public String create(MemberForm form){
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        //key 는 "members"로, value 는 members 리스트로, 화면에 담아서 view 에다가 넘긴다.
        model.addAttribute("members", members);
        return "members/memberList";
    }


}
