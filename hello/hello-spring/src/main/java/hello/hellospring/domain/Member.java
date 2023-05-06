package hello.hellospring.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//이제부터 Member 는 jpa 가 관리하는 Entity 가 되는 것이다.
@Entity
public class Member {

    //DB 에 값을 넣으면 DB 가 값을 자동으로 생성해준다.
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
