package hello.core.lifecycle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class NetworkClient {

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);
    }

    public void setUrl(String url){
        this.url = url;
    }

    //서비스 시작시 호출
    public void connect() {
        System.out.println("connect: " + url);
    }

    public void call(String message){
        System.out.println("call: " + url + " message = " + message);
    }

    //서비스 종료시 호출
    public void disconnect() {
        System.out.println("close " + url);
    }

    //외부 라이브러리에는 적용하지 못한다. 외부 라이브러리를 초기화, 종료 해야하면 `@Bean`의 기능을 사용
    @PostConstruct
    public void init() {
        connect();
        call("초기화 연결 메시지");
    }

    @PreDestroy
    public void close() {
        disconnect();
    }
}
