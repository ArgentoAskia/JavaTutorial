package cn.argento.askia.apps.lombok;

import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class User2 {
    public static void main(String[] args) {
        final User2 askia = User2.builder().addCard("123").addCard("456")
                .age(23)
                .id(1)
                .name("Askia")
                .build();
        System.out.println(askia);
    }

    public void print2DefaultUser(){
        var user1 = new User2();
        val user2 = new User2();
        System.out.println(user1);
        System.out.println(user2);
    }


    private String name;
    private int id;
    private int age;
    @Singular("addCard")
    private Set<String> cards;
}
