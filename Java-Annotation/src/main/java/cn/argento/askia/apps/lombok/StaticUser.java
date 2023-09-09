package cn.argento.askia.apps.lombok;

import lombok.*;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
//@Data(staticConstructor = "newInstance")
@NoArgsConstructor(staticName = "newInstance")
public class StaticUser extends User{


    private String address;
    private String address2;

    public static void main(String[] args) {
        StaticUser staticUser = StaticUser.newInstance();
        System.out.println(staticUser);
    }
}
