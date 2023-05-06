package hello.core.scope;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1);
    }

    @Test
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac =
            new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1);
    }

    @Scope("singleton")
    static class ClientBean {
        //스프링은 일반적으로 싱글톤 빈을 사용하므로, 싱글톤 빈이 프로토타입 빈을 사용하게 된다.
        //그런데 싱글톤 빈은 생성 시점에만 의존관계를 주입을 받기 때문에, 프로토타입 빈이 새로 생성되기는 하지만, 싱글톤 빈과 함께 계속 유지되는 것이 문제다.

        //생성시점에 주입
//        private final PrototypeBean prototypeBean;
//        @Autowired
//        public ClientBean(PrototypeBean prototypeBean) {
//            this.prototypeBean = prototypeBean;
//        }


        // Dependency Lookup (DL): 의존관계를 외부에서 주입(DI) 받는게 아니라
        // 직접 필요한 의존관계를 찾는 것을 의존관계 조회(탐색) 이라한다.
        // ObjectProvider: 지정한 빈을 컨테이너에서 대신 찾아주는 DL 서비스를 제공한다.
        @Autowired
        private ObjectProvider<PrototypeBean> prototypeBeanProvider;
//        private Provider<PrototypeBean> prototypeBeanProvider; javax.inject.Provider사용

        public int logic() {
            PrototypeBean prototypeBean = prototypeBeanProvider.getObject();
//            PrototypeBean prototypeBean = prototypeBeanProvider.get(); javax.inject.Provider사용
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }

    }

    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init " + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }

}
