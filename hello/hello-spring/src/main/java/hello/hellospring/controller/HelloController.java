package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    // 웹 페이지에 있는 hello를 누르면 여기로 이동한다.
    @GetMapping("hello")
    public String hello(Model model){
        // 모델을 만들어서 key 값으로 data, value 값으로 hello!!를 넣어준다.
        model.addAttribute("data", "hello!!");
        // 컨트롤러에서 리턴값으로 문자를 반환하면 `viewResolver`가 resources/template 에 있는 hello.html 을 찾는다.
        // 'resources/templates' + {viewName} + `.html`
        return "hello";
    }

    @GetMapping("hello-mvc")
    // localhost:8080/hello-mvc 만 치면 에러가 뜬다.
    // localhost:8080/hello-mvc?name=spring!!! 와 같이 인자를 전달해주어야 한다.
    public String helloMvc(@RequestParam("name") String name, Model model){
        model.addAttribute("name", name);
        return "hello-template";
    }

    @GetMapping("hello-string")
    // html body 부에 "hello" + name 데이터를 (html 템플릿을 가진 형태가 아니라) 직접 넣어 주겠다.
    @ResponseBody
    public String helloString(@RequestParam("name") String name){
        return "hello" + name;
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello = new Hello();
        hello.setName(name);
        // 객체를 반환하고 @ResponseBody 를 해놓으면
        // HttpMessageConverter 가 동작을 하고
        // jsonConverter 가 동작을 해서 기본적으로 json 형식으로 반환하게 된다.
        // {"name":"spring!!!!!"}
        return hello;
    }

    static class Hello{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
