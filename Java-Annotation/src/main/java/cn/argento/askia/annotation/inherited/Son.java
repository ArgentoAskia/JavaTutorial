package cn.argento.askia.annotation.inherited;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Son extends Father implements Serializable, Comparable<Son> {

    @Override
    public String getName() {
        return super.getName() + " = " + LocalDateTime.now();
    }

    @Override
    public int compareTo(Son o) {
        return 0;
    }
}
