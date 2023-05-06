package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest(){
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close();
    }

    @Configuration
    static class LifeCycleConfig {

        @Bean
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            //생성자 안에서 무거운 초기화 작업을 함께 진행 하는것 보다는,
            //객체를 생성하는 부분과 초기화 하는 부분을 명확하게 나누는 것이 유지 보수 관점에서 좋다.
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }
    }
}
