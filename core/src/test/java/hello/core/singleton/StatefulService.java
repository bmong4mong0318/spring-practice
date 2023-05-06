package hello.core.singleton;

public class StatefulService {

    // 공유되는 필드는 조심해야한다.
    // 스프링 빈은 항상 무상태(stateless)로 설계한다.
//    private int price; // 상태를 유지하는 필드

    public int order(String name, int price) {
        System.out.println("name = " + name + " price = " + price);
//        this.price = price;
        return  price;
    }

//    public int getPrice() {
////        return price;
//    }

}
