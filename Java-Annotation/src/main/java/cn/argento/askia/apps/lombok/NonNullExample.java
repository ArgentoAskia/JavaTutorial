package cn.argento.askia.apps.lombok;

import lombok.Lombok;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NonNullExample extends Object {
    private String name;
    @NonNull
    private Integer id;

    public NonNullExample(@NonNull String person) {
        super();
        this.name = person;
    }

    public static void main(String[] args) {
        new NonNullExample(2);
    }
}
