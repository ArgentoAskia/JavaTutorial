package cn.argento.askia.apps.lombok;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor(staticName = "of")
public class  User {
    public static void main(String[] args) {
        new User().print2DefaultUser();
    }

    public void print2DefaultUser(){
        var user1 = new User();
        val user2 = new User();
        System.out.println(user1);
        System.out.println(user2);
    }

    @NonNull
    private String name;
    @NonNull
    private int id;
    private int age;
    private List<String> cards;
}
