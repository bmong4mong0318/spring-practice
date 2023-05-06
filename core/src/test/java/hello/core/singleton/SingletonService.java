package hello.core.singleton;

public class SingletonService {

    //java가 뜰 때 static 영역에 객체를 딱 1개만 생성해둔다.
    private static final SingletonService instance = new SingletonService();

    //public으로 열어서 객체 인스턴스가 필요하면 이 static 메서드를 조회하도록 허용한다.
    public static SingletonService getInstance() {
        return instance;
    }

    //private으로 생성자를 만들었기 때문에 외부에서 생성이 불가능하다.
    private SingletonService() {
    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }

    //싱글톤 패턴 문제점
    //싱글톤 패턴을 구현하는 코드 자체가 많이 들어간다.
    //의존관계상 클라이언트가 구체 클래스에 의존한다. -> DIP를 위반한다.
    //클라이언트가 구체 클래스에 의존해서 OCP 원칙을 위한할 가능성이 높다.
    //테스트하기 어렵다.
    //내부 속성을 변경하거나 초기화 하기 어렵다.
    //private 생성자로 자식 클래스를 만들기 어렵다.
    //결론적으로 유연성이 떨어진다.

}
